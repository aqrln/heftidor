package ninja.aqrln.editor.dom.viewmodel;

import java.awt.Dimension;
import java.awt.Point;

/**
 * @author Alexey Orlenko
 */
public class ViewContext {
    private Dimension size;

    private Point position;

    public ViewContext() {
        this(null, null);
    }

    public ViewContext(Dimension size, Point position) {
        this.size = size;
        this.position = position;
    }

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
