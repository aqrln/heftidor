package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.DOMVisitor;
import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Alexey Orlenko
 */
public class LineElement extends CompositeElement {
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
            Dimension elementSize = element.getSize();
            int elementWidth = elementSize.width;
            int elementHeight = elementSize.height;

            if (elementHeight > height) {
                height = elementHeight;
            }

            width += elementWidth;
        }

        return new Dimension(width, height);
    }

    @Override
    public void draw(Graphics2D graphics, int x, int y) {

    }

    @Override
    public void accept(DOMVisitor visitor) {
        visitor.visitLineElement(this);
    }
}
