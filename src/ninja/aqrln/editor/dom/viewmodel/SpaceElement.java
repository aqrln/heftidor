package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.ChildlessElement;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.visitors.DOMVisitor;

import java.awt.Dimension;
import java.awt.Graphics2D;

/**
 * @author Alexey Orlenko
 */
public class SpaceElement extends ChildlessElement {
    @Override
    public Dimension getSize() {
        return null;
    }

    @Override
    public void setStyle(Style style) {

    }

    @Override
    public Style getStyle() {
        return null;
    }

    @Override
    public void draw(Graphics2D graphics, int x, int y) {

    }

    @Override
    public void accept(DOMVisitor visitor) {

    }
}
