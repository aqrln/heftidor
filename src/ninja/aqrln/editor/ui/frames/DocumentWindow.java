package ninja.aqrln.editor.ui.frames;

import ninja.aqrln.editor.ui.ApplicationUI;
import ninja.aqrln.editor.util.OperatingSystem;

import javax.swing.*;

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
    }
}
