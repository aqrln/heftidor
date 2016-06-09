package ninja.aqrln.editor.export;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.model.*;

import java.awt.Color;
import java.awt.Font;

/**
 * HTML exporter
 * @author Alexey Orlenko
 */
public class HTMLExporter implements DocumentModelVisitor {
    private StringBuilder stringBuilder;
    private String title;
    private Style prevStyle = null;

    /**
     * HTMLExporter constructor
     * @param title document title
     */
    public HTMLExporter(String title) {
        stringBuilder = new StringBuilder();
        this.title = title;
    }

    /**
     * Get the result string
     * @return HTML source as string
     */
    public String getString() {
        return stringBuilder.toString();
    }

    /**
     * Convenience static method
     *
     * @param document document to be exported
     * @return HTML source
     */
    public static String toHTML(Document document) {
        HTMLExporter exporter = new HTMLExporter(document.getName());
        document.getRootElement().accept(exporter);
        return exporter.getString();
    }

    private void writeColorComponent(int colorComponent) {
        String string = Integer.toHexString(colorComponent);
        if (string.length() < 2) {
            stringBuilder.append('0');
        }
        stringBuilder.append(string);
    }

    private void writeColor(Color color) {
        stringBuilder.append('#');
        writeColorComponent(color.getRed());
        writeColorComponent(color.getGreen());
        writeColorComponent(color.getBlue());
    }

    private void writeFontFamily(String family) {
        switch (family) {
            case "Serif":
                stringBuilder.append("serif");
                break;
            case "Monospaced":
                stringBuilder.append("monospace");
                break;
            default:
                stringBuilder.append("sans-serif");
                break;
        }
    }

    private void writeStyle(Style style) {
        stringBuilder.append("<span style=\"");

        stringBuilder.append("color:");
        writeColor(style.getForegroundColor());
        stringBuilder.append(";background-color:");
        writeColor(style.getBackgroundColor());

        Font font = style.getFont();

        stringBuilder.append(";font-family:");
        writeFontFamily(font.getFamily());
        stringBuilder.append(";font-size:");
        stringBuilder.append(font.getSize());
        stringBuilder.append("px;");

        if (font.isBold()) {
            stringBuilder.append("font-weight:bold;");
        }

        if (font.isItalic()) {
            stringBuilder.append("font-style:italic;");
        }

        stringBuilder.append("\">");
    }

    private void closeStyle() {
        stringBuilder.append("</span>");
    }

    @Override
    public void visitCharacterElement(CharacterElement element) {
        Style style = element.getStyle();

        if (style != prevStyle) {
            if (prevStyle != null) {
                closeStyle();
            }

            writeStyle(style);
            prevStyle = style;
        }

        stringBuilder.append(htmlEscape(element.getCharacter()));
    }

    @Override
    public void visitParagraphElement(ParagraphElement element) {
        stringBuilder.append("<p style=\"text-align: ");
        stringBuilder.append(element.getAlignment().toString());
        stringBuilder.append(";\">");

        for (Element character : element.getChildren()) {
            ((DocumentModelElement) character).accept(this);
        }

        closeStyle();
        prevStyle = null;

        stringBuilder.append("</p>");
    }

    @Override
    public void visitRootElement(RootElement element) {
        String preamble = "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>" +
                htmlEscape(title) + "</title></head><body>";
        stringBuilder.append(preamble);

        for (Element paragraph : element.getChildren()) {
            ((DocumentModelElement) paragraph).accept(this);
        }

        stringBuilder.append("</body></html>");
    }

    private String htmlEscape(String string) {
        StringBuilder out = new StringBuilder();
        for (char c : string.toCharArray()) {
            out.append(htmlEscape(c));
        }
        return out.toString();
    }

    private String htmlEscape(char character) {
        if ("\"<>&".indexOf(character) != -1) {
            return "&#" + (int) character + ";";
        } else {
            return "" + character;
        }
    }
}
