package ninja.aqrln.editor.dom.core;

import java.io.Serializable;
import java.util.List;

/**
 * @author Alexey Orlenko
 */
public interface Element extends Serializable {
    List<Element> getChildren();
    void setStyle(Style style);
    Style getStyle();
}
