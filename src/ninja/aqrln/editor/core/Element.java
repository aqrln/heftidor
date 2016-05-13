package ninja.aqrln.editor.core;

import java.awt.Dimension;
import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public interface Element {
    ListIterator<Element> getListIterator();
    Dimension getSize();
}
