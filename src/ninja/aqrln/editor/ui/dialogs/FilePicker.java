package ninja.aqrln.editor.ui.dialogs;

import java.awt.FileDialog;
import java.awt.Frame;

/**
 * @author Alexey Orlenko
 */
public abstract class FilePicker {
    public static final String DOCUMENT_EXTENSION = ".editordoc";

    private String lastDirectory = System.getProperty("user.home");

    private String showDialog(String title, int type, String extension) {
        FileDialog dialog = new FileDialog((Frame) null, title, type);

        dialog.setDirectory(lastDirectory);
        setFilter(dialog, extension);
        dialog.setVisible(true);

        String directory = dialog.getDirectory();
        String filename = dialog.getFile();

        if (filename == null) {
            return null;
        }

        if (!filename.endsWith(extension)) {
            filename += extension;
        }

        String separator = getSeparator();
        if (!directory.endsWith(separator)) {
            directory += separator;
        }

        lastDirectory = directory;

        return directory + filename;
    }

    public String showLoadDialog() {
        return showDialog("Open file", FileDialog.LOAD, DOCUMENT_EXTENSION);
    }

    public String showSaveDialog() {
        return showDialog("Save file", FileDialog.SAVE, DOCUMENT_EXTENSION);
    }

    public String showSaveHTMLDialog() {
        return showDialog("Export to HTML", FileDialog.SAVE, ".html");
    }

    public String showSaveLaTeXDialog() {
        return showDialog("Export to LaTeX", FileDialog.SAVE, ".tex");
    }

    protected abstract void setFilter(FileDialog dialog, String extension);

    protected abstract String getSeparator();
}
