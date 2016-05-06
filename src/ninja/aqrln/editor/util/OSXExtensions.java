package ninja.aqrln.editor.util;

import javax.swing.*;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Alexey Orlenko
 */
public class OSXExtensions {
    private static OSXExtensions instance;

    public static OSXExtensions getInstance() {
        if (instance == null && OperatingSystem.getOS() == OperatingSystem.OS_X) {
            instance = new OSXExtensions();
        }

        return instance;
    }

    private Object application;
    private Method setDefaultMenuBarMethod;

    private Class aboutHandlerClass;
    private Method setAboutHandlerMethod;

    private Class quitHandlerClass;
    private Method cancelQuitMethod;
    private Method setQuitHandlerMethod;

    private OSXExtensions() {
        try {
            Class applicationClass = Class.forName("com.apple.eawt.Application");
            application = applicationClass.getMethod("getApplication").invoke(null);

            setDefaultMenuBarMethod = applicationClass.getMethod("setDefaultMenuBar", JMenuBar.class);

            aboutHandlerClass = Class.forName("com.apple.eawt.AboutHandler");
            setAboutHandlerMethod = applicationClass.getMethod("setAboutHandler", aboutHandlerClass);

            quitHandlerClass = Class.forName("com.apple.eawt.QuitHandler");
            Class quitResponseClass = Class.forName("com.apple.eawt.QuitResponse");
            cancelQuitMethod = quitResponseClass.getMethod("cancelQuit");
            setQuitHandlerMethod = applicationClass.getMethod("setQuitHandler", quitHandlerClass);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setMenuBar(JMenuBar menu) {
        try {
            setDefaultMenuBarMethod.invoke(application, menu);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setAboutHandler(Runnable handler) {
        Object aboutHandler = Proxy.newProxyInstance(
                aboutHandlerClass.getClassLoader(),
                new Class[] { aboutHandlerClass },
                (proxy, method, args) -> {
                    if (method.getName().equals("handleAbout")) {
                        handler.run();
                    }
                    return null;
                });

        try {
            setAboutHandlerMethod.invoke(application, aboutHandler);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setQuitHandler(Runnable handler) {
        Object quitHandler = Proxy.newProxyInstance(
                quitHandlerClass.getClassLoader(),
                new Class[] { quitHandlerClass },
                (proxy, method, args) -> {
                    if (method.getName().equals("handleQuitRequestWith")) {
                        handler.run();
                        cancelQuitMethod.invoke(args[1]);
                    }
                    return null;
                });

        try {
            setQuitHandlerMethod.invoke(application, quitHandler);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
