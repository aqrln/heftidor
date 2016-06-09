package ninja.aqrln.editor.dom.core;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstract composite element
 * @author Alexey Orlenko
 */
public abstract class CompositeElement implements Element {
    protected List<Element> children;

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
        return children;
    }

    /**
     * Public constructor
     */
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

    /**
     * Add a new child
     * @param element child to be added
     */
    public void addChild(Element element) {
        element.setParent(this);
        getChildren().add(element);
    }
}
