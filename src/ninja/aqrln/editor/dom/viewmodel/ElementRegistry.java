package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.model.DocumentModelChildlessElement;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Element registry class
 * @author Alexey Orlenko
 */
public class ElementRegistry {
    private Map<DocumentModelChildlessElement, DocumentViewModelChildlessElement> registry;

    /**
     * Public constructor
     */
    public ElementRegistry() {
        registry = new IdentityHashMap<>();
    }

    /**
     * Save the relationship between document model element and document view model element
     * @param origin document model element
     * @param view   document view model element
     */
    public void setElementView(DocumentModelChildlessElement origin, DocumentViewModelChildlessElement view) {
        registry.put(origin, view);
    }

    /**
     * Get view model element for model element
     * @param origin document model element
     * @return document view model element
     */
    public DocumentViewModelChildlessElement getElementView(DocumentModelChildlessElement origin) {
        return registry.get(origin);
    }

    /**
     * Get a line where specified terminal element is laying
     * @param element document model childless element
     * @return line element
     */
    public LineElement getLine(DocumentModelChildlessElement element) {
        DocumentViewModelChildlessElement view = getElementView(element);
        CompositeElement parent = view.getParent();

        while (!(parent instanceof LineElement)) {
            parent = parent.getParent();
        }

        return (LineElement) parent;
    }
}
