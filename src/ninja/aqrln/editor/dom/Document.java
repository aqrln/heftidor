package ninja.aqrln.editor.dom;

import ninja.aqrln.editor.dom.model.CharacterElement;
import ninja.aqrln.editor.dom.model.ParagraphElement;
import ninja.aqrln.editor.dom.model.RootElement;
import ninja.aqrln.editor.dom.viewmodel.ViewCompositor;

/**
 * @author Alexey Orlenko
 */
public class Document {
    private RootElement documentData;

    public Document() {
        String firstParagraphText = "This is the first paragraph.";
        String secondParagraphText = "This is the second paragraph";

        ParagraphElement firstParagraph = new ParagraphElement();
        for (char c : firstParagraphText.toCharArray()) {
            firstParagraph.getChildren().add(new CharacterElement(c));
        }

        ParagraphElement secondParagraph = new ParagraphElement();
        for (char c : secondParagraphText.toCharArray()) {
            secondParagraph.getChildren().add(new CharacterElement(c));
        }

        documentData = new RootElement();
        documentData.getChildren().add(firstParagraph);
        documentData.getChildren().add(secondParagraph);
    }

    public RootElement getDocumentView() {
        ViewCompositor compositor = new ViewCompositor();
        documentData.accept(compositor);
        return compositor.getResult();
    }
}
