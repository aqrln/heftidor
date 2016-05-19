package ninja.aqrln.editor;

import ninja.aqrln.editor.ui.platform.ApplicationUI;

/**
 * Main class of the application
 *
 * @author Alexey Orlenko
 */
public class Main {

    /**
     * ApplicationUI entry point
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        ApplicationUI app = ApplicationUI.getInstance();
        app.run();
    }

}
