package ninja.aqrln.editor.dom.model;

import ninja.aqrln.editor.dom.core.FlatIterator;

/**
 * Root element of the document model
 * @author Alexey Orlenko
 */
public class RootElement extends DocumentModelCompositeElement {
    @Override
    public void accept(DocumentModelVisitor visitor) {
        visitor.visitRootElement(this);
    }

    @Override
    public FlatIterator getFlatIterator() {
        return new FlatIterator(this);
    }
}
