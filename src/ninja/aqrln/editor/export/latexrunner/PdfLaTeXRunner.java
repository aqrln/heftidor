package ninja.aqrln.editor.export.latexrunner;

import java.io.IOException;

/**
 * @author Alexey Orlenko
 */
public class PdfLaTeXRunner extends LaTeXRunner {
    public PdfLaTeXRunner(LaTeXRunner successor) {
        super(successor);
    }

    @Override
    public void run(String filename, String directory) {
        try {
            runCommand("pdflatex", filename, directory);
        } catch (IOException e) {
            super.run(filename, directory);
        }
    }
}
