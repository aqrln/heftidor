package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public class WordElement extends DocumentViewModelCompositeElement {

    @Override
    public Dimension calculateSize() {
        int width = 0;
        int height = 0;

        for (Element child : getChildren()) {
            Dimension size = ((DocumentViewModelElement) child).getSize();
            width += size.width;
            if (size.height > height) {
                height = size.height;
            }
        }

        return new Dimension(width, height);
    }

    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitWordElement(this);
    }

    @Override
    public ListIterator<Element> getFlatIterator() {
        return getChildren().listIterator();
    }
}
