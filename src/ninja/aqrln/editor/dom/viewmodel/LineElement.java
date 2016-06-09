package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.core.FlatIterator;

import java.awt.Dimension;
import java.util.ListIterator;

/**
 * Line element
 * @author Alexey Orlenko
 */
public class LineElement extends DocumentViewModelCompositeElement {
    private boolean firstLineInParagraph = false;
    private boolean lastLineInParagraph = false;

    /**
     * @return true if this line is last in the paragraph, false otherwise
     */
    public boolean isLastLine() {
        return lastLineInParagraph;
    }

    /**
     * Sets whether this line is last in paragraph
     * @param isLastLine if the line is last in paragraph
     */
    public void setLastLine(boolean isLastLine) {
        lastLineInParagraph = isLastLine;
    }

    /**
     * @return true if this line is first in the paragraph, false otherwise
     */
    public boolean isFirstLine() {
        return firstLineInParagraph;
    }

    /**
     * Sets whether this line is first in paragraph
     * @param isFirstLine if the line is first in paragraph
     */
    public void setFirstLine(boolean isFirstLine) {
        firstLineInParagraph = isFirstLine;
    }

    @Override
    public Dimension calculateSize() {
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

    @Override
    public ListIterator<Element> getFlatIterator() {
        return new FlatIterator(this);
    }
}
