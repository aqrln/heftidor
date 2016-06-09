package ninja.aqrln.editor.export.latexrunner;

import java.io.IOException;

/**
 * LuaLaTeX runner
 * @author Alexey Orlenko
 */
public class LuaLaTeXRunner extends LaTeXRunner {
    public LuaLaTeXRunner(LaTeXRunner successor) {
        super(successor);
    }

    @Override
    public void run(String filename, String directory) {
        try {
            runCommand("lualatex", filename, directory);
        } catch (IOException e) {
            super.run(filename, directory);
        }
    }
}
