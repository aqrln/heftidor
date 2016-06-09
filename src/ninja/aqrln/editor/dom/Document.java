package ninja.aqrln.editor.dom;

import ninja.aqrln.editor.dom.model.ParagraphElement;
import ninja.aqrln.editor.dom.model.RootElement;
import ninja.aqrln.editor.dom.viewmodel.ComposedRootElement;
import ninja.aqrln.editor.dom.viewmodel.ViewCompositor;

import java.io.Serializable;

/**
 * Document class
 * @author Alexey Orlenko
 */
public class Document implements Serializable {
    private RootElement documentData;
    private transient ComposedRootElement composedDocument;
    private String name;

    /**
     * Public constructor
     */
    public Document() {
        name = "Untitled";
        documentData = new RootElement();
        documentData.addChild(new ParagraphElement());
    }

    /**
     * Get the name of the document
     * @return name of the document
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the document
     * @param name name of the document
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the document view model tree (composing it first,
     * if needed)
     *
     * @return composed root element
     */
    public ComposedRootElement getDocumentView() {
        if (composedDocument == null) {
            compose();
        }

        return composedDocument;
    }

    /**
     * Get the document model tree
     * @return root element
     */
    public RootElement getRootElement() {
        return documentData;
    }

    /**
     * Sets the document data
     * @param root new root element
     */
    public void setRootElement(RootElement root) {
        documentData = root;
    }

    /**
     * Composes the document and generates the document view model tree
     */
    public void compose() {
        ViewCompositor compositor = new ViewCompositor();
        documentData.accept(compositor);
        composedDocument = compositor.getResult();
    }
}
