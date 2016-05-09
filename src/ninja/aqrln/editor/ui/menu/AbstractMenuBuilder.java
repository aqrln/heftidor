package ninja.aqrln.editor.ui.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.ActionListener;

/**
 * @author Alexey Orlenko
 */
public abstract class AbstractMenuBuilder {
    public abstract void addSubMenu(JMenu menu);
    public abstract void buildMenuItem(String name, KeyStroke keyStroke, int mnemonic, ActionListener listener);
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

    protected JMenuItem createMenuItem(String name, KeyStroke keyStroke, int mnemonic, ActionListener listener) {
        JMenuItem item = new JMenuItem(name);
        setupMenuItem(item, keyStroke, mnemonic);
        item.addActionListener(listener);

        return item;
    }
}
