package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.ChildlessElement;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.core.DOMVisitor;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Alexey Orlenko
 */
public class SpaceElement extends ChildlessElement {
    private int width;
    private Style style;

    public SpaceElement(int width) {
        super();
        this.width = width;
        this.style = Style.DEFAULT_STYLE;
    }

    public SpaceElement(Style style) {
        super();
        int width = style.getFont().getSize() / 2;
        this.width = width;
        this.style = style;
    }

    public SpaceElement() {
        this(Style.DEFAULT_STYLE);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(width, style.getFont().getSize());
    }

    @Override
    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public void draw(Graphics2D graphics, int x, int y) {
        Dimension size = getSize();

        graphics.setColor(style.getBackgroundColor());
        graphics.fillRect(x, y, size.width, size.height);
    }

    @Override
    public void accept(DOMVisitor visitor) {
        visitor.visitSpaceElement();
    }
}