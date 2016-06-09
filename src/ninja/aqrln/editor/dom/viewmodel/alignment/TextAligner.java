package ninja.aqrln.editor.dom.viewmodel.alignment;

import ninja.aqrln.editor.dom.model.ParagraphAlignment;

/**
 * Handles text alignment
 * @author Alexey Orlenko
 */
public class TextAligner {
    private static TextAlignmentStrategy left = new LeftAlignmentStrategy();
    private static TextAlignmentStrategy right = new RightAlignmentStrategy();
    private static TextAlignmentStrategy center = new CenterAlignmentStrategy();
    private static TextAlignmentStrategy justify = new JustifyAlignmentStrategy();

    /**
     * Text alignment strategy factory method
     * @param alignment paragraph alignment
     * @return alignment strategy
     */
    public static TextAlignmentStrategy getAligner(ParagraphAlignment alignment) {
        TextAlignmentStrategy strategy = null;

        switch (alignment) {
            case LEFT:
                strategy = left;
                break;
            case RIGHT:
                strategy = right;
                break;
            case CENTER:
                strategy = center;
                break;
            case JUSTIFY:
                strategy = justify;
                break;
        }

        return strategy;
    }
}
