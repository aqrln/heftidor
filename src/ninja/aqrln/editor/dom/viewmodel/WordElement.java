package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.DOMVisitor;
import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;

/**
 * @author Alexey Orlenko
 */
public class WordElement extends CompositeElement {

    @Override
    public Dimension getSize() {
        int width = 0;
        int height = 0;

        for (Element child : getChildren()) {
            Dimension size = child.getSize();
            width += size.width;
            if (size.height > height) {
                height = size.height;
            }
        }

        return new Dimension(width, height);
    }

    @Override
    public void accept(DOMVisitor visitor) {
        visitor.visitWordElement(this);
    }
}
