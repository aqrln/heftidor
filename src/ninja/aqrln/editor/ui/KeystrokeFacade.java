package ninja.aqrln.editor.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @author Alexey Orlenko
 */
public class KeystrokeFacade {
    public static KeyStroke getMenuShortcut(int key) {
        return KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
    }

    public static KeyStroke getShiftedMenuShortcut(int key) {
        int mask = InputEvent.SHIFT_MASK | Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        return KeyStroke.getKeyStroke(key, mask);
    }
}
