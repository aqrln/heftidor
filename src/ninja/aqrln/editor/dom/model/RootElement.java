package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.DOMVisitor;
import ninja.aqrln.editor.dom.core.Element;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Alexey Orlenko
 */
public class RootElement extends CompositeElement {
    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public void draw(Graphics2D graphics, int x, int y) {
        for (Element child : getChildren()) {
            y += 20;
            child.draw(graphics, x, y);
            y += child.getSize().height;
        }
    }

    @Override
    public void accept(DOMVisitor visitor) {
        visitor.visitRootElement(this);
    }
}
