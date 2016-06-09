package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.ChildlessElement;

/**
 * Abstract document model element that has no children
 * @author Alexey Orlenko
 */
public abstract class DocumentModelChildlessElement extends ChildlessElement
        implements DocumentModelElement, Cloneable {
    @Override
    public DocumentModelChildlessElement clone() {
        try {
            return (DocumentModelChildlessElement) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
