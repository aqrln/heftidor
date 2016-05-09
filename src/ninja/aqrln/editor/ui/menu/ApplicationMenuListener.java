package ninja.aqrln.editor.ui.menu;

/**
 * @author Alexey Orlenko
 */
public interface ApplicationMenuListener {
    void onFileNew();
    void onFileOpen();
    void onFileSave();
    void onFileSaveAs();
    void onFileSaveAll();
    void onFileClose();

    void onQuit();

    void onExportToHTML();
    void onExportToLaTeX();

    void onUndo();
    void onRedo();
    void onCut();
    void onCopy();
    void onPaste();

    void onAbout();
}
