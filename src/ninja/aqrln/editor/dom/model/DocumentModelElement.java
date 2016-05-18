package ninja.aqrln.editor.dom.model;

/**
 * @author Alexey Orlenko
 */
public interface DocumentModelElement {
    void accept(DocumentModelVisitor visitor);
}
