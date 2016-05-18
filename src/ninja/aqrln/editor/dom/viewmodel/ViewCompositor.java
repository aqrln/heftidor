package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.model.*;
import ninja.aqrln.editor.dom.viewmodel.alignment.TextAligner;

/**
 * @author Alexey Orlenko
 */
public class ViewCompositor implements DocumentModelVisitor {
    private ComposedRootElement result;
    private PageElement currentPage;
    private LineElement currentLine;
    private WordElement currentWord;
    private ParagraphAlignment currentAlignment;

    public ViewCompositor() {
        result = new ComposedRootElement();
        currentPage = new PageElement();
        currentWord = new WordElement();
    }

    @Override
    public void visitCharacterElement(CharacterElement element) {
        if (Character.isSpaceChar(element.getCharacter())) {
            closeWord(true);
        } else {
            currentWord.getChildren().add(new CharacterViewElement(element));
        }
    }

    private void closeWord(boolean addSpace) {
        int maxLineWidth = currentPage.CONTENT_WIDTH;
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
        currentAlignment = element.getAlignment();

        currentLine = new LineElement();
        currentLine.getChildren().add(new IndentElement());

        for (Element child : element.getChildren()) {
            ((DocumentModelElement) child).accept(this);
        }

        closeWord(false);
        currentLine.setLastLine(true);
        addLineToPage();
    }

    private void addLineToPage() {
        int maxContentHeight = currentPage.CONTENT_HEIGHT;
        int contentHeight = currentPage.getContentHeight();
        int newContentHeight = contentHeight + currentLine.getSize().height;

        if (newContentHeight > maxContentHeight) {
            result.getChildren().add(currentPage);
            currentPage = new PageElement();
        }

        TextAligner.getAligner(currentAlignment).align(currentLine);

        currentPage.getChildren().add(currentLine);
    }

    @Override
    public void visitRootElement(RootElement element) {
        for (Element child : element.getChildren()) {
            ((DocumentModelElement) child).accept(this);
        }

        result.getChildren().add(currentPage);
    }

    public ComposedRootElement getResult() {
        return result;
    }
}
