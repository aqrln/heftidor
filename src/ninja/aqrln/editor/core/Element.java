package ninja.aqrln.editor.core;

import java.awt.*;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public abstract class Element {
    protected java.util.List<Element> elements;

    public abstract void draw(Graphics graphics);

    public abstract Dimension getSize();

    public abstract ListIterator<Element> getIterator();
}
