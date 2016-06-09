package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Style;

import java.awt.Dimension;

/**
 * Space element
 * @author Alexey Orlenko
 */
public class SpaceElement extends DocumentViewModelChildlessElement {
    private int width;
    private Style style;

    /**
     * @param width width of the spacing
     */
    public SpaceElement(int width) {
        super();
        this.width = width;
        this.style = Style.DEFAULT_STYLE;
    }

    /**
     * @param style style of the surrounding text
     */
    public SpaceElement(Style style) {
        super();
        int width = style.getFont().getSize() / 2;
        this.width = width;
        this.style = style;
    }

    /**
     * Constructor with default style
     */
    public SpaceElement() {
        this(Style.DEFAULT_STYLE);
    }

    /**
     * Get width of the spacing
     * @return width of the gap
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set width of the spacing
     * @param width size of the gap
     */
    public void setWidth(int width) {
        this.width = width;
        getViewContext().setSize(null);
    }

    @Override
    public Dimension calculateSize() {
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
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitSpaceElement(this);
    }
}
