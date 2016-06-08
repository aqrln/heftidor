package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.model.*;
import ninja.aqrln.editor.dom.viewmodel.alignment.TextAligner;

import java.util.List;

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
            closeWord(element);
        } else {
            CharacterViewElement view = new CharacterViewElement(element);
            currentWord.addChild(view);
            result.getElementRegistry().setElementView(element, view);
        }
    }

    private void closeWord(CharacterElement spaceElement) {
        int maxLineWidth = PageElement.CONTENT_WIDTH;
        int lineWidth = currentLine.getSize().width;
        int newLineWidth = lineWidth + currentWord.getSize().width;

        if (newLineWidth > maxLineWidth) {
            addLineToPage();
            currentLine = new LineElement();
        }

        currentLine.addChild(currentWord);
        currentWord = new WordElement();

        if (spaceElement != null) {
            SpaceElement space = new SpaceElement();
            space.setOrigin(spaceElement);

            int spaceWidth = space.getWidth();
            lineWidth = currentLine.getSize().width;
            int maxSpaceWidth = maxLineWidth - lineWidth;
            space.setWidth(Integer.min(spaceWidth, maxSpaceWidth));

            currentLine.addChild(space);
            result.getElementRegistry().setElementView(spaceElement, space);
        }
    }

    @Override
    public void visitParagraphElement(ParagraphElement element) {
        currentAlignment = element.getAlignment();

        currentLine = new LineElement();

        if (element.hasFirstLineIndent()) {
            currentLine.addChild(new IndentElement());
        }

        List<Element> children = element.getChildren();

        for (Element child : children) {
            ((DocumentModelElement) child).accept(this);
        }

        if (children.size() == 0) {
            element.getDummyElement().accept(this);
        }

        closeWord(null);
        currentLine.setLastLine(true);
        addLineToPage();
    }

    private void addLineToPage() {
        int maxContentHeight = PageElement.CONTENT_HEIGHT;
        int contentHeight = currentPage.getContentHeight();
        int newContentHeight = contentHeight + currentLine.getSize().height;

        if (newContentHeight > maxContentHeight) {
            result.addChild(currentPage);
            currentPage = new PageElement();
        }

        TextAligner.getAligner(currentAlignment).align(currentLine);

        currentPage.addChild(currentLine);
    }

    @Override
    public void visitRootElement(RootElement element) {
        for (Element child : element.getChildren()) {
            ((DocumentModelElement) child).accept(this);
        }

        result.addChild(currentPage);
    }

    public ComposedRootElement getResult() {
        return result;
    }
}
