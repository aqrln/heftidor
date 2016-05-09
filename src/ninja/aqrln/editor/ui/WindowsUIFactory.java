package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.ui.menu.ApplicationMenuDirector;
import ninja.aqrln.editor.ui.menu.ApplicationMenuListener;
import ninja.aqrln.editor.ui.menu.MenuBarBuilder;
import ninja.aqrln.editor.ui.menu.MenuBuilder;
import ninja.aqrln.editor.ui.menu.WindowsApplicationMenuDirector;
import ninja.aqrln.editor.ui.menu.WindowsMnemonicAdditionStrategy;

import javax.swing.JScrollPane;

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

    @Override
    public ApplicationMenuDirector createApplicationMenuDirector(ApplicationMenuListener listener) {
        return new WindowsApplicationMenuDirector(listener);
    }

    private WindowsUIFactory() {
    }
}
