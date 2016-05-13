package ninja.aqrln.editor.component;

import ninja.aqrln.editor.dom.core.CompositeElement;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * @author Alexey Orlenko
 */
public class EditorPane extends JPanel {
    private CompositeElement rootElement;

    public EditorPane(CompositeElement element) {
        this.rootElement = element;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D)graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        rootElement.draw(g2d, 0, 0);
    }
}
