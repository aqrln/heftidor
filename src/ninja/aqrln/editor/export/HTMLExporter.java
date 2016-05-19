package ninja.aqrln.editor.export;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.model.*;

/**
 * @author Alexey Orlenko
 */
public class HTMLExporter implements DocumentModelVisitor {
    private StringBuilder stringBuilder;
    private String title;

    public HTMLExporter(String title) {
        stringBuilder = new StringBuilder();
        this.title = title;
    }

    public String getString() {
        return stringBuilder.toString();
    }

    public static String toHTML(Document document) {
        HTMLExporter exporter = new HTMLExporter(document.getName());
        document.getRootElement().accept(exporter);
        return exporter.getString();
    }

    @Override
    public void visitCharacterElement(CharacterElement element) {
        stringBuilder.append(htmlEscape(element.getCharacter()));
    }

    @Override
    public void visitParagraphElement(ParagraphElement element) {
        String alignment = "";
        switch (element.getAlignment()) {
            case LEFT:
                alignment = "left";
                break;
            case RIGHT:
                alignment = "right";
                break;
            case CENTER:
                alignment = "center";
                break;
            case JUSTIFY:
                alignment = "justify";
                break;
        }

        stringBuilder.append("<p style=\"text-align: ");
        stringBuilder.append(alignment);
        stringBuilder.append(";\">");

        for (Element character : element.getChildren()) {
            ((DocumentModelElement) character).accept(this);
        }

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
