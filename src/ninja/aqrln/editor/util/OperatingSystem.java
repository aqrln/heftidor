package ninja.aqrln.editor.util;

/**
 * @author Alexey Orlenko
 */
public enum OperatingSystem {

    OS_X, WINDOWS, LINUX, UNKNOWN;

    private static OperatingSystem os = UNKNOWN;

    public static OperatingSystem getOS() {
        if (os != UNKNOWN) {
            return os;
        }

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            os = OS_X;
        } else if (osName.contains("windows")) {
            os = WINDOWS;
        } else if (osName.contains("linux")) {
            os = LINUX;
        }

        return os;
    }

}
