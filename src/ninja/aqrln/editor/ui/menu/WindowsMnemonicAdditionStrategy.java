package ninja.aqrln.editor.ui.menu;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class WindowsMnemonicAdditionStrategy implements MnemonicAdditionStrategy {
    private static WindowsMnemonicAdditionStrategy instance;

    public static WindowsMnemonicAdditionStrategy getInstance() {
        if (instance == null) {
            instance = new WindowsMnemonicAdditionStrategy();
        }

        return instance;
    }

    private WindowsMnemonicAdditionStrategy() {
    }

    @Override
    public void setMnemonic(JMenuItem target, int mnemonic) {
        target.setMnemonic(mnemonic);
    }
}
