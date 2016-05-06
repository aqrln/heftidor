package ninja.aqrln.editor.ui;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class WindowsUIFactory extends UIFactory {
    private static WindowsUIFactory instance;

    public static WindowsUIFactory getInstance() {
        if (instance == null) {
            instance = new WindowsUIFactory();
        }

        return instance;
    }

    @Override
    public JScrollPane createScrollPane() {
        JScrollPane pane = new JScrollPane();
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return pane;
    }

    @Override
    public MenuBuilder createMenuBuilder() {
        MenuBuilder builder = new MenuBuilder();
        builder.setMnemonicAdditionStrategy(WindowsMnemonicAdditionStrategy.getInstance());

        return builder;
    }

    @Override
    public MenuBarBuilder createMenuBarBuilder() {
        MenuBarBuilder builder = new MenuBarBuilder();
        builder.setMnemonicAdditionStrategy(WindowsMnemonicAdditionStrategy.getInstance());

        return builder;
    }

    private WindowsUIFactory() {
    }
}
