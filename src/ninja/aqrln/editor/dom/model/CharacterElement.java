package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.Style;

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
