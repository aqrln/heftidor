package ninja.aqrln.editor.component;

import ninja.aqrln.editor.dom.Document;
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
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

/**
 * Editor Swing component
 * @author Alexey Orlenko
 */
public class EditorPane extends JPanel implements KeyListener, MouseListener, MouseMotionListener {
    private Document document;

    private JScrollPane scrollPane;

    private boolean cursorVisible;

    private int paragraphIndex;
    private int nextElementIndex;

    private static final Stroke CURSOR_STROKE = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    private CursorContext selectionStart;
    private CursorContext selectionEnd;
    private boolean hasSelection = false;
    private boolean isSelectingNow = false;

    private static final Color SELECTION_COLOR = new Color(20, 140, 255, 127);

    /**
     * Public constructor
     * @param document Document instance that is bound to this editor
     */
    public EditorPane(Document document) {
        this.document = document;
        setPreferredSize(new Dimension(800, 600));
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

        addMouseListener(this);
        addMouseMotionListener(this);

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

    /**
     * Sets the scroll pane in which component is located
     * @param scrollPane
     */
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

        drawSelection(g2d);

        drawCursor(g2d);
    }

    private void drawSelection(Graphics2D graphics) {
        ElementRegistry registry = document.getDocumentView().getElementRegistry();

        traverseSelection(element -> {
            DocumentViewModelChildlessElement view = registry.getElementView(element);

            Point position = view.getViewContext().getPosition();
            Dimension size = view.getSize();

            graphics.setColor(SELECTION_COLOR);
            graphics.fillRect(position.x, position.y, size.width, size.height);
        });
    }

    private void traverseSelection(Consumer<DocumentModelChildlessElement> consumer) {
        if (!hasSelection) {
            return;
        }

        CursorContext selectionStart = this.selectionStart;
        CursorContext selectionEnd = this.selectionEnd;

        if (selectionEnd.getParagraphIndex() < selectionStart.getParagraphIndex() ||
                (selectionStart.getParagraphIndex() == selectionEnd.getParagraphIndex() &&
                        selectionEnd.getNextElementIndex() < selectionStart.getNextElementIndex())) {
            selectionStart = this.selectionEnd;
            selectionEnd = this.selectionStart;
        }

        for (int paragraph = selectionStart.getParagraphIndex();
             paragraph <= selectionEnd.getParagraphIndex(); paragraph++) {
            ParagraphElement paragraphElement =
                    (ParagraphElement) document.getRootElement().getChildren().get(paragraph);
            int start;
            int end;

            if (paragraph == selectionStart.getParagraphIndex()) {
                start = selectionStart.getNextElementIndex();
            } else {
                start = 0;
            }

            if (paragraph == selectionEnd.getParagraphIndex()) {
                end = selectionEnd.getNextElementIndex();
            } else {
                end = paragraphElement.getChildren().size();
            }

            List<Element> elements = paragraphElement.getChildren();

            for (int index = start; index < end; index++) {
                DocumentModelChildlessElement element = (DocumentModelChildlessElement) elements.get(index);
                consumer.accept(element);
            }
        }
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

        boolean cursorRight = false;
        if (nextElementIndex > 0) {
            cursorRight = true;
        }

        DocumentModelChildlessElement element = getCurrentElement();

        DocumentViewModelElement view = getElementView(element);
        ViewContext context = view.getViewContext();

        size = context.getSize();
        Point elementPosition = context.getPosition();

        if (cursorRight) {
            position = elementPosition;
        } else {
            position = new Point(elementPosition.x - size.width, elementPosition.y);
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

            case KeyEvent.VK_ENTER:
                splitParagraphAtInsertionPoint();
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
        Style style = getCurrentElement().getStyle();
        List<Element> paragraphChildren = getParagraph().getChildren();
        CharacterElement characterElement = new CharacterElement(character, style);

        paragraphChildren.add(nextElementIndex, characterElement);
        nextElementIndex++;

        document.compose();
        repaint();
    }

    private void splitParagraphAtInsertionPoint() {
        ParagraphElement paragraph = getParagraph();
        List<Element> children = paragraph.getChildren();

        List<Element> tail = children.subList(nextElementIndex, children.size());

        ParagraphElement newParagraph = new ParagraphElement();
        newParagraph.setAlignment(paragraph.getAlignment());
        newParagraph.setFirstLineIndent(paragraph.getFirstLineIndent());

        tail.forEach(newParagraph::addChild);
        tail.clear();

        paragraphIndex++;
        nextElementIndex = 0;

        document.getRootElement().getChildren().add(paragraphIndex, newParagraph);

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
        LineElement currentLine = getCurrentLine();
        LineElement newLine = currentLine;

        while (currentLine == newLine && !(paragraphIndex == 0 && newLine.isFirstLine())) {
            moveLeft();
            newLine = getCurrentLine();
        }
    }

    private void moveDown() {
        LineElement currentLine = getCurrentLine();
        LineElement newLine = currentLine;

        while (currentLine == newLine &&
                !(paragraphIndex == document.getRootElement().getChildren().size() - 1 && newLine.isLastLine())) {
            moveRight();
            newLine = getCurrentLine();
        }
    }

    private LineElement getCurrentLine() {
        ElementRegistry registry = document.getDocumentView().getElementRegistry();
        DocumentModelChildlessElement currentElement = getCurrentElement();
        return registry.getLine(currentElement);
    }

    private DocumentModelChildlessElement getCurrentElement() {
        int index = nextElementIndex;
        if (index > 0) {
            index--;
        }

        ParagraphElement paragraph = getParagraph();

        if (paragraph.getChildren().size() > 0) {
            return (DocumentModelChildlessElement) getParagraph().getChildren().get(index);
        } else {
            return paragraph.getDummyElement();
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

    /**
     * Toggles the indentation of the paragraph under cursor
     */
    public void toggleCurrentParagraphIndent() {
        ParagraphElement paragraph = getParagraph();
        paragraph.setFirstLineIndent(!paragraph.getFirstLineIndent());

        document.compose();
        repaint();
    }

    /**
     * Makes current paragraph centered
     */
    public void centerCurrentParagraph() {
        getParagraph().setAlignment(ParagraphAlignment.CENTER);
        document.compose();
        repaint();
    }

    /**
     * Makes current paragraph left-aligned
     */
    public void leftAlignCurrentParagraph() {
        getParagraph().setAlignment(ParagraphAlignment.LEFT);
        document.compose();
        repaint();
    }

    /**
     * Makes current paragraph right-aligned
     */
    public void rightAlignCurrentParagraph() {
        getParagraph().setAlignment(ParagraphAlignment.RIGHT);
        document.compose();
        repaint();
    }

    /**
     * Makes current paragraph justified
     */
    public void justifyCurrentParagraph() {
        getParagraph().setAlignment(ParagraphAlignment.JUSTIFY);
        document.compose();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        hasSelection = false;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        selectionStart = getNearestElement(e.getX(), e.getY());
        selectionEnd = selectionStart;

        paragraphIndex = selectionStart.getParagraphIndex();
        nextElementIndex = selectionStart.getNextElementIndex();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!hasSelection) {
            return;
        }

        if (selectionEnd.getParagraphIndex() < selectionStart.getParagraphIndex() ||
                (selectionStart.getParagraphIndex() == selectionEnd.getParagraphIndex() &&
                        selectionEnd.getNextElementIndex() < selectionStart.getNextElementIndex())) {
            CursorContext temp = selectionStart;
            selectionStart = selectionEnd;
            selectionEnd = temp;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        hasSelection = true;

        selectionEnd = getNearestElement(e.getX(), e.getY());
        paragraphIndex = selectionEnd.getParagraphIndex();
        nextElementIndex = selectionEnd.getNextElementIndex();

        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private CursorContext getNearestElement(int x, int y) {
        // TODO: optimize by iterating only visible elements
        int selectedParagraphIndex = 0;
        int selectedElementIndex = 0;

        double distance = Double.MAX_VALUE;

        ElementRegistry registry = document.getDocumentView().getElementRegistry();

        List<Element> paragraphs = document.getRootElement().getChildren();
        for (int paragraph = 0; paragraph < paragraphs.size(); paragraph++) {
            ParagraphElement paragraphElement = (ParagraphElement) paragraphs.get(paragraph);
            List<Element> elements = paragraphElement.getChildren();
            List<Element> elementsIterable = elements;
            int elementsCount = elements.size() + 1;

            if (elements.size() == 0) {
                elementsIterable = new ArrayList<>(1);
                elementsIterable.add(paragraphElement.getDummyElement());
            }

            Dimension size = null;
            Point leftTop = null;
            int anchorX;
            int anchorY;

            for (int element = 0; element < elementsCount; element++) {
                if (element < elementsIterable.size()) {
                    DocumentModelChildlessElement model = (DocumentModelChildlessElement) elementsIterable.get(element);
                    DocumentViewModelChildlessElement viewModel = registry.getElementView(model);

                    size = viewModel.getSize();
                    leftTop = viewModel.getViewContext().getPosition();

                    anchorX = leftTop.x + size.width / 2;
                    anchorY = leftTop.y + size.height / 2;
                } else {
                    anchorX = leftTop.x + size.width;
                    anchorY = leftTop.y + size.height;
                }

                double currentDistance = getDistance(x, y, anchorX, anchorY);

                if (currentDistance < distance) {
                    distance = currentDistance;
                    selectedElementIndex = element;
                    selectedParagraphIndex = paragraph;
                }
            }
        }

        return new CursorContext(selectedParagraphIndex, selectedElementIndex);
    }

    private double getDistance(int x1, int y1, int x2, int y2) {
        return Math.hypot(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    /**
     * Sets the style of selected text to plain
     */
    public void makeSelectionNormal() {
        changeSelectionFontStyle(Font.PLAIN);
    }

    /**
     * Sets the style of selected text to bold
     */
    public void makeSelectionBold() {
        changeSelectionFontStyle(Font.BOLD);
    }

    /**
     * Sets the style of selected text to italic
     */
    public void makeSelectionItalic() {
        changeSelectionFontStyle(Font.ITALIC);
    }

    private void changeSelectionFontStyle(int fontStyle) {
        traverseSelection(element -> {
            Style style = element.getStyle();
            Font font = style.getFont();
            Font newFont = new Font(font.getName(), fontStyle, font.getSize());
            Style newStyle = new Style(newFont, style.getForegroundColor(), style.getBackgroundColor());
            element.setStyle(newStyle);
        });

        document.compose();
        repaint();
    }
}
