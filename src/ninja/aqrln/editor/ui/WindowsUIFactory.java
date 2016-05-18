package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.ui.menu.*;

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
