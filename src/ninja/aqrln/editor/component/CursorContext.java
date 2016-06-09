package ninja.aqrln.editor.component;

/**
 * Encapsulates cursor position
 *
 * @author Alexey Orlenko
 */
public class CursorContext {
    private int paragraphIndex;
    private int nextElementIndex;

    /**
     * Public constructor
     * @param paragraphIndex index of the current paragraph
     * @param nextElementIndex index of the paragraph element under the cursor
     */
    public CursorContext(int paragraphIndex, int nextElementIndex) {
        this.paragraphIndex = paragraphIndex;
        this.nextElementIndex = nextElementIndex;
    }

    /**
     * @return index of the current paragraph
     */
    public int getParagraphIndex() {
        return paragraphIndex;
    }

    /**
     * @return index of the paragraph element under the cursor
     */
    public int getNextElementIndex() {
        return nextElementIndex;
    }
}
