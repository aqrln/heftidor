package ninja.aqrln.editor.dom.core;

import java.awt.Dimension;
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
    void accept(DOMVisitor visitor);
}
