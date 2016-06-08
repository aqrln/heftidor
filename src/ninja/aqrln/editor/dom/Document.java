package ninja.aqrln.editor.dom;

import ninja.aqrln.editor.dom.model.ParagraphElement;
import ninja.aqrln.editor.dom.model.RootElement;
import ninja.aqrln.editor.dom.viewmodel.ComposedRootElement;
import ninja.aqrln.editor.dom.viewmodel.ViewCompositor;

import java.io.Serializable;

/**
 * @author Alexey Orlenko
 */
public class Document implements Serializable {
    private RootElement documentData;
    private transient ComposedRootElement composedDocument;
    private String name;

    public Document() {
        name = "Untitled";
        documentData = new RootElement();
        documentData.addChild(new ParagraphElement());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ComposedRootElement getDocumentView() {
        if (composedDocument == null) {
            compose();
        }

        return composedDocument;
    }

    public RootElement getRootElement() {
        return documentData;
    }

    public void setRootElement(RootElement root) {
        documentData = root;
    }

    public void compose() {
        ViewCompositor compositor = new ViewCompositor();
        documentData.accept(compositor);
        composedDocument = compositor.getResult();
    }
}
