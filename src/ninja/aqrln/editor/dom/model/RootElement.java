package ninja.aqrln.editor.dom.model;

/**
 * @author Alexey Orlenko
 */
public class RootElement extends DocumentModelCompositeElement {
    @Override
    public void accept(DocumentModelVisitor visitor) {
        visitor.visitRootElement(this);
    }
}
