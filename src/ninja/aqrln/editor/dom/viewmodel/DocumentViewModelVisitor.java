package ninja.aqrln.editor.dom.viewmodel;

/**
 * Document view model tree visitor
 * @author Alexey Orlenko
 */
public interface DocumentViewModelVisitor {

    /**
     * Visit character view element
     * @param element character view element to visit
     */
    void visitCharacterElement(CharacterViewElement element);

    /**
     * Visit line element
     * @param element line element to visit
     */
    void visitLineElement(LineElement element);

    /**
     * Visit page element
     * @param element page element to visit
     */
    void visitPageElement(PageElement element);

    /**
     * Visit root element
     * @param element composed root element to visit
     */
    void visitRootElement(ComposedRootElement element);

    /**
     * Visit word element
     * @param element word element to visit
     */
    void visitWordElement(WordElement element);

    /**
     * Visit space element
     * @param element space element to visit
     */
    void visitSpaceElement(SpaceElement element);
}
