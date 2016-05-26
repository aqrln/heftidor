package ninja.aqrln.editor.component;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.model.CharacterElement;
import ninja.aqrln.editor.dom.model.DocumentModelChildlessElement;
import ninja.aqrln.editor.dom.viewmodel.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Alexey Orlenko
 */
public class EditorPane extends JPanel implements KeyListener {
    private Document document;

    private JScrollPane scrollPane;

    private boolean cursorVisible;

    private DocumentModelChildlessElement currentElement;
    private ListIterator<Element> elementsIterator;

    private static final Stroke CURSOR_STROKE = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    private boolean movingForward = true;

    public EditorPane(Document document) {
        this.document = document;
        setPreferredSize(new Dimension(800, 600));
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

        document.compose();
        currentElement = null;
        elementsIterator = document.getRootElement().getFlatIterator();

        cursorVisible = false;
        setupCursorTimer();
    }

    private void setupCursorTimer() {
        Timer cursorTimer = new Timer();
        cursorTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cursorVisible = !cursorVisible;
                repaint();
            }
        }, 0, 1000);
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D)graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int paddingLeft = (getWidth() - PageElement.PAGE_SIZE.width) / 2;
        int viewHeight = scrollPane.getHeight();
        int scrolledValue = scrollPane.getVerticalScrollBar().getValue();

        ComposedRootElement rootElement = document.getDocumentView();
        Dimension contentSize = rootElement.getSize();
        setPreferredSize(contentSize);
        revalidate();

        DocumentRenderer renderer = new DocumentRenderer(g2d, paddingLeft, 0, viewHeight, scrolledValue);
        rootElement.accept(renderer);

        drawCursor(g2d);
    }

    private void drawCursor(Graphics2D graphics) {
        if (!cursorVisible) {
            return;
        }

        ViewContext elementContext = getCurrentElementViewContext();

        Point elementPosition = elementContext.getPosition();
        Dimension elementSize = elementContext.getSize();
        int height = (int) (elementSize.height * 1.2);
        int x = elementPosition.x + elementSize.width;
        int y = elementPosition.y;

        graphics.setStroke(CURSOR_STROKE);
        graphics.setColor(Color.BLACK);
        graphics.drawLine(x, y, x, y + height);
    }

    private ViewContext getCurrentElementViewContext() {
        if (currentElement != null) {
            DocumentViewModelChildlessElement view = getCurrentElementView();
            view.getSize();
            return view.getViewContext();
        }

        Dimension size;
        Point position;

        if (elementsIterator.hasNext()) {
            DocumentViewModelElement nextElement = getElementView(getNextElement());
            ViewContext nextElementContext = nextElement.getViewContext();
            size = nextElementContext.getSize();
            Point nextPosition = nextElementContext.getPosition();
            position = new Point(nextPosition.x - size.width, nextPosition.y);
        } else {
            size = new Dimension(0, Style.DEFAULT_FONT.getSize());
            position = new Point(0, 0); // TODO
        }

        return new ViewContext(size, position);
    }

    private DocumentModelChildlessElement getNextElement() {
        DocumentModelChildlessElement element = (DocumentModelChildlessElement) elementsIterator.next();
        elementsIterator.previous();
        return element;
    }

    private DocumentViewModelChildlessElement getElementView(DocumentModelChildlessElement element) {
        return document.getDocumentView().getElementRegistry().getElementView(element);
    }

    private DocumentViewModelChildlessElement getCurrentElementView() {
        return getElementView(currentElement);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (isPrintableChar(c)) {
            addChar(c);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                moveLeft();
                repaint();
                break;

            case KeyEvent.VK_RIGHT:
                moveRight();
                repaint();
                break;

            case KeyEvent.VK_UP:
                moveUp();
                repaint();
                break;

            case KeyEvent.VK_DOWN:
                moveDown();
                repaint();
                break;

            default:
                break;
        }
    }

    private boolean isPrintableChar(char character) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(character);
        return !Character.isISOControl(character) &&
                character != KeyEvent.CHAR_UNDEFINED &&
                block != null &&
                block != Character.UnicodeBlock.SPECIALS;
    }

    private void addChar(char character) {
        DocumentModelChildlessElement element = currentElement != null ? currentElement : getNextElement();

        Style style = element.getStyle();
        CharacterElement characterElement = new CharacterElement(character, style);

        characterElement.setParent(element.getParent());
        elementsIterator.add(characterElement);
        currentElement = characterElement;

        document.compose();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveLeft() {
        if (!elementsIterator.hasPrevious()) {
            currentElement = null;
            return;
        }

        currentElement = (DocumentModelChildlessElement) elementsIterator.previous();

        if (movingForward) {
            movingForward = false;
            moveLeft();
        }
    }

    public void moveRight() {
        if (!elementsIterator.hasNext()) {
            return;
        }

        currentElement = (DocumentModelChildlessElement) elementsIterator.next();

        if (!movingForward) {
            movingForward = true;
            moveRight();
        }
    }

    public void moveUp() {
        if (currentElement == null) {
            return;
        }

        ElementRegistry registry = document.getDocumentView().getElementRegistry();
        LineElement currentLine = registry.getLine(currentElement);

        while (registry.getLine(currentElement) == currentLine) {
            moveLeft();
        }
    }

    public void moveDown() {
        DocumentModelChildlessElement element = currentElement != null ? currentElement : getNextElement();
        ElementRegistry registry = document.getDocumentView().getElementRegistry();
        LineElement currentLine = registry.getLine(element);

        while (registry.getLine(element) == currentLine) {
            moveRight();
            element = currentElement;
        }
    }
}
