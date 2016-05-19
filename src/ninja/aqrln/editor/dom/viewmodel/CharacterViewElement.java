package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.model.CharacterElement;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FontMetrics;

/**
 * @author Alexey Orlenko
 */
public class CharacterViewElement extends DocumentViewModelChildlessElement {
    private CharacterElement characterElement;

    public CharacterViewElement(CharacterElement element) {
        this.characterElement = element;
    }

    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitCharacterElement(this);
    }

    @Override
    public Dimension getSize() {
        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(characterElement.getStyle().getFont());

        int width = fontMetrics.charWidth(characterElement.getCharacter());
        int height = fontMetrics.getHeight();

        return new Dimension(width, height);
    }

    @Override
    public void setStyle(Style style) {
        characterElement.setStyle(style);
    }

    @Override
    public Style getStyle() {
        return characterElement.getStyle();
    }

    public char getCharacter() {
        return characterElement.getCharacter();
    }
}
