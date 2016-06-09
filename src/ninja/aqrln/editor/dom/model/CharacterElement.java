package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.Style;

/**
 * Document model element that represents a character
 * @author Alexey Orlenko
 */
public class CharacterElement extends DocumentModelChildlessElement {
    private char character;
    private Style style;

    /**
     * Public constructor
     * @param character character to be represented
     * @param style style of the character
     */
    public CharacterElement(char character, Style style) {
        this.character = character;
        this.style = style;
    }

    /**
     * Public constructor with default style
     * @param character character ot be represented
     */
    public CharacterElement(char character) {
        this(character, Style.DEFAULT_STYLE);
    }

    /**
     * @return represented character
     */
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
