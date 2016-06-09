package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.model.CharacterElement;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FontMetrics;

/**
 * Character view model element
 * @author Alexey Orlenko
 */
public class CharacterViewElement extends DocumentViewModelChildlessElement {
    /**
     * @param element original character element
     */
    public CharacterViewElement(CharacterElement element) {
        setOrigin(element);
    }

    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitCharacterElement(this);
    }

    @Override
    public Dimension calculateSize() {
        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(getOrigin().getStyle().getFont());

        int width = fontMetrics.charWidth(((CharacterElement) getOrigin()).getCharacter());
        int height = fontMetrics.getHeight();

        return new Dimension(width, height);
    }

    @Override
    public void setStyle(Style style) {
        getOrigin().setStyle(style);
    }

    @Override
    public Style getStyle() {
        return getOrigin().getStyle();
    }

    /**
     * Get original character
     * @return character that backs up the original character element
     */
    public char getCharacter() {
        return ((CharacterElement) getOrigin()).getCharacter();
    }
}
