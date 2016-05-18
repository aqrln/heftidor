package ninja.aqrln.editor.component;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.viewmodel.ComposedRootElement;
import ninja.aqrln.editor.dom.viewmodel.PageElement;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * @author Alexey Orlenko
 */
public class EditorPane extends JPanel {
    private Document document;

    public EditorPane(Document document) {
        this.document = document;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D)graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int paddingLeft = (getSize().width - PageElement.PAGE_SIZE.width) / 2;

        ComposedRootElement rootElement = document.getDocumentView();
        Dimension contentSize = rootElement.getSize();
        setPreferredSize(contentSize);
        revalidate();

        DocumentRenderer renderer = new DocumentRenderer(g2d, paddingLeft, 0);
        rootElement.accept(renderer);
    }
}
