package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.Element;

import java.util.ListIterator;

/**
 * Document model paragraph element
 * @author Alexey Orlenko
 */
public class ParagraphElement extends DocumentModelCompositeElement {
    private ParagraphAlignment alignment = ParagraphAlignment.JUSTIFY;

    private boolean firstLineIndent = true;

    private DocumentModelChildlessElement dummyElement = null;

    /**
     * Get alignment of the paragraph
     * @return paragraph alignment
     */
    public ParagraphAlignment getAlignment() {
        return alignment;
    }

    /**
     * Set alignment of the paragraph
     * @param alignment new alignment
     */
    public void setAlignment(ParagraphAlignment alignment) {
        this.alignment = alignment;
    }

    /**
     * Check if this paragraph is indented
     * @return true if first line has indent, false otherwise
     */
    public boolean hasFirstLineIndent() {
        return firstLineIndent;
    }

    /**
     * Sets indent mode
     * @param hasIndent true if first line must be indented, false otherwise
     */
    public void setFirstLineIndent(boolean hasIndent) {
        firstLineIndent = hasIndent;
    }

    @Override
    public void accept(DocumentModelVisitor visitor) {
        visitor.visitParagraphElement(this);
    }

    @Override
    public ListIterator<Element> getFlatIterator() {
        return children.listIterator();
    }

    public boolean getFirstLineIndent() {
        return firstLineIndent;
    }

    /**
     * Returns a dummy element to use in situations where at least one child is required
     * even though the paragraph is actually empty (for example, to handle cursor in empty
     * paragraphs correctly).
     *
     * @return dummy element with default style
     */
    public DocumentModelChildlessElement getDummyElement() {
        if (dummyElement == null) {
            dummyElement = new CharacterElement(' ');
        }

        return dummyElement;
    }
}
