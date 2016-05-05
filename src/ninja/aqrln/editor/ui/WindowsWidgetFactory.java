package ninja.aqrln.editor.ui;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class WindowsWidgetFactory extends WidgetFactory {
    private static WindowsWidgetFactory instance;

    public static WindowsWidgetFactory getInstance() {
        if (instance == null) {
            instance = new WindowsWidgetFactory();
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

    private WindowsWidgetFactory() { }
}
