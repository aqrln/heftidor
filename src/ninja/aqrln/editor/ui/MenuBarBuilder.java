package ninja.aqrln.editor.ui;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Alexey Orlenko
 */
public class MenuBarBuilder extends AbstractMenuBuilder {
    private JMenuBar menuBar;

    public void buildMenuBar() {
        menuBar = new JMenuBar();
    }

    @Override
    public void addSubMenu(JMenu menu) {
        menuBar.add(menu);
    }

    @Override
    public void buildMenuItem(String name, KeyStroke keyStroke, int mnemonic, ActionListener listener) {
        JMenuItem item = createMenuItem(name, keyStroke, mnemonic, listener);
        menuBar.add(item);
    }

    @Override
    public void buildSeparator() {
        JSeparator separator = new JSeparator(JSeparator.VERTICAL);
        menuBar.add(separator);
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }
}
