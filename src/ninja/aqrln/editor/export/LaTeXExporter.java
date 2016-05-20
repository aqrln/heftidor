package ninja.aqrln.editor.export;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.core.Element;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.model.*;

import java.awt.Font;

/**
 * @author Alexey Orlenko
 */
public class LaTeXExporter implements DocumentModelVisitor {
    private StringBuilder stringBuilder;
    private boolean firstParagraph;
    private Style prevStyle;

    public LaTeXExporter() {
        stringBuilder = new StringBuilder();
        firstParagraph = true;
    }

    public String getString() {
        return stringBuilder.toString();
    }

    public static String toLaTeX(Document document) {
        LaTeXExporter exporter = new LaTeXExporter();
        document.getRootElement().accept(exporter);
        return exporter.getString();
    }

    private void openStyle(Style style) {
        Font font = style.getFont();

        if (font.getFamily().equals("SansSerif")) {
            stringBuilder.append("\\textsf{");
        }

        if (font.getFamily().equals("Monospaced")) {
            stringBuilder.append("\\texttt{");
        }

        if (font.isItalic()) {
            stringBuilder.append("\\textit{");
        }

        if (font.isBold()) {
            stringBuilder.append("\\textbf{");
        }
    }

    private void closeStyle(Style style) {
        Font font = style.getFont();

        if (font.isBold()) {
            stringBuilder.append('}');
        }

        if (font.isItalic()) {
            stringBuilder.append('}');
        }

        if (!font.getFamily().equals("Serif")) {
            stringBuilder.append('}');
        }
    }

    @Override
    public void visitCharacterElement(CharacterElement element) {
        Style style = element.getStyle();

        if (style != prevStyle) {
            if (prevStyle != null) {
                closeStyle(prevStyle);
            }

            openStyle(style);
            prevStyle = style;
        }

        stringBuilder.append(latexEscape(element.getCharacter()));
    }

    @Override
    public void visitParagraphElement(ParagraphElement element) {
        if (firstParagraph) {
            firstParagraph = false;
        } else {
            stringBuilder.append('\n');
        }

        for (Element child : element.getChildren()) {
            ((DocumentModelElement) child).accept(this);
        }

        closeStyle(prevStyle);
        prevStyle = null;

        stringBuilder.append('\n');
    }

    @Override
    public void visitRootElement(RootElement element) {
        stringBuilder.append("\\documentclass{article}\n");
        stringBuilder.append("\\begin{document}\n");

        for (Element paragraph : element.getChildren()) {
            ((DocumentModelElement) paragraph).accept(this);
        }

        stringBuilder.append("\\end{document}\n");
    }

    private String latexEscape(char character) {
        if (character == '\\') {
            return "\\textbackslash{}";
        }

        if ("~^".indexOf(character) != -1) {
            return "\\" + character + "{}";
        }

        if ("#$%&_{}".indexOf(character) != -1) {
            return "\\" + character;
        }

        return "" + character;
    }
}
