package ninja.aqrln.editor.dom.viewmodel.alignment;

import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.viewmodel.LineElement;
import ninja.aqrln.editor.dom.viewmodel.PageElement;
import ninja.aqrln.editor.dom.viewmodel.SpaceElement;

import java.util.List;

/**
 * Justify alignment strategy
 * @author Alexey Orlenko
 */
public class JustifyAlignmentStrategy implements TextAlignmentStrategy {
    @Override
    public void align(LineElement line) {
        if (line.isLastLine()) {
            return;
        }

        List<Element> children = line.getChildren();

        Element lastElement = children.get(children.size() - 1);
        if (lastElement instanceof SpaceElement) {
            ((SpaceElement) lastElement).setWidth(0);
        }

        int maxLineWidth = PageElement.CONTENT_WIDTH;
        int lineWidth = line.getSize().width;
        int totalSpacing = maxLineWidth - lineWidth;

        if (totalSpacing == 0) {
            return;
        }

        int spacesCount = (int) children.stream().filter(element -> element instanceof SpaceElement).count() - 1;

        int additionalSpacing = totalSpacing / spacesCount;

        children.stream().filter(element -> element instanceof SpaceElement).forEach(element -> {
            SpaceElement spaceElement = (SpaceElement) element;
            spaceElement.setWidth(spaceElement.getWidth() + additionalSpacing);
        });
    }
}
