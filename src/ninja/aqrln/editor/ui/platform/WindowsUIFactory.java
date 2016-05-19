package ninja.aqrln.editor.ui.platform;

import ninja.aqrln.editor.ui.dialogs.FilePicker;
import ninja.aqrln.editor.ui.dialogs.WindowsFilePicker;
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

    @Override
    public FilePicker getFilePicker() {
        return WindowsFilePicker.getInstance();
    }

    private WindowsUIFactory() {
    }
}
