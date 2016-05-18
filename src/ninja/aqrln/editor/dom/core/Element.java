package ninja.aqrln.editor.dom.core;

import ninja.aqrln.editor.dom.visitors.DOMVisitor;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public interface Element {
    ListIterator<Element> getListIterator();
    List<Element> getChildren();
    Dimension getSize();
    void setStyle(Style style);
    Style getStyle();
    void draw(Graphics2D graphics, int x, int y);
    void accept(DOMVisitor visitor);
}
