package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.util.OSXExtensions;
import ninja.aqrln.editor.util.OperatingSystem;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class ApplicationUI implements ApplicationMenuListener {
    private static ApplicationUI instance;

    public static ApplicationUI getInstance() {
        if (instance == null) {
            instance = new ApplicationUI();
        }

        return instance;
    }

    private JMenuBar applicationMenu;

    private ApplicationUI() {
        applicationMenu = UIFactory.getInstance().createApplicationMenuDirector(this).buildApplicationMenu();
    }

    public void run() {
        if (OperatingSystem.getOS() == OperatingSystem.OS_X) {
            OSXExtensions.getInstance().setMenuBar(applicationMenu);
        }

        onFileNew();
    }

    public JMenuBar getApplicationMenu() {
        return applicationMenu;
    }

    @Override
    public void onFileNew() {
        DocumentWindow window = new DocumentWindow();
        window.setVisible(true);
    }

    @Override
    public void onFileOpen() {

    }

    @Override
    public void onFileSave() {

    }

    @Override
    public void onFileSaveAs() {

    }

    @Override
    public void onFileSaveAll() {

    }

    @Override
    public void onFileClose() {

    }

    @Override
    public void onQuit() {
        System.out.println("quit");
        System.exit(0);
    }

    @Override
    public void onExportToHTML() {

    }

    @Override
    public void onExportToLaTeX() {

    }

    @Override
    public void onUndo() {

    }

    @Override
    public void onRedo() {

    }

    @Override
    public void onCut() {

    }

    @Override
    public void onCopy() {

    }

    @Override
    public void onPaste() {

    }

    @Override
    public void onAbout() {
        JDialog about = new AboutDialog();
        about.setVisible(true);
    }
}
