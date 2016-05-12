package ninja.aqrln.editor.core;

import java.util.ListIterator;

/**
 * @author Alexey Orlenko
 */
public abstract class ChildlessElement implements Element {
    public ListIterator<Element> getChildren() {
        return new ListIterator<Element>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Element next() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Element previous() {
                return null;
            }

            @Override
            public int nextIndex() {
                return 0;
            }

            @Override
            public int previousIndex() {
                return 0;
            }

            @Override
            public void remove() {
                throw new IllegalStateException("cannot remove from element that has no children");
            }

            @Override
            public void set(Element element) {
                throw new IllegalStateException("cannot set to element that has no children");
            }

            @Override
            public void add(Element element) {
                throw new IllegalStateException("cannot add to element that has no children");
            }
        };
    }
}
