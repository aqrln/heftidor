package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;
import java.util.List;

/**
 * @author Alexey Orlenko
 */
public abstract class DocumentViewModelCompositeElement extends CompositeElement implements DocumentViewModelElement {
    private Dimension size;

    @Override
    public Dimension getSize() {
        if (size == null) {
            size = calculateSize();
        }

        return size;
    }

    @Override
    public List<Element> getChildren() {
        List<Element> children = super.getChildren();
        size = null;
        return children;
    }

    protected abstract Dimension calculateSize();
}
