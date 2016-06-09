package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;
import java.util.List;

/**
 * Abstract document view model composite element
 * @author Alexey Orlenko
 */
public abstract class DocumentViewModelCompositeElement extends CompositeElement implements DocumentViewModelElement {
    private ViewContext context = new ViewContext();

    @Override
    public Dimension getSize() {
        if (context.getSize() == null) {
            context.setSize(calculateSize());
        }

        return context.getSize();
    }

    /**
     * Get view context
     * @return view context
     */
    public ViewContext getViewContext() {
        return context;
    }

    @Override
    public List<Element> getChildren() {
        List<Element> children = super.getChildren();
        context.setSize(null);
        return children;
    }

    protected abstract Dimension calculateSize();
}
