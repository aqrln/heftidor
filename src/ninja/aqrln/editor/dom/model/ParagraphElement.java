package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.DOMVisitor;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Alexey Orlenko
 */
public class ParagraphElement extends CompositeElement {
    @Override
    public Dimension getSize() {
//        int width = 0;
//        int height = 0;
//
//        for (Element element : children) {
//            Dimension elementSize = element.getSize();
//            int elementWidth = (int)elementSize.getWidth();
//            int elementHeight = (int)elementSize.getHeight();
//
//            if (elementHeight > height) {
//                height = elementHeight;
//            }
//
//            width += elementWidth;
//        }
//
//        return new Dimension(width, height);
        return null;
    }

    @Override
    public void draw(Graphics2D graphics, int x, int y) {
//        for (Element element : children) {
//            element.draw(graphics, x, y);
//            x += element.getSize().getWidth();
//        }
    }

    @Override
    public void accept(DOMVisitor visitor) {
        visitor.visitParagraphElement(this);
    }
}
