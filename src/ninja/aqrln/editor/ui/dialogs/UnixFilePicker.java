package ninja.aqrln.editor.ui.dialogs;

import java.awt.FileDialog;

/**
 * @author Alexey Orlenko
 */
public class UnixFilePicker extends FilePicker {
    private static UnixFilePicker instance;

    public static UnixFilePicker getInstance() {
        if (instance == null) {
            instance = new UnixFilePicker();
        }

        return instance;
    }

    private UnixFilePicker() {
    }

    @Override
    protected void setFilter(FileDialog dialog, String extension) {
        dialog.setFilenameFilter(((dir, name) -> name.endsWith(extension)));
    }

    @Override
    protected String getSeparator() {
        return "/";
    }
}
