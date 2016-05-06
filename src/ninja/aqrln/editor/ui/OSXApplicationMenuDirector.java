package ninja.aqrln.editor.ui;

import ninja.aqrln.editor.util.OSXExtensions;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class OSXApplicationMenuDirector extends ApplicationMenuDirector {
    public OSXApplicationMenuDirector(ApplicationMenuListener listener) {
        super(listener);
    }

    @Override
    public JMenuBar buildApplicationMenu() {
        OSXExtensions.setAboutHandler(() -> listener.onAbout());
        OSXExtensions.setQuitHandler(() -> listener.onQuit());

        return super.buildApplicationMenu();
    }

    @Override
    protected void buildAdditionalMenus(MenuBarBuilder builder) {
    }

    @Override
    protected void buildAdditionalFileMenuItems(MenuBuilder builder) {
    }
}
