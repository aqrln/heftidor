package ninja.aqrln.editor.component;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.core.Style;
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

    private PageElement currentPage;
    private LineElement currentLine;
    private WordElement currentWord;
    private DocumentViewModelChildlessElement currentElement;

    private ListIterator<Element> pageIterator;
    private ListIterator<Element> lineIterator;
    private ListIterator<Element> wordIterator;

    private static final Stroke CURSOR_STROKE = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    public EditorPane(Document document) {
        this.document = document;
        setPreferredSize(new Dimension(800, 600));
        setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

        document.compose();
        currentPage = (PageElement) document.getDocumentView().getChildren().get(0);
        currentLine = (LineElement) currentPage.getChildren().get(0);
        currentWord = (WordElement) currentLine.getChildren().get(0);
        currentElement = null;

        pageIterator = currentPage.getChildren().listIterator();
        lineIterator = currentLine.getChildren().listIterator();
        wordIterator = currentWord.getChildren().listIterator();

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
            return currentElement.getViewContext();
        }

        Dimension size;
        Point position;

        if (currentWord.getChildren().size() != 0) {
            ViewContext nextElementContext =
                    ((DocumentViewModelElement) currentWord.getChildren().get(0)).getViewContext();
            size = nextElementContext.getSize();
            Point nextPosition = nextElementContext.getPosition();
            position = new Point(nextPosition.x - size.width, nextPosition.y);
        } else {
            size = new Dimension(0, Style.DEFAULT_FONT.getSize());
            position = currentWord.getViewContext().getPosition();
        }

        return new ViewContext(size, position);
    }

    @Override
    public void keyTyped(KeyEvent e) {

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
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void moveLeft() {
        if (currentElement == null) {
            moveUp();
            while (wordIterator.hasNext()) {
                wordIterator.next();
            }
        } else if (wordIterator.hasPrevious()) {
            currentElement = (DocumentViewModelChildlessElement) wordIterator.previous();
        } else {
            currentElement = null;
        }
    }

    public void moveRight() {
        if (wordIterator.hasNext()) {

        }
    }

    public void moveUp() {

    }

    public void moveDown() {

    }
}
