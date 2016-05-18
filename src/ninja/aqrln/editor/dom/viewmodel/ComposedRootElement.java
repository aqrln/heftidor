package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.model.RootElement;

/**
 * @author Alexey Orlenko
 */
public class ComposedRootElement extends RootElement implements DocumentViewModelElement {
    @Override
    public void accept(DocumentViewModelVisitor visitor) {
        visitor.visitRootElement(this);
    }
}
