package ninja.aqrln.editor;

import ninja.aqrln.editor.net.ssl.SSLHelper;
import ninja.aqrln.editor.ui.ApplicationUI;

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
        SSLHelper.installCertificate();
        ApplicationUI app = ApplicationUI.getInstance();
        app.run();
    }

}
