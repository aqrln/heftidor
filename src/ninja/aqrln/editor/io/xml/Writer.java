package ninja.aqrln.editor.io.xml;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.model.*;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.Color;
import java.awt.Font;
import java.io.OutputStream;

/**
 * XML writer
 * @author Alexey Orlenko
 */
public class Writer {
    private Document document;
    private org.w3c.dom.Document xml;

    /**
     * XML writer constructor
     * @param document document to seriallize
     */
    public Writer(Document document) {
        this.document = document;
    }

    /**
     * Write the document to a stream
     * @param stream
     */
    public void write(OutputStream stream) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

            xml = builder.newDocument();

            Writer.Visitor visitor = new Writer.Visitor();
            visitor.visitRootElement(document.getRootElement());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(xml);
            StreamResult result = new StreamResult(stream);

            transformer.transform(source, result);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private class Visitor implements DocumentModelVisitor {
        private Element root;
        private Element paragraph;
        private Element text;
        private Style prevStyle;
        private StringBuilder textValue;

        @Override
        public void visitCharacterElement(CharacterElement element) {
            Style style = element.getStyle();

            if (style != prevStyle) {
                openNewTextElement(style);
                prevStyle = style;
            }

            textValue.append(element.getCharacter());
        }

        private void openNewTextElement(Style style) {
            dumpTextContent();

            textValue = new StringBuilder();
            text = xml.createElement(Constants.TEXT_ELEMENT);

            text.setAttribute(Constants.FONT_NAME, style.getFont().getName());
            text.setAttribute(Constants.FONT_STYLE, getStyleName(style.getFont().getStyle()));
            text.setAttribute(Constants.FONT_SIZE, style.getFont().getSize() + "");
            text.setAttribute(Constants.BACKGROUND_COLOR, dumpColor(style.getBackgroundColor()));
            text.setAttribute(Constants.FOREGROUND_COLOR, dumpColor(style.getForegroundColor()));

            paragraph.appendChild(text);
        }

        private String dumpColor(Color color) {
            int red = color.getRed();
            int green = color.getGreen();
            int blue = color.getBlue();

            return String.format("%02x%02x%02x", red, green, blue);
        }

        private String getStyleName(int style) {
            switch (style) {
                case Font.PLAIN:
                    return Constants.STYLE_PLAIN;
                case Font.BOLD:
                    return Constants.STYLE_BOLD;
                case Font.ITALIC:
                    return Constants.STYLE_ITALIC;
                default:
                    throw new IllegalArgumentException("invalid font style");
            }
        }

        private void dumpTextContent() {
            if (textValue != null) {
                String content = textValue.toString();
                text.setTextContent(content);
            }
        }

        @Override
        public void visitParagraphElement(ParagraphElement element) {
            prevStyle = null;
            textValue = null;
            text = null;

            paragraph = xml.createElement(Constants.PARAGRAPH_ELEMENT);
            paragraph.setAttribute(Constants.PARAGRAPH_ALIGNMENT, element.getAlignment().toString());
            paragraph.setAttribute(Constants.PARAGRAPH_INDENT, element.getFirstLineIndent() + "");

            visitChildren(element);

            dumpTextContent();
            root.appendChild(paragraph);
        }

        @Override
        public void visitRootElement(RootElement element) {
            root = xml.createElement(Constants.DOCUMENT_ELEMENT);
            visitChildren(element);
            xml.appendChild(root);
        }

        private void visitChildren(DocumentModelCompositeElement element) {
            for (ninja.aqrln.editor.dom.core.Element child : element.getChildren()) {
                DocumentModelElement modelElement = (DocumentModelElement) child;
                modelElement.accept(this);
            }
        }
    }
}
