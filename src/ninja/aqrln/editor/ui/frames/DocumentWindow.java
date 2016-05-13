package ninja.aqrln.editor.ui.frames;

import ninja.aqrln.editor.component.EditorPane;
import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.model.CharacterElement;
import ninja.aqrln.editor.dom.model.ParagraphElement;
import ninja.aqrln.editor.ui.ApplicationUI;
import ninja.aqrln.editor.util.OperatingSystem;

import javax.swing.JFrame;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public class DocumentWindow extends JFrame {
    public DocumentWindow() {
        super("Untitled — Editor");

        setSize(800, 600);
        setLocationRelativeTo(null);

        if (OperatingSystem.getOS() != OperatingSystem.OS_X) {
            setJMenuBar(ApplicationUI.getInstance().getApplicationMenu());
        }

        initializeComponents();
    }

    private void initializeComponents() {
        ParagraphElement text = new ParagraphElement();
        ListIterator<Element> iter = text.getListIterator();
        for (char c : "Hello World!".toCharArray()) {
            CharacterElement charElem = new CharacterElement(c);
            iter.add(charElem);
        }
        EditorPane editor = new EditorPane(text);

        add(editor);
        pack();
        repaint();
    }
}
