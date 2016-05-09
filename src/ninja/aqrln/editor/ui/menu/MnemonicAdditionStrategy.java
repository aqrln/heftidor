package ninja.aqrln.editor.ui.menu;

import javax.swing.JMenuItem;

/**
 * @author Alexey Orlenko
 */
public interface MnemonicAdditionStrategy {
    void setMnemonic(JMenuItem target, int mnemonic);
}
