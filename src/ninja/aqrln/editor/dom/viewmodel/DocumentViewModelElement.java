package ninja.aqrln.editor.dom.viewmodel;

import java.awt.Dimension;

/**
 * Abstract document view model element
 * @author Alexey Orlenko
 */
public interface DocumentViewModelElement {

    /**
     * Accept a visitor
     * @param visitor document view model tree visitor
     */
    void accept(DocumentViewModelVisitor visitor);

    /**
     * Get cached size (or calculate if it hasn't been yet)
     * @return cached size
     */
    Dimension getSize();

    /**
     * Get view context of the element
     * @return view context
     */
    ViewContext getViewContext();
}
