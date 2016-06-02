package ninja.aqrln.editor.component;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.core.CompositeElement;
import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.model.CharacterElement;
import ninja.aqrln.editor.dom.model.DocumentModelChildlessElement;
import ninja.aqrln.editor.dom.model.ParagraphAlignment;
import ninja.aqrln.editor.dom.model.ParagraphElement;
import ninja.aqrln.editor.dom.viewmodel.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * @author Alexey Orlenko
 */
public class EditorPane extends JPanel implements KeyListener {
    private Document document;

    private JScrollPane scrollPane;

    private boolean cursorVisible;

    private int paragraphIndex;
    private int nextElementIndex;

    private static final Stroke CURSOR_STROKE = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    private boolean movingForward = true;

    public EditorPane(Document document) {
        this.document = document;
        setPreferredSize(new Dimension(800, 600));
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

        document.compose();
        paragraphIndex = 0;
        nextElementIndex = 0;

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
        Dimension size;
        Point position;

        ParagraphElement paragraph = getParagraph();

        if (paragraph.getChildren().size() == 0) {
            size = new Dimension(0, Style.DEFAULT_FONT.getSize());
            position = new Point(0, 0); // TODO
        } else {
            int index = nextElementIndex;
            boolean cursorRight = false;

            if (index > 0) {
                index--;
                cursorRight = true;
            }

            DocumentViewModelElement view =
                    getElementView((DocumentModelChildlessElement) paragraph.getChildren().get(index));
            ViewContext context = view.getViewContext();

            size = context.getSize();
            Point elementPosition = context.getPosition();

            if (cursorRight) {
                position = elementPosition;
            } else {
                position = new Point(elementPosition.x - size.width, elementPosition.y);
            }
        }

        return new ViewContext(size, position);
    }

    private ParagraphElement getParagraph() {
        return (ParagraphElement) document.getRootElement().getChildren().get(paragraphIndex);
    }

    private DocumentViewModelChildlessElement getElementView(DocumentModelChildlessElement element) {
        return document.getDocumentView().getElementRegistry().getElementView(element);
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

            case KeyEvent.VK_BACK_SPACE:
                deleteLeft();
                break;

            case KeyEvent.VK_DELETE:
                deleteRight();
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
        Style style;

        List<Element> paragraphChildren = getParagraph().getChildren();

        if (paragraphChildren.size() > 0) {
            int index = nextElementIndex;
            if (index > 0) {
                index--;
            }

            DocumentModelChildlessElement element = (DocumentModelChildlessElement) getParagraph().getChildren().get(index);
            style = element.getStyle();
        } else {
            style = Style.DEFAULT_STYLE;
        }

        CharacterElement characterElement = new CharacterElement(character, style);

        paragraphChildren.add(nextElementIndex, characterElement);
        nextElementIndex++;

        document.compose();
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void moveLeft() {
        if (nextElementIndex > 0) {
            nextElementIndex--;
        } else if (paragraphIndex > 0) {
            paragraphIndex--;
            nextElementIndex = getParagraph().getChildren().size();
        }
    }

    private void moveRight() {
        if (nextElementIndex < getParagraph().getChildren().size()) {
            nextElementIndex++;
        } else if (paragraphIndex < document.getRootElement().getChildren().size() - 1) {
            paragraphIndex++;
            nextElementIndex = 0;
        }
    }

    private void moveUp() {
        verticalMove(this::moveLeft);
    }

    private void moveDown() {
        verticalMove(this::moveRight);
    }

    private void verticalMove(Runnable action) {
        ElementRegistry registry = document.getDocumentView().getElementRegistry();

        int index = nextElementIndex;
        if (index > 0) {
            index--;
        }

        DocumentModelChildlessElement currentElement =
                (DocumentModelChildlessElement) getParagraph().getChildren().get(index);
        LineElement currentLine = registry.getLine(currentElement);

        for (Element element : currentLine.getChildren()) {
            int moveLength;
            if (element instanceof CompositeElement) {
                moveLength = element.getChildren().size();
            } else {
                moveLength = 1;
            }

            for (int i = 0; i < moveLength; i++) {
                action.run();
            }
        }
    }

    private void deleteLeft() {
        if (nextElementIndex > 0) {
            nextElementIndex--;
            getParagraph().getChildren().remove(nextElementIndex);
        } else if (paragraphIndex > 0) {
            nextElementIndex = joinParagraphs(paragraphIndex - 1, paragraphIndex);
            paragraphIndex--;
        }

        document.compose();
        repaint();
    }

    private int joinParagraphs(int firstIndex, int secondIndex) {
        List<Element> paragraphs = document.getRootElement().getChildren();
        ParagraphElement firstParagraph = (ParagraphElement) paragraphs.get(firstIndex);
        ParagraphElement secondParagraph = (ParagraphElement) paragraphs.get(secondIndex);

        int displacement = firstParagraph.getChildren().size();

        secondParagraph.getChildren().forEach(firstParagraph::addChild);
        paragraphs.remove(secondIndex);

        return displacement;
    }

    private void deleteRight() {
        if (nextElementIndex < getParagraph().getChildren().size()) {
            getParagraph().getChildren().remove(nextElementIndex);
        } else if (paragraphIndex < document.getRootElement().getChildren().size() - 1) {
            joinParagraphs(paragraphIndex, paragraphIndex + 1);
        }

        document.compose();
        repaint();
    }

    public void toggleCurrentParagraphIndent() {
        ParagraphElement paragraph = getParagraph();
        paragraph.setFirstLineIndent(!paragraph.getFirstLineIndent());

        document.compose();
        repaint();
    }

    public void centerCurrentParagraph() {
        getParagraph().setAlignment(ParagraphAlignment.CENTER);
        document.compose();
        repaint();
    }

    public void leftAlignCurrentParagraph() {
        getParagraph().setAlignment(ParagraphAlignment.LEFT);
        document.compose();
        repaint();
    }

    public void rightAlignCurrentParagraph() {
        getParagraph().setAlignment(ParagraphAlignment.RIGHT);
        document.compose();
        repaint();
    }

    public void justifyCurrentParagraph() {
        getParagraph().setAlignment(ParagraphAlignment.JUSTIFY);
        document.compose();
        repaint();
    }
}
