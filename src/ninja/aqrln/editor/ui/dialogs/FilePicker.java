package ninja.aqrln.editor.ui.dialogs;

import java.awt.FileDialog;
import java.awt.Frame;

/**
 * @author Alexey Orlenko
 */
public abstract class FilePicker {
    public static final String EXTENSION = ".editordoc";

    private String lastDirectory = System.getProperty("user.home");

    private String showDialog(String title, int type) {
        FileDialog dialog = new FileDialog((Frame) null, title, type);

        dialog.setDirectory(lastDirectory);
        setFilter(dialog);
        dialog.setVisible(true);

        String directory = dialog.getDirectory();
        String filename = dialog.getFile();

        if (filename == null) {
            return null;
        }

        if (!filename.endsWith(EXTENSION)) {
            filename += EXTENSION;
        }

        String separator = getSeparator();
        if (!directory.endsWith(separator)) {
            directory += separator;
        }

        lastDirectory = directory;

        return directory + filename;
    }

    public String showLoadDialog() {
        return showDialog("Open file", FileDialog.LOAD);
    }

    public String showSaveDialog() {
        return showDialog("Save file", FileDialog.SAVE);
    }

    protected abstract void setFilter(FileDialog dialog);

    protected abstract String getSeparator();
}
