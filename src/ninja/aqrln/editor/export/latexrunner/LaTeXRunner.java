package ninja.aqrln.editor.export.latexrunner;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;

/**
 * @author Alexey Orlenko
 */
public abstract class LaTeXRunner {
    private LaTeXRunner successor;
    private static LaTeXRunner chain = null;

    public LaTeXRunner(LaTeXRunner successor) {
        this.successor = successor;
    }

    public void run(String filename, String directory) {
        if (successor != null) {
            successor.run(filename, directory);
        } else {
            JOptionPane.showMessageDialog(null, "No compatible LaTeX compiler was found on your system",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void runCommand(String compiler, String filename, String workingDirectory) throws IOException {
        Runtime.getRuntime().exec(new String[] { compiler, filename }, null, new File(workingDirectory));
    }

    public static LaTeXRunner getRunner() {
        if (chain == null) {
            chain = new XeLaTeXRunner(new LuaLaTeXRunner(new PdfLaTeXRunner(null)));
        }

        return chain;
    }
}
