package ninja.aqrln.editor.ui.dialogs;

import java.awt.FileDialog;

/**
 * FilePicker implementation for Windows
 * @author Alexey Orlenko
 */
public class WindowsFilePicker extends FilePicker {
    private static WindowsFilePicker instance;

    public static WindowsFilePicker getInstance() {
        if (instance == null) {
            instance = new WindowsFilePicker();
        }

        return instance;
    }

    private WindowsFilePicker() {
    }

    @Override
    protected void setFilter(FileDialog dialog, String extension) {
        dialog.setFile("*" + extension);
    }

    @Override
    protected String getSeparator() {
        return "\\";
    }
}
