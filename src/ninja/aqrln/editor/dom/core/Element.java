package ninja.aqrln.editor.dom.core;

import java.io.Serializable;
import java.util.List;
import java.util.ListIterator;

/**
 * Abstract DOM element
 * @author Alexey Orlenko
 */
public interface Element extends Serializable {
    /**
     * Get the list of children
     * @return list of children
     */
    List<Element> getChildren();

    /**
     * Set the style of the element
     * @param style style of the element
     */
    void setStyle(Style style);

    /**
     * Get the style of the element
     * @return style of the element
     */
    Style getStyle();

    /**
     * Get a flat iterator that allows to iterate through the leaf nodes
     * @return a flat iterator instance
     */
    ListIterator<Element> getFlatIterator();

    /**
     * Get parent element
     * @return parent element
     */
    CompositeElement getParent();

    /**
     * Set parent element
     * @param parent parent element
     */
    void setParent(CompositeElement parent);
}
