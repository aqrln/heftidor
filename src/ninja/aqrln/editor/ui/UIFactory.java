package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.ui.dialogs.FilePicker;
import ninja.aqrln.editor.ui.menu.ApplicationMenuDirector;
import ninja.aqrln.editor.ui.menu.ApplicationMenuListener;
import ninja.aqrln.editor.ui.menu.MenuBarBuilder;
import ninja.aqrln.editor.ui.menu.MenuBuilder;
import ninja.aqrln.editor.util.OperatingSystem;

/**
 * @author Alexey Orlenko
 */
public abstract class UIFactory {
    private static UIFactory instance;

    public static UIFactory getInstance() {
        if (instance != null) {
            return instance;
        }

        if (OperatingSystem.getOS() == OperatingSystem.OS_X) {
            instance = OSXUIFactory.getInstance();
        } else {
            instance = WindowsUIFactory.getInstance();
        }

        switch (OperatingSystem.getOS()) {
            case OS_X:
                instance = OSXUIFactory.getInstance();
                break;
            case WINDOWS:
                instance = WindowsUIFactory.getInstance();
                break;
            case LINUX:
            case UNKNOWN:
                instance = LinuxUIFactory.getInstance();
                break;
        }

        return instance;
    }

    public abstract MenuBuilder createMenuBuilder();

    public abstract MenuBarBuilder createMenuBarBuilder();

    public abstract ApplicationMenuDirector createApplicationMenuDirector(ApplicationMenuListener listener);

    public abstract FilePicker getFilePicker();
}
