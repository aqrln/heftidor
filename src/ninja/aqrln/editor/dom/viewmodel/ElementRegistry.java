package ninja.aqrln.editor.dom.viewmodel;

import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.model.DocumentModelChildlessElement;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author Alexey Orlenko
 */
public class ElementRegistry {
    private Map<DocumentModelChildlessElement, DocumentViewModelChildlessElement> registry;

    public ElementRegistry() {
        registry = new IdentityHashMap<>();
    }

    public void setElementView(DocumentModelChildlessElement origin, DocumentViewModelChildlessElement view) {
        registry.put(origin, view);
    }

    public DocumentViewModelChildlessElement getElementView(DocumentModelChildlessElement origin) {
        return registry.get(origin);
    }

    public LineElement getLine(DocumentModelChildlessElement element) {
        DocumentViewModelChildlessElement view = getElementView(element);
        CompositeElement parent = view.getParent();

        while (!(parent instanceof LineElement)) {
            parent = parent.getParent();
        }

        return (LineElement) parent;
    }
}
