package ninja.aqrln.editor.export.latexrunner;

import java.io.IOException;

/**
 * @author Alexey Orlenko
 */
public class XeLaTeXRunner extends LaTeXRunner {
    public XeLaTeXRunner(LaTeXRunner successor) {
        super(successor);
    }

    @Override
    public void run(String filename, String directory) {
        try {
            runCommand("xelatex", filename, directory);
        } catch (IOException e) {
            super.run(filename, directory);
        }
    }
}
