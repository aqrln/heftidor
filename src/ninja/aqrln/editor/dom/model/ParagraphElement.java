package ninja.aqrln.editor.dom.model;

/**
 * @author Alexey Orlenko
 */
public class ParagraphElement extends DocumentModelCompositeElement {
    private ParagraphAlignment alignment = ParagraphAlignment.JUSTIFY;

    private boolean firstLineIndent = true;

    public ParagraphAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(ParagraphAlignment alignment) {
        this.alignment = alignment;
    }

    public boolean hasFirstLineIndent() {
        return firstLineIndent;
    }

    public void setFirstLineIndent(boolean hasIndent) {
        firstLineIndent = hasIndent;
    }

    @Override
    public void accept(DocumentModelVisitor visitor) {
        visitor.visitParagraphElement(this);
    }
}
