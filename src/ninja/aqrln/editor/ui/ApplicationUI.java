package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.model.DocumentModelChildlessElement;
import ninja.aqrln.editor.export.HTMLExporter;
import ninja.aqrln.editor.export.LaTeXExporter;
import ninja.aqrln.editor.export.latexrunner.LaTeXRunner;
import ninja.aqrln.editor.io.DocumentSerializer;
import ninja.aqrln.editor.net.cloud.EditorCloudAPI;
import ninja.aqrln.editor.ui.frames.AboutDialog;
import ninja.aqrln.editor.ui.frames.DocumentWindow;
import ninja.aqrln.editor.ui.menu.ApplicationMenuListener;
import ninja.aqrln.editor.ui.platform.UIFactory;
import ninja.aqrln.editor.util.OSXExtensions;
import ninja.aqrln.editor.util.OperatingSystem;

import javax.swing.*;
import java.awt.Desktop;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Alexey Orlenko
 */
public class ApplicationUI implements ApplicationMenuListener {
    private static ApplicationUI instance;

    private DocumentWindow activeWindow;

    private SortedSet<DocumentWindow> windowsPool;

    private JMenu windowMenu;
    private JCheckBoxMenuItem checkedWindowMenuItem;

    public static ApplicationUI getInstance() {
        if (instance == null) {
            instance = new ApplicationUI();
        }

        return instance;
    }

    private JMenuBar applicationMenu;

    private List<DocumentModelChildlessElement> clipboard;

    private ApplicationUI() {
        applicationMenu = UIFactory.getInstance().createApplicationMenuDirector(this).buildApplicationMenu();
        windowsPool = Collections.synchronizedSortedSet(new TreeSet<>());
        clipboard = new ArrayList<>();
    }

    public void run() {
        if (OperatingSystem.getOS() == OperatingSystem.OS_X) {
            OSXExtensions.getInstance().setMenuBar(applicationMenu);
        }

        onFileNew();
    }

    public JMenuBar getApplicationMenu() {
        return applicationMenu;
    }

    private void newDocumentWindow(Document document, String filename) {
        SwingUtilities.invokeLater(() -> {
            DocumentWindow window = new DocumentWindow(document);
            window.setFilename(filename);
            window.setVisible(true);
        });
    }

    public void notifyWindowActivation(DocumentWindow window) {
        activeWindow = window;
        rebuildWindowMenu();
    }

    public void notifyWindowOpen(DocumentWindow window) {
        windowsPool.add(window);
        rebuildWindowMenu();
    }

    public void notifyWindowClose(DocumentWindow window) {
        windowsPool.remove(window);

        if (windowsPool.size() == 0) {
            activeWindow = null;

            if (OperatingSystem.getOS() != OperatingSystem.OS_X) {
                onQuit();
            }
        }

        rebuildWindowMenu();
    }

    public void rebuildWindowMenu() {
        windowMenu.removeAll();

        for (DocumentWindow window : windowsPool) {
            String title = window.getDocument().getName();
            JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(title);

            menuItem.addActionListener(e -> {
                checkedWindowMenuItem.setState(false);
                checkedWindowMenuItem = menuItem;
                window.toFront();
            });

            if (window == activeWindow) {
                menuItem.setState(true);
                checkedWindowMenuItem = menuItem;
            }

            windowMenu.add(menuItem);
        }
    }

    @Override
    public void onFileNew() {
        newDocumentWindow(new Document(), null);
    }

    @Override
    public void onFileOpen() {
        String filename = UIFactory.getInstance().getFilePicker().showLoadDialog();
        if (filename == null) {
            return;
        }

        Document document = DocumentSerializer.load(filename);
        newDocumentWindow(document, filename);
    }

    private void saveDocumentWindow(DocumentWindow window) {
        String filename = window.getFilename();

        if (filename == null) {
            onFileSaveAs();
        } else {
            Document document = window.getDocument();
            DocumentSerializer.save(document, filename);
        }
    }

    @Override
    public void onFileSave() {
        DocumentWindow window = activeWindow;
        if (window == null) {
            return;
        }

        saveDocumentWindow(window);
    }

    @Override
    public void onFileSaveAs() {
        DocumentWindow window = activeWindow;
        if (window == null) {
            return;
        }

        String filename = UIFactory.getInstance().getFilePicker().showSaveDialog();
        if (filename == null) {
            return;
        }

        Document document = window.getDocument();
        DocumentSerializer.save(document, filename);

        window.setFilename(filename);
        window.updateTitle();
    }

    @Override
    public void onFileSaveAll() {
        windowsPool.forEach(this::saveDocumentWindow);
    }

    @Override
    public void onFileClose() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.dispatchEvent(new WindowEvent(activeWindow, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void onQuit() {
        System.exit(0);
    }

    @Override
    public void onExportToHTML() {
        if (activeWindow == null) {
            return;
        }

        Document document = activeWindow.getDocument();

        String filename = UIFactory.getInstance().getFilePicker().showSaveHTMLDialog();
        if (filename == null) {
            return;
        }

        String html = HTMLExporter.toHTML(document);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(html);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not export HTML", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onExportToLaTeX() {
        if (activeWindow == null) {
            return;
        }

        Document document = activeWindow.getDocument();

        String filename = UIFactory.getInstance().getFilePicker().showSaveLaTeXDialog();
        if (filename == null) {
            return;
        }

        String tex = LaTeXExporter.toLaTeX(document);

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(tex);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not export LaTeX", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(null, "Generate PDF?", "Export to LaTeX", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            Path path = Paths.get(filename);
            String directory = path.getParent().toString();

            LaTeXRunner compiler = LaTeXRunner.getRunner();
            compiler.run(filename, directory);
        }
    }

    @Override
    public void onUndo() {

    }

    @Override
    public void onRedo() {

    }

    @Override
    public void onCut() {
        if (activeWindow == null) {
            return;
        }

        clipboard = activeWindow.getEditorPane().cutSelection();
    }

    @Override
    public void onCopy() {
        if (activeWindow == null) {
            return;
        }

        clipboard = activeWindow.getEditorPane().copySelection();
    }

    @Override
    public void onPaste() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.getEditorPane().pasteClipboard(clipboard);
    }

    @Override
    public void onAbout() {
        JDialog about = new AboutDialog();
        about.setVisible(true);
    }

    @Override
    public void onToggleFirstLineIndent() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.getEditorPane().toggleCurrentParagraphIndent();
    }

    @Override
    public void notifyWindowMenuReference(JMenu windowMenu) {
        this.windowMenu = windowMenu;
    }

    @Override
    public void onPublishToCloud() {
        if (activeWindow == null) {
            return;
        }

        String html = HTMLExporter.toHTML(activeWindow.getDocument());

        String url = EditorCloudAPI.publishDocument(html);
        if (url == null) {
            return;
        }

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "Internal error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JTextArea textArea = new JTextArea(url);
            textArea.setEditable(false);
            JOptionPane.showMessageDialog(null, textArea, "URL", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void onLeftAlignParagraph() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.getEditorPane().leftAlignCurrentParagraph();
    }

    @Override
    public void onRightAlignParagraph() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.getEditorPane().rightAlignCurrentParagraph();
    }

    @Override
    public void onCenterParagraph() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.getEditorPane().centerCurrentParagraph();
    }

    @Override
    public void onJustifyParagraph() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.getEditorPane().justifyCurrentParagraph();
    }

    @Override
    public void onMakeNormal() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.getEditorPane().makeSelectionNormal();
    }

    @Override
    public void onMakeBold() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.getEditorPane().makeSelectionBold();
    }

    @Override
    public void onMakeItalic() {
        if (activeWindow == null) {
            return;
        }

        activeWindow.getEditorPane().makeSelectionItalic();
    }
}
