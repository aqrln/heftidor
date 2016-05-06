package ninja.aqrln.editor.core;

import ninja.aqrln.editor.ui.ApplicationMenuListener;
import ninja.aqrln.editor.ui.UIFactory;
import ninja.aqrln.editor.util.OSXExtensions;
import ninja.aqrln.editor.util.OperatingSystem;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class Application implements ApplicationMenuListener {
    private static Application instance;

    public static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }

        return instance;
    }

    private JMenuBar applicationMenu;

    private Application() {
        applicationMenu = UIFactory.getInstance().createApplicationMenuDirector(this).buildApplicationMenu();
    }

    public void run() {
        if (OperatingSystem.getOS() == OperatingSystem.OS_X) {
            OSXExtensions.setMenuBar(applicationMenu);
        }
    }

    @Override
    public void onFileNew() {

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
        System.out.println("about");
    }
}
