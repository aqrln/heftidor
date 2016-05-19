package ninja.aqrln.editor.ui.frames;

import ninja.aqrln.editor.component.EditorPane;
import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.ui.ApplicationUI;
import ninja.aqrln.editor.util.OperatingSystem;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Alexey Orlenko
 */
public class DocumentWindow extends JFrame implements WindowListener {
    private Document document;

    private String filename = null;

    public DocumentWindow(Document document) {
        super();
        addWindowListener(this);

        this.document = document;
        updateTitle();

        setPreferredSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        setJMenuBar(ApplicationUI.getInstance().getApplicationMenu());

        // If we are under OS X, the menu bar has to be set twice:
        // globally and for each window made invisible, since otherwise
        // the Swing windows steal accelerators and none of them except
        // Quit and About work
        if (OperatingSystem.getOS() == OperatingSystem.OS_X) {
            Dimension invisible = new Dimension(0, 0);
            getJMenuBar().setSize(invisible);
            getJMenuBar().setPreferredSize(invisible);
            getJMenuBar().setMaximumSize(invisible);
        }

        initializeComponents();
    }

    private void initializeComponents() {
        EditorPane editor = new EditorPane(document);
        JScrollPane scrollPane = new JScrollPane(editor);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);

        getContentPane().add(scrollPane);
        pack();
        repaint();
    }

    public void updateTitle() {
        setTitle(document.getName() + " — Editor");
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {
        ApplicationUI.getInstance().notifyWindowActivation(this);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
