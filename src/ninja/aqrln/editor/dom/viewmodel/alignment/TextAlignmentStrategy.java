package ninja.aqrln.editor.dom.viewmodel.alignment;

import ninja.aqrln.editor.dom.viewmodel.LineElement;

/**
 * Abstract text alignment strategy
 * @author Alexey Orlenko
 */
public interface TextAlignmentStrategy {

    /**
     * Align a line of text
     * @param line line element
     */
    void align(LineElement line);
}
