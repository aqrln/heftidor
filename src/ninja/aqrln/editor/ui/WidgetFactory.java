package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.util.OperatingSystem;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public abstract class WidgetFactory {
    private static WidgetFactory instance;

    public static WidgetFactory getInstance() {
        if (instance != null) {
            return instance;
        }

        if (OperatingSystem.getOS() == OperatingSystem.OS_X) {
            instance = OSXWidgetFactory.getInstance();
        } else {
            instance = WindowsWidgetFactory.getInstance();
        }

        return instance;
    }

    public abstract JScrollPane createScrollPane();
}
