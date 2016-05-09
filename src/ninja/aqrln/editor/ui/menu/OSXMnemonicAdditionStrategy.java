package ninja.aqrln.editor.ui.menu;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class OSXMnemonicAdditionStrategy implements MnemonicAdditionStrategy {
    private static OSXMnemonicAdditionStrategy instance;

    public static OSXMnemonicAdditionStrategy getInstance() {
        if (instance == null) {
            instance = new OSXMnemonicAdditionStrategy();
        }

        return instance;
    }

    private OSXMnemonicAdditionStrategy() {
    }

    @Override
    public void setMnemonic(JMenuItem target, int mnemonic) {
        // do nothing
    }
}
