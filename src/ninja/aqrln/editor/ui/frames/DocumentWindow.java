package ninja.aqrln.editor.ui.frames;

import ninja.aqrln.editor.component.EditorPane;
import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.ui.ApplicationUI;
import ninja.aqrln.editor.util.OperatingSystem;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public class DocumentWindow extends JFrame {
    private Document document;

    public DocumentWindow(Document document) {
        super("Untitled — Editor");

        this.document = document;

        setPreferredSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        setJMenuBar(ApplicationUI.getInstance().getApplicationMenu());

        // If we are under OS X, the menu bar has to be set twice:
        // globally and for each window made invisible, since otherwise
        // the Swing windows steals accelerators and none of them except
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
}
