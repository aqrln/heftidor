package ninja.aqrln.editor.dom.viewmodel.alignment;

import ninja.aqrln.editor.dom.viewmodel.LineElement;

/**
 * @author Alexey Orlenko
 */
public interface TextAlignmentStrategy {
    void align(LineElement line);
}
