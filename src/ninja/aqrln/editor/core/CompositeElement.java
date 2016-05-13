package ninja.aqrln.editor.core;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public abstract class CompositeElement implements Element {
    protected List<Element> children;

    public ListIterator<Element> getListIterator() {
        return children.listIterator();
    }

    public CompositeElement() {
        children = new LinkedList<>();
    }
}
