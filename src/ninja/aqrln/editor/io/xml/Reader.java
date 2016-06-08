package ninja.aqrln.editor.io.xml;

import ninja.aqrln.editor.dom.Document;
import ninja.aqrln.editor.dom.core.Style;
import ninja.aqrln.editor.dom.model.CharacterElement;
import ninja.aqrln.editor.dom.model.ParagraphAlignment;
import ninja.aqrln.editor.dom.model.ParagraphElement;
import ninja.aqrln.editor.dom.model.RootElement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alexey Orlenko
 */
public class Reader {
    private InputStream stream;

    public Reader(InputStream stream) {
        this.stream = stream;
    }

    public Document read() throws IOException {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();

            org.w3c.dom.Document xmlDocument = builder.parse(new InputSource(stream));
            xmlDocument.getDocumentElement().normalize();

            Reader.Builder documentBuilder = new Reader.Builder(xmlDocument);
            documentBuilder.parse();
            return documentBuilder.getDocument();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class Builder {
        private org.w3c.dom.Document xml;
        private RootElement root;
        private ParagraphElement paragraph;

        public Builder(org.w3c.dom.Document xml) {
            this.xml = xml;
        }

        public void parse() {
            recursiveBuild(xml.getDocumentElement());
        }

        private void recursiveBuild(Element element) {
            switch (element.getTagName()) {
                case Constants.DOCUMENT_ELEMENT:
                    buildDocument(element);
                    break;

                case Constants.PARAGRAPH_ELEMENT:
                    buildParagraph(element);
                    break;

                case Constants.TEXT_ELEMENT:
                    buildText(element);
                    break;

                default:
                    throw new IllegalArgumentException("unexpected element");
            }
        }

        private void buildChildren(Element element) {
            NodeList children = element.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node node = children.item(i);
                if (node instanceof Element) {
                    recursiveBuild((Element) node);
                }
            }
        }

        private void buildDocument(Element element) {
            root = new RootElement();
            buildChildren(element);
        }

        private void buildParagraph(Element element) {
            paragraph = new ParagraphElement();
            root.addChild(paragraph);

            String alignmentString = element.getAttribute(Constants.PARAGRAPH_ALIGNMENT);
            ParagraphAlignment alignment = ParagraphAlignment.fromString(alignmentString);
            paragraph.setAlignment(alignment);

            boolean indent = Boolean.parseBoolean(element.getAttribute(Constants.PARAGRAPH_INDENT));
            paragraph.setFirstLineIndent(indent);

            buildChildren(element);
        }

        private void buildText(Element element) {
            String fontName = element.getAttribute(Constants.FONT_NAME);
            int fontStyle = parseStyle(element.getAttribute(Constants.FONT_STYLE));
            int fontSize = Integer.parseInt(element.getAttribute(Constants.FONT_SIZE));
            Font font = new Font(fontName, fontStyle, fontSize);

            Color backgroundColor = parseColor(element.getAttribute(Constants.BACKGROUND_COLOR));
            Color foregroundColor = parseColor(element.getAttribute(Constants.FOREGROUND_COLOR));

            Style style = new Style(font, foregroundColor, backgroundColor);
            String content = element.getTextContent();

            for (char character : content.toCharArray()) {
                CharacterElement characterElement = new CharacterElement(character, style);
                paragraph.addChild(characterElement);
            }
        }

        private Color parseColor(String representation) {
            String redString = representation.substring(0, 2);
            String greenString = representation.substring(2, 4);
            String blueString = representation.substring(4, 6);

            int red = Integer.parseInt(redString, 16);
            int green = Integer.parseInt(greenString, 16);
            int blue = Integer.parseInt(blueString, 16);

            return new Color(red, green, blue);
        }

        private int parseStyle(String styleName) {
            switch (styleName) {
                case Constants.STYLE_PLAIN:
                    return Font.PLAIN;
                case Constants.STYLE_BOLD:
                    return Font.BOLD;
                case Constants.STYLE_ITALIC:
                    return Font.ITALIC;
                default:
                    throw new IllegalArgumentException("invalid font style");
            }
        }

        public Document getDocument() {
            Document document = new Document();
            document.setRootElement(root);
            return document;
        }
    }
}
