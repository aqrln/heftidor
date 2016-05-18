package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.ChildlessElement;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.core.DOMVisitor;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * @author Alexey Orlenko
 */
public class CharacterElement extends ChildlessElement {
    private char character;
    private Style style;

    public CharacterElement(char character, Style style) {
        this.character = character;
        this.style = style;
    }

    public CharacterElement(char character) {
        this(character, Style.DEFAULT_STYLE);
    }

    @Override
    public Dimension getSize() {
        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(style.getFont());

        int width = fontMetrics.charWidth(character);
        int height = fontMetrics.getHeight();

        return new Dimension(width, height);
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
        int width = (int)size.getWidth();
        int height = (int)size.getHeight();

        graphics.setColor(style.getBackgroundColor());
        graphics.fillRect(x, y, width, height);

        graphics.setFont(style.getFont());
        graphics.setColor(style.getForegroundColor());
        graphics.drawString("" + character, x, y + height);
    }

    @Override
    public void accept(DOMVisitor visitor) {
        visitor.visitCharacterElement(this);
    }
}
