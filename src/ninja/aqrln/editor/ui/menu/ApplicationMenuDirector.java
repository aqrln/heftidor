package ninja.aqrln.editor.ui.menu;

import ninja.aqrln.editor.ui.helpers.KeystrokeHelper;
import ninja.aqrln.editor.ui.platform.UIFactory;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

/**
 * @author Alexey Orlenko
 */
public abstract class ApplicationMenuDirector {
    protected ApplicationMenuListener listener;

    public ApplicationMenuDirector(ApplicationMenuListener listener) {
        this.listener = listener;
    }

    public JMenuBar buildApplicationMenu() {
        MenuBarBuilder builder = UIFactory.getInstance().createMenuBarBuilder();
        builder.buildMenuBar();

        builder.addSubMenu(buildFileMenu());
        builder.addSubMenu(buildEditMenu());

        buildAdditionalMenus(builder);

        return builder.getMenuBar();
    }

    protected abstract void buildAdditionalMenus(MenuBarBuilder builder);

    private JMenu buildFileMenu() {
        MenuBuilder builder = UIFactory.getInstance().createMenuBuilder();
        builder.buildMenu("File", KeyEvent.VK_F);

        builder.buildMenuItem("New", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_N), KeyEvent.VK_N,
                e -> listener.onFileNew());
        builder.buildMenuItem("Open...", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_O), KeyEvent.VK_O,
                e -> listener.onFileOpen());
        builder.buildMenuItem("Save", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_S), KeyEvent.VK_S,
                e -> listener.onFileSave());
        builder.buildMenuItem("Save as...", KeystrokeHelper.getShiftedMenuShortcut(KeyEvent.VK_S), KeyEvent.VK_A,
                e -> listener.onFileSaveAs());
        builder.buildMenuItem("Save all", KeystrokeHelper.getAlternatedMenuShortcut(KeyEvent.VK_S), KeyEvent.VK_L,
                e -> listener.onFileSaveAll());
        builder.buildMenuItem("Close", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_W), KeyEvent.VK_C,
                e -> listener.onFileClose());

        builder.buildSeparator();
        builder.addSubMenu(buildExportMenu());

        buildAdditionalFileMenuItems(builder);

        return builder.getMenu();
    }

    private JMenu buildExportMenu() {
        MenuBuilder builder = UIFactory.getInstance().createMenuBuilder();
        builder.buildMenu("Export to", KeyEvent.VK_E);

        builder.buildMenuItem("HTML...", null, KeyEvent.VK_H, e -> listener.onExportToHTML());
        builder.buildMenuItem("LaTeX...", null, KeyEvent.VK_L, e -> listener.onExportToLaTeX());

        return builder.getMenu();
    }

    protected abstract void buildAdditionalFileMenuItems(MenuBuilder builder);

    private JMenu buildEditMenu() {
        MenuBuilder builder = UIFactory.getInstance().createMenuBuilder();
        builder.buildMenu("Edit", KeyEvent.VK_E);

        builder.buildMenuItem("Undo", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_Z), KeyEvent.VK_U,
                e -> listener.onUndo());
        builder.buildMenuItem("Redo", KeystrokeHelper.getShiftedMenuShortcut(KeyEvent.VK_Z), KeyEvent.VK_R,
                e -> listener.onRedo());

        builder.buildSeparator();

        builder.buildMenuItem("Cut", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_X), KeyEvent.VK_T,
                e -> listener.onCut());
        builder.buildMenuItem("Copy", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_C), KeyEvent.VK_O,
                e -> listener.onCopy());
        builder.buildMenuItem("Paste", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_V), KeyEvent.VK_P,
                e -> listener.onPaste());

        builder.buildSeparator();

        builder.buildMenuItem("Toggle first line indent", KeystrokeHelper.getMenuShortcut(KeyEvent.VK_I), KeyEvent.VK_I,
                e -> listener.onToggleFirstLineIndent());

        return builder.getMenu();
    }
}
