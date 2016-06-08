package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.Element;

import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public class ParagraphElement extends DocumentModelCompositeElement {
    private ParagraphAlignment alignment = ParagraphAlignment.JUSTIFY;

    private boolean firstLineIndent = true;

    private DocumentModelChildlessElement dummyElement = null;

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

    @Override
    public ListIterator<Element> getFlatIterator() {
        return children.listIterator();
    }

    public boolean getFirstLineIndent() {
        return firstLineIndent;
    }

    public DocumentModelChildlessElement getDummyElement() {
        if (dummyElement == null) {
            dummyElement = new CharacterElement(' ');
        }

        return dummyElement;
    }
}
