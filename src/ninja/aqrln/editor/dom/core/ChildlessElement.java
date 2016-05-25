package ninja.aqrln.editor.dom.core;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public abstract class ChildlessElement implements Element {
    @Override
    public List<Element> getChildren() {
        return new LinkedList<>();
    }

    @Override
    public ListIterator<Element> getFlatIterator() {
        return getChildren().listIterator();
    }
}
