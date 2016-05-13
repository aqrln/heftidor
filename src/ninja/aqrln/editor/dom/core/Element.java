package ninja.aqrln.editor.dom.core;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public interface Element {
    ListIterator<Element> getListIterator();
    Dimension getSize();
    void setStyle(Style style);
    Style getStyle();
    void draw(Graphics2D graphics, int x, int y);
}
