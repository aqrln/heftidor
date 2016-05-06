package ninja.aqrln.editor;

import ninja.aqrln.editor.core.Application;

/**
 * Main class of the application
 *
 * @author Alexey Orlenko
 */
public class Main {

    /**
     * Application entry point
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Application app = Application.getInstance();
        app.run();
    }

}
