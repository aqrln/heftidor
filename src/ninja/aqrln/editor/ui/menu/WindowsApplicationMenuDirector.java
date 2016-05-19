package ninja.aqrln.editor.ui.menu;

import ninja.aqrln.editor.ui.KeystrokeHelper;
import ninja.aqrln.editor.ui.platform.UIFactory;

import javax.swing.JMenu;
import java.awt.event.KeyEvent;

/**
 * @author Alexey Orlenko
 */
public class WindowsApplicationMenuDirector extends ApplicationMenuDirector {
    public WindowsApplicationMenuDirector(ApplicationMenuListener listener) {
        super(listener);
    }

    @Override
    protected void buildAdditionalMenus(MenuBarBuilder builder) {
        builder.addSubMenu(buildHelpMenu());
    }

    private JMenu buildHelpMenu() {
        MenuBuilder builder = UIFactory.getInstance().createMenuBuilder();
        builder.buildMenu("Help", KeyEvent.VK_H);
        builder.buildMenuItem("About...", null, KeyEvent.VK_A, e -> listener.onAbout());

        return builder.getMenu();
    }

    @Override
    protected void buildAdditionalFileMenuItems(MenuBuilder builder) {
        builder.buildSeparator();
        builder.buildMenuItem("Quit", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_Q), KeyEvent.VK_Q,
                e -> listener.onQuit());
    }
}
