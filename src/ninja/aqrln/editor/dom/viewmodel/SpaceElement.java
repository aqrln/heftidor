package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Style;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public class SpaceElement extends DocumentViewModelChildlessElement {
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
    public Dimension calculateSize() {
        return getSize();
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
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitSpaceElement(this);
    }
}
