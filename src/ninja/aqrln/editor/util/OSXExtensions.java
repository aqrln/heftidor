package ninja.aqrln.editor.util;

import javax.swing.*;

/**
 * @author Alexey Orlenko
 */
public class OSXExtensions {
    public static void setMenuBar(JMenuBar menu) {
        com.apple.eawt.Application.getApplication().setDefaultMenuBar(menu);
    }

    public static void setAboutHandler(Runnable handler) {
        com.apple.eawt.Application.getApplication().setAboutHandler(e -> handler.run());
    }

    public static void setQuitHandler(Runnable handler) {
        com.apple.eawt.Application.getApplication().setQuitHandler((quitEvent, quitResponse) -> {
            handler.run();
            quitResponse.cancelQuit();
        });
    }
}
