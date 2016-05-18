package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.model.CharacterElement;

/**
 * @author Alexey Orlenko
 */
public class CharacterViewElement extends CharacterElement implements DocumentViewModelElement {
    public CharacterViewElement(CharacterElement element) {
        super(element.getCharacter(), element.getStyle());
    }

    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitCharacterElement(this);
    }
}
