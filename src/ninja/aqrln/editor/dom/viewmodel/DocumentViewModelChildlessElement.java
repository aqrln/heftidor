package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.ChildlessElement;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public abstract class DocumentViewModelChildlessElement extends ChildlessElement implements DocumentViewModelElement {
    private Dimension size;

    @Override
    public Dimension getSize() {
        if (size == null) {
            size = calculateSize();
        }

        return size;
    }

    protected abstract Dimension calculateSize();
}
