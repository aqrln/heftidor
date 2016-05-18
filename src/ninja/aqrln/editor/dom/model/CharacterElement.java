package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.Style;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FontMetrics;

/**
 * @author Alexey Orlenko
 */
public class CharacterElement extends DocumentModelChildlessElement {
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

    public char getCharacter() {
        return character;
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
    public void accept(DocumentModelVisitor visitor) {
        visitor.visitCharacterElement(this);
    }
}
