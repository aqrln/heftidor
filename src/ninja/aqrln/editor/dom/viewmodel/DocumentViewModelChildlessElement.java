package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.ChildlessElement;
import ninja.aqrln.editor.dom.model.DocumentModelChildlessElement;

import java.awt.Dimension;

/**
 * Abstract document view model element that has no children
 * @author Alexey Orlenko
 */
public abstract class DocumentViewModelChildlessElement extends ChildlessElement implements DocumentViewModelElement {
    private ViewContext context = new ViewContext();

    private DocumentModelChildlessElement origin;

    /**
     * Get original element
     * @return document model element
     */
    public DocumentModelChildlessElement getOrigin() {
        return origin;
    }

    /**
     * Set original element
     * @param origin document model element
     */
    public void setOrigin(DocumentModelChildlessElement origin) {
        this.origin = origin;
    }

    @Override
    public Dimension getSize() {
        if (context.getSize() == null) {
            context.setSize(calculateSize());
        }

        return context.getSize();
    }

    /**
     * Get view context of this element
     * @return view context
     */
    public ViewContext getViewContext() {
        return context;
    }

    /**
     * Calculate the size of this element on the screen.
     * This method is invoked when context.size is null.
     *
     * @return calculated size
     */
    protected abstract Dimension calculateSize();
}
