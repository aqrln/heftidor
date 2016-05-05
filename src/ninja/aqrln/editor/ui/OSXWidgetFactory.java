package ninja.aqrln.editor.ui;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class OSXWidgetFactory extends WidgetFactory {
    private static OSXWidgetFactory instance;

    public static OSXWidgetFactory getInstance() {
        if (instance == null) {
            instance = new OSXWidgetFactory();
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

    private OSXWidgetFactory() { }
}
