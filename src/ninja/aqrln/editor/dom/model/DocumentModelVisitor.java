package ninja.aqrln.editor.dom.model;

/**
 * @author Alexey Orlenko
 */
public interface DocumentModelVisitor {
    void visitCharacterElement(CharacterElement element);
    void visitParagraphElement(ParagraphElement element);
    void visitRootElement(RootElement element);
}
