package ninja.aqrln.editor.dom.model;

/**
 * @author Alexey Orlenko
 */
public enum ParagraphAlignment {
    LEFT, RIGHT, CENTER, JUSTIFY;

    @Override
    public String toString() {
        switch (this) {
            case LEFT:
                return "left";
            case RIGHT:
                return "right";
            case CENTER:
                return "center";
            case JUSTIFY:
                return "justify";
        }
        return null;
    }

    public static ParagraphAlignment fromString(String representation) {
        switch (representation.toLowerCase()) {
            case "left":
                return LEFT;
            case "right":
                return RIGHT;
            case "center":
                return CENTER;
            case "justify":
                return JUSTIFY;
            default:
                throw new IllegalArgumentException("invalid alignment");
        }
    }
}
