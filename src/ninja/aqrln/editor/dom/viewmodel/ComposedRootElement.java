package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public class ComposedRootElement extends DocumentViewModelCompositeElement {
    public static final int PAGE_SPACING = 10;

    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitRootElement(this);
    }

    @Override
    public Dimension getSize() {
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
}
