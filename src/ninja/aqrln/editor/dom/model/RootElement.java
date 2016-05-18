package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public class RootElement extends DocumentModelCompositeElement {
    public static final int PAGE_SPACING = 10;

    @Override
    public Dimension getSize() {
        int width = 0;
        int height = 0;

        for (Element child : getChildren()) {
            height += PAGE_SPACING;

            Dimension size = child.getSize();

            if (size.width > width) {
                width = size.width;
            }

            height += size.height + PAGE_SPACING;
        }

        return new Dimension(width, height);
    }

    @Override
    public void accept(DocumentModelVisitor visitor) {
        visitor.visitRootElement(this);
    }
}
