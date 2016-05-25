package ninja.aqrln.editor.dom.core;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public interface Element extends Serializable {
    List<Element> getChildren();
    void setStyle(Style style);
    Style getStyle();
    ListIterator<Element> getFlatIterator();
}
