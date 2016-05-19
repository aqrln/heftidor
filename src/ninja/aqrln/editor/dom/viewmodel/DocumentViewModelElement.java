package ninja.aqrln.editor.dom.viewmodel;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public interface DocumentViewModelElement {
    void accept(DocumentViewModelVisitor visitor);
    Dimension getSize();
}
