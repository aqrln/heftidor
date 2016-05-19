package ninja.aqrln.editor.dom;

import ninja.aqrln.editor.dom.model.CharacterElement;
import ninja.aqrln.editor.dom.model.ParagraphAlignment;
import ninja.aqrln.editor.dom.model.ParagraphElement;
import ninja.aqrln.editor.dom.model.RootElement;
import ninja.aqrln.editor.dom.viewmodel.ComposedRootElement;
import ninja.aqrln.editor.dom.viewmodel.ViewCompositor;

import java.io.Serializable;

/**
 * @author Alexey Orlenko
 */
public class Document implements Serializable {
    private RootElement documentData;
    private String name;

    public Document() {
        name = "Untitled";
        documentData = new RootElement();

        String firstParagraphText = "This is the first paragraph. It has a lot of freaking text that is used solely for " +
                "testing purposes and to check out the document compositor.";

        String secondParagraphText = "This is the second paragraph. Based procedures sticky efficiently engineer " +
                "standards supply e-services cross-media next-generation prospective niches. Dynamically 24/365 drive " +
                "without cooperative competently vortals imperatives vortals client-centric evolve cultivate premium. " +
                "Products empower high-impact wireless web-readiness technologies forward enabled tested schemas " +
                "competitive sources mindshare economically. Superior systems robust synergy progressive " +
                "multidisciplinary underwhelm best market interfaces total cooperative.";

        String thirdParagraphText = "Repurpose morph disseminate revolutionize leadership highly multimedia syndicate " +
                "seamlessly compelling testing re-engineer. Technologies products professional and generate invested " +
                "change enhance maintain extend future-proof skills. Competitive procrastinate excellent real-time " +
                "recaptiualize quality user conceptualize parallel interoperable leading-edge resource-leveling based. " +
                "Researched high-payoff cutting-edge service uniquely disintermediate convergence vertical experiences " +
                "methodologies methodologies sticky. Results B2C data exploit high-yield grow sound timely " +
                "backward-compatible viral robust capital fashion evisculate premier.";

        String fourthParagraphText = "Improvements diverse restore deliver total backward-compatible pandemic " +
                "compelling seamlessly high-quality vortals improvements expanded. Communities facilitate exceptional " +
                "world-class best-of-breed practices capital exceptional improvements vectors resource-leveling " +
                "expertise. Synergistically simplify reconceptualize one-to-one solutions action completely scalable " +
                "maintain prospective access goal-oriented sticky. Mesh re-engineer to mesh collaboratively " +
                "progressive strategic maintainable via backward-compatible open-source channels deliver ideas.";

        documentData.getChildren().add(createParagraph(firstParagraphText, ParagraphAlignment.LEFT));
        documentData.getChildren().add(createParagraph(secondParagraphText, ParagraphAlignment.JUSTIFY));
        documentData.getChildren().add(createParagraph(thirdParagraphText, ParagraphAlignment.RIGHT));
        documentData.getChildren().add(createParagraph(fourthParagraphText, ParagraphAlignment.CENTER));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private ParagraphElement createParagraph(String text, ParagraphAlignment alignment) {
        ParagraphElement paragraph = new ParagraphElement();

        for (char c : text.toCharArray()) {
            paragraph.getChildren().add(new CharacterElement(c));
        }

        paragraph.setAlignment(alignment);

        if (alignment != ParagraphAlignment.JUSTIFY) {
            paragraph.setFirstLineIndent(false);
        }

        return paragraph;
    }

    public ComposedRootElement getDocumentView() {
        ViewCompositor compositor = new ViewCompositor();
        documentData.accept(compositor);
        return compositor.getResult();
    }

    public RootElement getRootElement() {
        return documentData;
    }
}
