package ninja.aqrln.editor.dom.core;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Abstract element that has no children
 * @author Alexey Orlenko
 */
public abstract class ChildlessElement implements Element {
    private CompositeElement parent;

    /**
     * Get parent element
     * @return parent element
     */
    public CompositeElement getParent() {
        return parent;
    }

    /**
     * Set parent element
     * @param parent parent element
     */
    public void setParent(CompositeElement parent) {
        this.parent = parent;
    }

    @Override
    public List<Element> getChildren() {
        return new LinkedList<>();
    }

    @Override
    public ListIterator<Element> getFlatIterator() {
        return getChildren().listIterator();
    }
}
