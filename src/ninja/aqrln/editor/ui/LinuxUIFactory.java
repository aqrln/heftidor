package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.ui.dialogs.FilePicker;
import ninja.aqrln.editor.ui.dialogs.UnixFilePicker;
import ninja.aqrln.editor.ui.menu.*;

/**
 * @author Alexey Orlenko
 */
public class LinuxUIFactory extends UIFactory {
    private static LinuxUIFactory instance;

    public static LinuxUIFactory getInstance() {
        if (instance == null) {
            instance = new LinuxUIFactory();
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
        return UnixFilePicker.getInstance();
    }

    private LinuxUIFactory() {
    }
}
