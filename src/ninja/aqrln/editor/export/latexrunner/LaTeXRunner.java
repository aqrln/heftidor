package ninja.aqrln.editor.export.latexrunner;

import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;

/**
 * Abstract LaTeX typesetter runner.
 * Implements the Chain Of Responsibility pattern
 *
 * @author Alexey Orlenko
 */
public abstract class LaTeXRunner {
    private LaTeXRunner successor;
    private static LaTeXRunner chain = null;

    /**
     * LaTeX runner constructor. Takes the successor that should be
     * tried if this runner fails, or null if it is the last one in
     * the chain of responsibility
     *
     * @param successor next runner
     */
    public LaTeXRunner(LaTeXRunner successor) {
        this.successor = successor;
    }

    /**
     * Run LaTeX typesetter
     *
     * @param filename tex file name
     * @param directory working directory
     */
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

    /**
     * Create runner chain
     * @return latex runners chain
     */
    public static LaTeXRunner getRunner() {
        if (chain == null) {
            chain = new XeLaTeXRunner(new LuaLaTeXRunner(new PdfLaTeXRunner(null)));
        }

        return chain;
    }
}
