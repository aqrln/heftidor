package ninja.aqrln.editor.ui.menu;

import ninja.aqrln.editor.util.OSXExtensions;

import javax.swing.JMenuBar;

/**
 * @author Alexey Orlenko
 */
public class OSXApplicationMenuDirector extends ApplicationMenuDirector {
    public OSXApplicationMenuDirector(ApplicationMenuListener listener) {
        super(listener);
    }

    @Override
    public JMenuBar buildApplicationMenu() {
        OSXExtensions.getInstance().setAboutHandler(() -> listener.onAbout());
        OSXExtensions.getInstance().setQuitHandler(() -> listener.onQuit());

        return super.buildApplicationMenu();
    }

    @Override
    protected void buildAdditionalMenus(MenuBarBuilder builder) {
    }

    @Override
    protected void buildAdditionalFileMenuItems(MenuBuilder builder) {
    }
}
