package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public class PageElement extends DocumentViewModelCompositeElement {
    public static final Dimension PAGE_SIZE = new Dimension(400, 500);
    public static final int PADDING_TOP = 30;
    public static final int PADDING_LEFT = 30;
    public static final int PADDING_BOTTOM = 30;
    public static final int PADDING_RIGHT = 30;

    public static final int CONTENT_WIDTH = PAGE_SIZE.width - PADDING_LEFT - PADDING_RIGHT;
    public static final int CONTENT_HEIGHT = PAGE_SIZE.height - PADDING_TOP - PADDING_BOTTOM;

    @Override
    public Dimension getSize() {
        return PAGE_SIZE;
    }

    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitPageElement(this);
    }

    public int getContentHeight() {
        int height = 0;

        for (Element child : this.getChildren()) {
            height += ((DocumentViewModelElement) child).getSize().height;
        }

        return height;
    }
}
