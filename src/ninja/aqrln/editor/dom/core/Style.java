package ninja.aqrln.editor.dom.core;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Alexey Orlenko
 */
public class Style {
    private Font font;
    private Color backgroundColor;
    private Color foregroundColor;

    public static final Font DEFAULT_FONT = new Font(Font.SERIF, Font.PLAIN, 14);
    public static final Color DEFAULT_FG_COLOR = Color.BLACK;
    public static final Color DEFAULT_BG_COLOR = Color.WHITE;

    public static final Style DEFAULT_STYLE = new Style();

    public Style(Font font, Color foregroundColor, Color backgroundColor) {
        this.font = font;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
    }

    public Style(Font font, Color foregroundColor) {
        this(font, foregroundColor, DEFAULT_BG_COLOR);
    }

    public Style(Font font) {
        this(font, DEFAULT_FG_COLOR, DEFAULT_BG_COLOR);
    }

    public Style() {
        this(DEFAULT_FONT, DEFAULT_FG_COLOR, DEFAULT_BG_COLOR);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color color) {
        foregroundColor = color;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }
}
