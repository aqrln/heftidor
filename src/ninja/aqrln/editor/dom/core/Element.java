package ninja.aqrln.editor.dom.core;

import java.util.List;

/**
 * @author Alexey Orlenko
 */
public interface Element {
    List<Element> getChildren();
    void setStyle(Style style);
    Style getStyle();
}
