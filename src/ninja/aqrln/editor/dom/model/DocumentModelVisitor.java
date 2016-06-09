package ninja.aqrln.editor.dom.model;

/**
 * Visitor of the document model tree
 * @author Alexey Orlenko
 */
public interface DocumentModelVisitor {

    /**
     * Visits character element
     * @param element character to be visited
     */
    void visitCharacterElement(CharacterElement element);

    /**
     * Visits paragraph element
     * @param element paragraph to be visited
     */
    void visitParagraphElement(ParagraphElement element);

    /**
     * Visits root element
     * @param element root element to be visited
     */
    void visitRootElement(RootElement element);
}
