package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.core.FlatIterator;

import java.awt.Dimension;
import java.util.ListIterator;

/**
 * Page element
 * @author Alexey Orlenko
 */
public class PageElement extends DocumentViewModelCompositeElement {

    /**
     * Page size
     */
    public static final Dimension PAGE_SIZE = new Dimension(400, 500);

    /**
     * Upper padding
     */
    public static final int PADDING_TOP = 30;

    /**
     * Left padding
     */
    public static final int PADDING_LEFT = 30;

    /**
     * Bottom padding
     */
    public static final int PADDING_BOTTOM = 30;

    /**
     * Right padding
     */
    public static final int PADDING_RIGHT = 30;

    /**
     * Maximal width of the content
     */
    public static final int CONTENT_WIDTH = PAGE_SIZE.width - PADDING_LEFT - PADDING_RIGHT;

    /**
     * Maximal height of the content
     */
    public static final int CONTENT_HEIGHT = PAGE_SIZE.height - PADDING_TOP - PADDING_BOTTOM;

    @Override
    public Dimension getSize() {
        return PAGE_SIZE;
    }

    @Override
    public Dimension calculateSize() {
        return PAGE_SIZE;
    }

    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitPageElement(this);
    }

    /**
     * Get actual height of the content
     * @return calculated content height
     */
    public int getContentHeight() {
        int height = 0;

        for (Element child : this.getChildren()) {
            height += ((DocumentViewModelElement) child).getSize().height;
        }

        return height;
    }

    @Override
    public ListIterator<Element> getFlatIterator() {
        return new FlatIterator(this);
    }
}
