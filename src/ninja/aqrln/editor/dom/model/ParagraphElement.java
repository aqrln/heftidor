package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.DOMVisitor;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public class ParagraphElement extends CompositeElement {
    private ParagraphAlignment alignment = ParagraphAlignment.JUSTIFY;

    public ParagraphAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(ParagraphAlignment alignment) {
        this.alignment = alignment;
    }

    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public void accept(DOMVisitor visitor) {
        visitor.visitParagraphElement(this);
    }
}
