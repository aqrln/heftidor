package ninja.aqrln.editor.dom.core;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

/**
 * Element style
 * @author Alexey Orlenko
 */
public class Style implements Serializable {
    private Font font;
    private Color backgroundColor;
    private Color foregroundColor;

    /**
     * Default font
     */
    public static final Font DEFAULT_FONT = new Font(Font.SERIF, Font.PLAIN, 14);

    /**
     * Default foreground color
     */
    public static final Color DEFAULT_FG_COLOR = Color.BLACK;

    /**
     * Default background color
     */
    public static final Color DEFAULT_BG_COLOR = Color.WHITE;

    /**
     * Default style
     */
    public static final Style DEFAULT_STYLE = new Style();

    /**
     * @param font font to be used
     * @param foregroundColor color of the text
     * @param backgroundColor background color
     */
    public Style(Font font, Color foregroundColor, Color backgroundColor) {
        this.font = font;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
    }

    /**
     * @param font font to be used
     * @param foregroundColor color of the text
     */
    public Style(Font font, Color foregroundColor) {
        this(font, foregroundColor, DEFAULT_BG_COLOR);
    }

    /**
     * @param font font to be used
     */
    public Style(Font font) {
        this(font, DEFAULT_FG_COLOR, DEFAULT_BG_COLOR);
    }

    /**
     * Default constructor
     */
    public Style() {
        this(DEFAULT_FONT, DEFAULT_FG_COLOR, DEFAULT_BG_COLOR);
    }

    /**
     * Get used font
     * @return font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Set a new font
     * @param font font to use
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Get text color
     * @return foreground color
     */
    public Color getForegroundColor() {
        return foregroundColor;
    }

    /**
     * Set text color
     * @param color new foreground color
     */
    public void setForegroundColor(Color color) {
        foregroundColor = color;
    }

    /**
     * Get background color
     * @return background color
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Set background color
     * @param color new background color
     */
    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }
}
