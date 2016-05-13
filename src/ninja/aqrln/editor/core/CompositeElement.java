package ninja.aqrln.editor.core;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public abstract class CompositeElement implements Element {
    protected List<Element> children;

    @Override
    public ListIterator<Element> getListIterator() {
        return children.listIterator();
    }

    public CompositeElement() {
        children = new LinkedList<>();
    }

    @Override
    public void setStyle(Style style) {
        for (Element element : children) {
            element.setStyle(style);
        }
    }

    @Override
    public Style getStyle() {
        if (children.size() == 0) {
            return Style.DEFAULT_STYLE;
        }

        return children.get(children.size() - 1).getStyle();
    }
}
