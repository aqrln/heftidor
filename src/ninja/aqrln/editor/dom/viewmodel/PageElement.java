package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.DOMVisitor;
import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Alexey Orlenko
 */
public class PageElement extends CompositeElement {
    public static final Dimension PAGE_SIZE = new Dimension(800, 1000);
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
