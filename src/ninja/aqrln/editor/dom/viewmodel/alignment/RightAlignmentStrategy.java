package ninja.aqrln.editor.dom.viewmodel.alignment;

import ninja.aqrln.editor.dom.viewmodel.AlignmentHelperElement;
import ninja.aqrln.editor.dom.viewmodel.LineElement;
import ninja.aqrln.editor.dom.viewmodel.PageElement;

/**
 * @author Alexey Orlenko
 */
public class RightAlignmentStrategy implements TextAlignmentStrategy {
    @Override
    public void align(LineElement line) {
        int maxLineWidth = PageElement.CONTENT_WIDTH;
        int lineWidth = line.getSize().width;

        int spacing = maxLineWidth - lineWidth;
        AlignmentHelperElement padding = new AlignmentHelperElement(spacing);

        line.getChildren().add(0, padding);
    }
}
