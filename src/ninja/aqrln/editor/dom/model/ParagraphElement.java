package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;
import java.awt.Graphics;

/**
 * @author Alexey Orlenko
 */
public class ParagraphElement extends CompositeElement {
    @Override
    public Dimension getSize() {
        int width = 0;
        int height = 0;

        for (Element element : children) {
            Dimension elementSize = element.getSize();
            int elementWidth = (int)elementSize.getWidth();
            int elementHeight = (int)elementSize.getHeight();

            if (elementHeight > height) {
                height = elementHeight;
            }

            width += elementWidth;
        }

        return new Dimension(width, height);
    }

    @Override
    public void draw(Graphics graphics, int x, int y) {
        for (Element element : children) {
            element.draw(graphics, x, y);
            x += element.getSize().getWidth();
        }
    }
}
