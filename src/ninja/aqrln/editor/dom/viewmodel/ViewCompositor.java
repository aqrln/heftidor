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
    private LineElement currentLine;
    private WordElement currentWord;

    public ViewCompositor() {
        result = new RootElement();
        currentPage = new PageElement();
        currentLine = new LineElement();
        currentWord = new WordElement();
    }

    @Override
    public void visitCharacterElement(CharacterElement element) {
        if (Character.isSpaceChar(element.getCharacter())) {
            closeWord(true);
        } else {
            currentWord.getChildren().add(element);
        }
    }

    private void closeWord(boolean addSpace) {
        int maxLineWidth = currentPage.getSize().width - PageElement.PADDING_LEFT - PageElement.PADDING_RIGHT;
        int lineWidth = currentLine.getSize().width;
        int newLineWidth = lineWidth + currentWord.getSize().width;

        if (newLineWidth > maxLineWidth) {
            addLineToPage();
            currentLine = new LineElement();
        }

        currentLine.getChildren().add(currentWord);
        currentWord = new WordElement();

        if (addSpace) {
            SpaceElement space = new SpaceElement();
            int spaceWidth = space.getWidth();
            lineWidth = currentLine.getSize().width;
            int maxSpaceWidth = maxLineWidth - lineWidth;
            space.setWidth(Integer.min(spaceWidth, maxSpaceWidth));
            currentLine.getChildren().add(space);
        }
    }

    @Override
    public void visitParagraphElement(ParagraphElement element) {
        currentLine.getChildren().add(new IndentElement());

        for (Element child : element.getChildren()) {
            child.accept(this);
        }

        closeWord(false);
        currentLine.setLastLine(true);
        addLineToPage();
    }

    private void addLineToPage() {
        int maxContentHeight = currentPage.getSize().height - PageElement.PADDING_TOP - PageElement.PADDING_BOTTOM;
        int contentHeight = currentPage.getContentHeight();
        int newContentHeight = contentHeight + currentLine.getSize().height;

        if (newContentHeight > maxContentHeight) {
            result.getChildren().add(currentPage);
            currentPage = new PageElement();
        }

        currentPage.getChildren().add(currentLine);
    }

    @Override
    public void visitLineElement(LineElement element) {
        illegalElement("line");
    }

    @Override
    public void visitPageElement(PageElement element) {
        illegalElement("page");
    }

    @Override
    public void visitRootElement(RootElement element) {
        for (Element child : element.getChildren()) {
            child.accept(this);
        }

        result.getChildren().add(currentPage);
    }

    @Override
    public void visitWordElement(WordElement wordElement) {
        illegalElement("word");
    }

    @Override
    public void visitSpaceElement() {
        illegalElement("space");
    }

    public RootElement getResult() {
        return result;
    }

    private void illegalElement(String type) {
        throw new IllegalStateException(type + " elements cannot appear in logical DOM model");
    }
}
