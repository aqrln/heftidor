package ninja.aqrln.editor.dom.core;

import ninja.aqrln.editor.dom.model.RootElement;
import ninja.aqrln.editor.dom.model.CharacterElement;
import ninja.aqrln.editor.dom.model.ParagraphElement;
import ninja.aqrln.editor.dom.viewmodel.LineElement;
import ninja.aqrln.editor.dom.viewmodel.PageElement;
import ninja.aqrln.editor.dom.viewmodel.WordElement;

/**
 * @author Alexey Orlenko
 */
public interface DOMVisitor {
    void visitCharacterElement(CharacterElement element);
    void visitParagraphElement(ParagraphElement element);
    void visitLineElement(LineElement element);
    void visitPageElement(PageElement element);
    void visitRootElement(RootElement element);
    void visitWordElement(WordElement wordElement);
    void visitSpaceElement();
}
