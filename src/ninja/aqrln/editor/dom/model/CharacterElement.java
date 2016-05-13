package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.ChildlessElement;
import ninja.aqrln.editor.dom.core.Style;

import java.awt.Dimension;

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
        return null;
    }

    @Override
    public void setStyle(Style style) {
        this.style = style;
    }

    @Override
    public Style getStyle() {
        return style;
    }
}
