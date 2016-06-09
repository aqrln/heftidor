package ninja.aqrln.editor.dom.model;

/**
 * Abstract document model element
 * @author Alexey Orlenko
 */
public interface DocumentModelElement {
    /**
     * Accepts visitor
     * @param visitor document model visitor to be accepted
     */
    void accept(DocumentModelVisitor visitor);
}
