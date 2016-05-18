package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.model.RootElement;

/**
 * @author Alexey Orlenko
 */
public interface DocumentViewModelVisitor {
    void visitCharacterElement(CharacterViewElement element);
    void visitLineElement(LineElement element);
    void visitPageElement(PageElement element);
    void visitRootElement(ComposedRootElement element);
    void visitWordElement(WordElement element);
    void visitSpaceElement(SpaceElement element);
}
