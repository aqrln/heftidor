package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.DOMVisitor;
import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Alexey Orlenko
 */
public class RootElement extends CompositeElement {
    public static final int PAGE_SPACING = 10;

    @Override
    public Dimension getSize() {
        int width = 0;
        int height = 0;

        for (Element child : getChildren()) {
            height += PAGE_SPACING;

            Dimension size = child.getSize();

            if (size.width > width) {
                width = size.width;
            }

            height += size.height + PAGE_SPACING;
        }

        return new Dimension(width, height);
    }

    @Override
    public void draw(Graphics2D graphics, int x, int y) {
        for (Element child : getChildren()) {
            y += PAGE_SPACING;
            child.draw(graphics, x, y);
            y += child.getSize().height + PAGE_SPACING;
        }
    }

    @Override
    public void accept(DOMVisitor visitor) {
        visitor.visitRootElement(this);
    }
}
