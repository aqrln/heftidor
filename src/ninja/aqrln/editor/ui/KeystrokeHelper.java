package ninja.aqrln.editor.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @author Alexey Orlenko
 */
public class KeystrokeHelper {
    private static KeyStroke getMaskedMenuShortcut(int key, int mask) {
        mask |= Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        return KeyStroke.getKeyStroke(key, mask);
    }

    public static KeyStroke getMenuShortcut(int key) {
        return getMaskedMenuShortcut(key, 0);
    }

    public static KeyStroke getShiftedMenuShortcut(int key) {
        return getMaskedMenuShortcut(key, InputEvent.SHIFT_MASK);
    }

    public static KeyStroke getAlternatedMenuShortcut(int key) {
        return getMaskedMenuShortcut(key, InputEvent.ALT_MASK);
    }
}
