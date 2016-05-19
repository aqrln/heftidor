package ninja.aqrln.editor.ui.dialogs;

import java.awt.FileDialog;

/**
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
    protected void setFilter(FileDialog dialog) {
        dialog.setFile("*" + EXTENSION);
    }

    @Override
    protected String getSeparator() {
        return "\\";
    }
}
