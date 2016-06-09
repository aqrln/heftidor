package ninja.aqrln.editor.component;

import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.viewmodel.*;

import java.awt.*;

import static ninja.aqrln.editor.dom.viewmodel.PageElement.PAGE_SIZE;

/**
 * Renders the document view model tree onto Graphics2D
 * @author Alexey Orlenko
 */
public class DocumentRenderer implements DocumentViewModelVisitor {
    private static final Stroke PAGE_STROKE = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    private Graphics2D graphics;
    private int x;
    private int y;
    private int viewHeight;
    private int scrolledValue;

    /**
     * Public constructor
     * @param graphics Graphics2D instance
     * @param x x coordinate from which the drawing should start on the surface
     * @param y y coordinate from which the drawing should start on the surface
     * @param viewHeight height of the viewport
     * @param scrolledValue height of the scrolled part of the surface
     */
    public DocumentRenderer(Graphics2D graphics, int x, int y, int viewHeight, int scrolledValue) {
        this.graphics = graphics;
        this.x = x;
        this.y = y;
        this.viewHeight = viewHeight;
        this.scrolledValue = scrolledValue;
    }

    private void updatePosition(DocumentViewModelElement element) {
        element.getViewContext().setPosition(new Point(x, y));
    }

    @Override
    public void visitCharacterElement(CharacterViewElement element) {
        Dimension size = element.getSize();
        int width = size.width;
        int height = size.height;
        Style style = element.getStyle();

        graphics.setColor(style.getBackgroundColor());
        graphics.fillRect(x, y, width, height);

        graphics.setFont(style.getFont());
        graphics.setColor(style.getForegroundColor());
        graphics.drawString("" + element.getCharacter(), x, y + height);

        updatePosition(element);
    }

    @Override
    public void visitLineElement(LineElement element) {
        int prevX = x;

        updatePosition(element);

        for (Element child : element.getChildren()) {
            DocumentViewModelElement view = (DocumentViewModelElement) child;
            view.accept(this);
            x += view.getSize().width;
        }

        x = prevX;
    }

    @Override
    public void visitPageElement(PageElement element) {
        graphics.setStroke(PAGE_STROKE);

        graphics.setColor(Color.WHITE);
        graphics.fillRect(x, y, PAGE_SIZE.width, PAGE_SIZE.height);

        graphics.setColor(Color.BLACK);
        graphics.drawRect(x, y, PAGE_SIZE.width, PAGE_SIZE.height);

        int prevX = x;
        int prevY = y;

        x += PageElement.PADDING_LEFT;
        y += PageElement.PADDING_TOP;

        updatePosition(element);

        for (Element child : element.getChildren()) {
            DocumentViewModelElement view = (DocumentViewModelElement) child;
            view.accept(this);
            y += view.getSize().height;
        }

        x = prevX;
        y = prevY;
    }

    @Override
    public void visitRootElement(ComposedRootElement element) {
        updatePosition(element);

        for (Element child : element.getChildren()) {
            DocumentViewModelElement view = (DocumentViewModelElement) child;
            y += ComposedRootElement.PAGE_SPACING;
            int pageHeight = view.getSize().height + ComposedRootElement.PAGE_SPACING;

            if (y + pageHeight > scrolledValue) {
                view.accept(this);
            }
            y += pageHeight;

            if (y - scrolledValue > viewHeight) {
                break;
            }
        }
    }

    @Override
    public void visitWordElement(WordElement element) {
        int prevX = x;

        updatePosition(element);

        for (Element child : element.getChildren()) {
            DocumentViewModelElement view = (DocumentViewModelElement) child;
            view.accept(this);
            x += view.getSize().width;
        }

        x = prevX;
    }

    @Override
    public void visitSpaceElement(SpaceElement element) {
        updatePosition(element);

        Dimension size = element.getSize();
        graphics.setColor(element.getStyle().getBackgroundColor());
        graphics.fillRect(x, y, size.width, size.height);
    }
}
