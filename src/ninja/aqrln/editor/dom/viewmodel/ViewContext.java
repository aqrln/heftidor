package ninja.aqrln.editor.dom.viewmodel;

import java.awt.Dimension;
import java.awt.Point;

/**
 * View context (size and position on the screen)
 * @author Alexey Orlenko
 */
public class ViewContext {
    private Dimension size;

    private Point position;

    /**
     * Default constructor
     */
    public ViewContext() {
        this(null, null);
    }

    /**
     * @param size size of the element
     * @param position position on the screen
     */
    public ViewContext(Dimension size, Point position) {
        this.size = size;
        this.position = position;
    }

    /**
     * @return the size of the element
     */
    public Dimension getSize() {
        return size;
    }

    /**
     * Set the size of the element
     * @param size size of the element
     */
    public void setSize(Dimension size) {
        this.size = size;
    }

    /**
     * @return position of the element
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Set position of the element on the screen
     * @param position position of the element
     */
    public void setPosition(Point position) {
        this.position = position;
    }
}
