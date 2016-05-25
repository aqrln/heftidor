package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.ChildlessElement;
import ninja.aqrln.editor.dom.model.DocumentModelChildlessElement;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public abstract class DocumentViewModelChildlessElement extends ChildlessElement implements DocumentViewModelElement {
    private ViewContext context = new ViewContext();

    private DocumentModelChildlessElement origin;

    public DocumentModelChildlessElement getOrigin() {
        return origin;
    }

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

    public ViewContext getViewContext() {
        return context;
    }

    protected abstract Dimension calculateSize();
}
