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
        String firstParagraphText = "This is the first paragraph. It has a lot of freaking text that is used solely for " +
                "testing purposes and to check out the document compositor.";
        String secondParagraphText = "This is the second paragraph. Based procedures sticky efficiently engineer " +
                "standards supply e-services cross-media next-generation prospective niches. Dynamically 24/365 drive " +
                "without cooperative competently vortals imperatives vortals client-centric evolve cultivate premium. " +
                "Products empower high-impact wireless web-readiness technologies forward enabled tested schemas " +
                "competitive sources mindshare economically. Superior systems robust synergy progressive " +
                "multidisciplinary underwhelm best market interfaces total cooperative.";

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
