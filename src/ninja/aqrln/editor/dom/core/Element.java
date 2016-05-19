package ninja.aqrln.editor.dom.core;

import java.util.List;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public interface Element {
    ListIterator<Element> getListIterator();
    List<Element> getChildren();
    void setStyle(Style style);
    Style getStyle();
}
