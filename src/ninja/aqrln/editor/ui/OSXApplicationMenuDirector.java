package ninja.aqrln.editor.ui;

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
        com.apple.eawt.Application.getApplication().setAboutHandler(e -> listener.onAbout());
        com.apple.eawt.Application.getApplication().setQuitHandler((quitEvent, quitResponse) -> {
            listener.onQuit();
            quitResponse.cancelQuit();
        });
        
        return super.buildApplicationMenu();
    }

    @Override
    protected void buildAdditionalMenus(MenuBarBuilder builder) {
    }

    @Override
    protected void buildAdditionalFileMenuItems(MenuBuilder builder) {
    }
}
