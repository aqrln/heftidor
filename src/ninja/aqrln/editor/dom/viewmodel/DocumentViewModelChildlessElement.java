package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.ChildlessElement;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public abstract class DocumentViewModelChildlessElement extends ChildlessElement implements DocumentViewModelElement {
    private ViewContext context = new ViewContext();

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
