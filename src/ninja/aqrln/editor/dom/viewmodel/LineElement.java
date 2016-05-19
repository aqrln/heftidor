package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public class LineElement extends DocumentViewModelCompositeElement {
    private boolean lastLineInParagraph = false;

    public boolean isLastLine() {
        return lastLineInParagraph;
    }

    public void setLastLine(boolean isLastLine) {
        lastLineInParagraph = isLastLine;
    }

    @Override
    public Dimension getSize() {
        int width = 0;
        int height = 0;

        for (Element element : children) {
            Dimension elementSize = ((DocumentViewModelElement) element).getSize();
            int elementWidth = elementSize.width;
            int elementHeight = (int)(elementSize.height * 1.2);

            if (elementHeight > height) {
                height = elementHeight;
            }

            width += elementWidth;
        }

        if (lastLineInParagraph) {
            height *= 1.5;
        }

        return new Dimension(width, height);
    }

    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitLineElement(this);
    }
}
