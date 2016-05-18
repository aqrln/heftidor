package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.DOMVisitor;
import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.model.CharacterElement;
import ninja.aqrln.editor.dom.model.ParagraphElement;
import ninja.aqrln.editor.dom.model.RootElement;

/**
 * @author Alexey Orlenko
 */
public class ViewCompositor implements DOMVisitor {
    private RootElement result;
    private PageElement currentPage;
    private ParagraphElement currentParagraph;
    private LineElement currentLine;

    public ViewCompositor() {
        result = new RootElement();
        currentPage = new PageElement();
    }

    @Override
    public void visitCharacterElement(CharacterElement element) {
        if (currentLine == null) {
            currentLine = new LineElement();
        }

        currentLine.getChildren().add(element);
    }

    @Override
    public void visitParagraphElement(ParagraphElement element) {

    }

    @Override
    public void visitLineElement(LineElement element) {

    }

    @Override
    public void visitPageElement(PageElement element) {
        result.getChildren().add(element);
    }

    @Override
    public void visitRootElement(RootElement element) {
        for (Element child : element.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visitWordElement(WordElement wordElement) {

    }

    public RootElement getResult() {
        return result;
    }
}
