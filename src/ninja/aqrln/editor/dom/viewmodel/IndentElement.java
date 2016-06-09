package ninja.aqrln.editor.dom.viewmodel;

/**
 * Helper class that is used to render first-line indents
 * @author Alexey Orlenko
 */
public class IndentElement extends SpaceElement {

    /**
     * Paragraph indentation size
     */
    public static final int INDENT_SIZE = 32;

    /**
     * Public constructor
     */
    public IndentElement() {
        super(INDENT_SIZE);
    }
}
