package ninja.aqrln.editor.dom.core;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Alexey Orlenko
 */
public abstract class ChildlessElement implements Element {
    @Override
    public List<Element> getChildren() {
        return new LinkedList<>();
    }
}
