package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.ui.menu.ApplicationMenuDirector;
import ninja.aqrln.editor.ui.menu.ApplicationMenuListener;
import ninja.aqrln.editor.ui.menu.MenuBarBuilder;
import ninja.aqrln.editor.ui.menu.MenuBuilder;
import ninja.aqrln.editor.util.OperatingSystem;

import javax.swing.*;

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

        return instance;
    }

    public abstract JScrollPane createScrollPane();

    public abstract MenuBuilder createMenuBuilder();

    public abstract MenuBarBuilder createMenuBarBuilder();

    public abstract ApplicationMenuDirector createApplicationMenuDirector(ApplicationMenuListener listener);
}
