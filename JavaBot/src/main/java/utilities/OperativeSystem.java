package utilities;

/**
 * Clase basada en el Sistema Operativo en uso - <code>String</code>
 * <br>
 * Recogerá dicho sistema operativo y lo pasará a minúsculas para trabajar con él.
 * @see http://lopica.sourceforge.net/os.html
 * 
 * @author LeCuay
 * @version 0.1
 */
public class OperativeSystem {
    private static final String OS = System.getProperty("os.name").toLowerCase();

    /**
     * Método para la comprobación de Sistemas Operativos Windows.
     */
    public static final boolean isWindows(){
        return OS.indexOf("win") >= 0;
    }

    /**
     * Método para la comprobación de Sistemas Operativos Mac OS.
     */
    public static final boolean isMacOS(){
        return OS.indexOf("mac") >= 0;
    }

    /**
     * Método para la comprobación de Sistemas Operativos Solaris.
     */
    public static final boolean isSolaris(){
        return OS.indexOf("sunos") >= 0 ||
        OS.indexOf("solaris") >= 0;
    }

    /**
     * Método para la comprobación de Sistemas Operativos Unix.
     */
    public static final boolean isUnix(){
        return OS.indexOf("nux") >= 0 ||
        OS.indexOf("nix") >= 0 ||
        OS.indexOf("aix") >= 0;
    }

    /**
     * Gets actual Operative System.
     * @return The Operative System.
     */
    public static String getOS(){return OS;}
}
