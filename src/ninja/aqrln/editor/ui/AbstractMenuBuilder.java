package ninja.aqrln.editor.ui;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public abstract class AbstractMenuBuilder {
    public abstract void addSubMenu(JMenu menu);
    public abstract void buildMenuItem(String name, KeyStroke keyStroke, int mnemonic);
    public abstract void buildSeparator();

    private MnemonicAdditionStrategy mnemonicStrategy;

    public void setMnemonicAdditionStrategy(MnemonicAdditionStrategy strategy) {
        mnemonicStrategy = strategy;
    }

    protected void setupMenuItem(JMenuItem item, KeyStroke keyStroke, int mnemonic) {
        if (keyStroke != null) {
            item.setAccelerator(keyStroke);
        }

        mnemonicStrategy.setMnemonic(item, mnemonic);
    }
}
