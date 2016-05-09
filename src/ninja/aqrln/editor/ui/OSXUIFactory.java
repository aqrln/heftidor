package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.ui.menu.ApplicationMenuDirector;
import ninja.aqrln.editor.ui.menu.ApplicationMenuListener;
import ninja.aqrln.editor.ui.menu.MenuBarBuilder;
import ninja.aqrln.editor.ui.menu.MenuBuilder;
import ninja.aqrln.editor.ui.menu.OSXApplicationMenuDirector;
import ninja.aqrln.editor.ui.menu.OSXMnemonicAdditionStrategy;

import javax.swing.JScrollPane;

/**
 * @author Alexey Orlenko
 */
public class OSXUIFactory extends UIFactory {
    private static OSXUIFactory instance;

    public static OSXUIFactory getInstance() {
        if (instance == null) {
            instance = new OSXUIFactory();
        }

        return instance;
    }

    @Override
    public JScrollPane createScrollPane() {
        JScrollPane pane = new JScrollPane();
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        return pane;
    }

    @Override
    public MenuBuilder createMenuBuilder() {
        MenuBuilder builder = new MenuBuilder();
        builder.setMnemonicAdditionStrategy(OSXMnemonicAdditionStrategy.getInstance());

        return builder;
    }

    @Override
    public MenuBarBuilder createMenuBarBuilder() {
        MenuBarBuilder builder = new MenuBarBuilder();
        builder.setMnemonicAdditionStrategy(OSXMnemonicAdditionStrategy.getInstance());

        return builder;
    }

    @Override
    public ApplicationMenuDirector createApplicationMenuDirector(ApplicationMenuListener listener) {
        return new OSXApplicationMenuDirector(listener);
    }

    private OSXUIFactory() {
    }
}
