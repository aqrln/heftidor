package ninja.aqrln.editor.dom.core;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Flat iterator that allows to iterate through the leaf nodes
 * @author Alexey Orlenko
 */
public class FlatIterator implements ListIterator<Element> {
    private Element element;

    private List<ListIterator<Element>> iterators;
    private int currentIteratorIndex;

    /**
     * Public constructor
     * @param element root element
     */
    public FlatIterator(Element element) {
        this.element = element;
        this.iterators = new ArrayList<>();

        iterators.addAll(element.getChildren().stream().map(Element::getFlatIterator).collect(Collectors.toList()));
        currentIteratorIndex = 0;
    }

    @Override
    public boolean hasNext() {
        if (currentIteratorIndex == -1) {
            currentIteratorIndex++;
        }

        while (currentIteratorIndex < iterators.size() && !iterators.get(currentIteratorIndex).hasNext()) {
            currentIteratorIndex++;
        }

        return currentIteratorIndex < iterators.size();
    }

    @Override
    public Element next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        return iterators.get(currentIteratorIndex).next();
    }

    @Override
    public boolean hasPrevious() {
        if (currentIteratorIndex == iterators.size()) {
            currentIteratorIndex--;
        }

        while (currentIteratorIndex >= 0 && !iterators.get(currentIteratorIndex).hasPrevious()) {
            iterators.set(currentIteratorIndex, element.getChildren().get(currentIteratorIndex).getFlatIterator());
            currentIteratorIndex--;
        }

        return currentIteratorIndex >= 0;
    }

    @Override
    public Element previous() {
        if (!hasPrevious()) {
            throw new NoSuchElementException();
        }

        return iterators.get(currentIteratorIndex).previous();
    }

    @Override
    public int nextIndex() {
        return iterators.get(currentIteratorIndex).nextIndex();
    }

    @Override
    public int previousIndex() {
        return iterators.get(currentIteratorIndex).previousIndex();
    }

    @Override
    public void remove() {
        iterators.get(currentIteratorIndex).remove();
    }

    @Override
    public void set(Element element) {
        iterators.get(currentIteratorIndex).set(element);
    }

    @Override
    public void add(Element element) {
        iterators.get(currentIteratorIndex).add(element);
    }
}
