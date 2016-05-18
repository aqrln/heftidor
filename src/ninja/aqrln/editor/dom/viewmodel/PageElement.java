package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.DOMVisitor;
import ninja.aqrln.editor.dom.core.Element;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Alexey Orlenko
 */
public class PageElement extends CompositeElement {
    public static final Dimension PAGE_SIZE = new Dimension(400, 500);
    public static final int PADDING_TOP = 30;
    public static final int PADDING_LEFT = 30;
    public static final int PADDING_BOTTOM = 30;
    public static final int PADDING_RIGHT = 30;

    @Override
    public Dimension getSize() {
        return PAGE_SIZE;
    }

    @Override
    public void draw(Graphics2D graphics, int x, int y) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(x, y, PAGE_SIZE.width, PAGE_SIZE.height);

        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, PAGE_SIZE.width, PAGE_SIZE.height);

        x += PADDING_LEFT;
        y += PADDING_TOP;

        for (Element child : getChildren()) {
            child.draw(graphics, x, y);
            y += child.getSize().height;
        }
    }

    @Override
    public void accept(DOMVisitor visitor) {
        visitor.visitPageElement(this);
    }

    public int getContentHeight() {
        int height = 0;

        for (Element child : this.getChildren()) {
            height += child.getSize().height;
        }

        return height;
    }
}
