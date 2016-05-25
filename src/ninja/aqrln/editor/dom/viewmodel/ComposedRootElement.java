package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.core.FlatIterator;

import java.awt.Dimension;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public class ComposedRootElement extends DocumentViewModelCompositeElement {
    public static final int PAGE_SPACING = 10;

    private Dimension size;

    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitRootElement(this);
    }

    @Override
    public Dimension calculateSize() {
        int width = 0;
        int height = 0;

        for (Element child : getChildren()) {
            height += PAGE_SPACING;

            Dimension size = ((DocumentViewModelElement) child).getSize();

            if (size.width > width) {
                width = size.width;
            }

            height += size.height + PAGE_SPACING;
        }

        return new Dimension(width, height);
    }

    @Override
    public ListIterator<Element> getFlatIterator() {
        return new FlatIterator(this);
    }
}
