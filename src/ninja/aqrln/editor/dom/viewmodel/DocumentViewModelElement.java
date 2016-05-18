package ninja.aqrln.editor.dom.viewmodel;

/**
 * @author Alexey Orlenko
 */
public interface DocumentViewModelElement {
    void accept(DocumentViewModelVisitor visitor);
}
