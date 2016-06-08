package ninja.aqrln.editor.component;

/**
 * @author Alexey Orlenko
 */
public class CursorContext {
    private int paragraphIndex;
    private int nextElementIndex;

    public CursorContext(int paragraphIndex, int nextElementIndex) {
        this.paragraphIndex = paragraphIndex;
        this.nextElementIndex = nextElementIndex;
    }

    public int getParagraphIndex() {
        return paragraphIndex;
    }

    public int getNextElementIndex() {
        return nextElementIndex;
    }
}
