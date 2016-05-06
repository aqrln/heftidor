package ninja.aqrln.editor.ui;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class MenuBuilder extends AbstractMenuBuilder {
    private JMenu menu;

    public void buildMenu(String name, KeyStroke keyStroke, int mnemonic) {
        menu = new JMenu(name);
        setupMenuItem(menu, keyStroke, mnemonic);
    }

    @Override
    public void addSubMenu(JMenu menu) {
        menu.add(menu);
    }

    @Override
    public void buildMenuItem(String name, KeyStroke keyStroke, int mnemonic) {
        JMenuItem item = new JMenuItem(name);
        setupMenuItem(item, keyStroke, mnemonic);
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
