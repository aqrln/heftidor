package ninja.aqrln.editor.dom;

import ninja.aqrln.editor.dom.model.RootElement;
import ninja.aqrln.editor.dom.viewmodel.ViewCompositor;

/**
 * @author Alexey Orlenko
 */
public class Document {
    private RootElement documentData;

    public Document() {
        documentData = new RootElement();
    }

    public RootElement getDocumentView() {
        ViewCompositor compositor = new ViewCompositor();
        compositor.visitRootElement(documentData);
        return compositor.getResult();
    }
}
