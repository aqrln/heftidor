package ninja.aqrln.editor.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Alexey Orlenko
 */
public class MenuBuilder extends AbstractMenuBuilder {
    private JMenu menu;

    public void buildMenu(String name, int mnemonic) {
        menu = new JMenu(name);
        setupMenuItem(menu, null, mnemonic);
    }

    @Override
    public void addSubMenu(JMenu menu) {
        menu.add(menu);
    }

    @Override
    public void buildMenuItem(String name, KeyStroke keyStroke, int mnemonic, ActionListener listener) {
        JMenuItem item = createMenuItem(name, keyStroke, mnemonic, listener);
        menu.add(item);
    }

    @Override
    public void buildSeparator() {
        menu.addSeparator();
    }

    public JMenu getMenu() {
        return menu;
    }
}
