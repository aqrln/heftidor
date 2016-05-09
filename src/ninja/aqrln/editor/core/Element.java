package ninja.aqrln.editor.core;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public abstract class Element {
    private List<Element> elements;
    private Style style;

    public abstract void draw(Graphics graphics);

    public abstract Dimension getSize();

    public ListIterator<Element> getIterator() {
        return elements.listIterator();
    }

    public List<Element> getElements() {
        return elements;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;

        for (Element element : elements) {
            element.setStyle(style);
        }
    }
}
