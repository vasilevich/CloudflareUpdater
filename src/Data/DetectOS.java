package Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.prefs.Preferences;

/**
 * Created by Y_OSef on 11/10/2014.
 */
public class DetectOS {

    public static String SF_ALLUSERSDESKTOP = "AllUsersDesktop";
    public static String SF_ALLUSERSSTARTMENU = "AllUsersStartMenu";
    public static String SF_ALLUSERSPROGRAMS = "AllUsersPrograms";
    public static String SF_ALLUSERSSTARTUP = "AllUsersStartup";
    public static String SF_DESKTOP = "Desktop";
    public static String SF_FAVORITES = "Favorites";
    public static String SF_MYDOCUMENT = "MyDocuments";
    public static String SF_PROGRAMS = "Programs";
    public static String SF_RECENT = "Recent";
    public static String SF_SENDTO = "SendTo";
    public static String SF_STARTMENU = "StartMenu";
    public static String SF_STARTUP = "Startup";
    private static String _OS = System.getProperty("os.name").toLowerCase();
    public final String OS;


    public DetectOS() {

        if (isWindows())
            OS = "Windows";
        else if (isSolaris())
            OS = "Solaris";
        else if (isMac())
            OS = "IOS";
        else if (isLinux())
            OS = "Linux";
        else if (isUnix())
            OS = "Unix";
        else
            OS = "OS";


    }

    public static String getSpecialFolder(String folder) {
        String result = "";
        if (isWindows()) {
            try {
                File file = File.createTempFile("pathChecker", ".vbs");
                file.deleteOnExit();
                FileWriter fw = new java.io.FileWriter(file);

                String vbs = "Set WshShell = WScript.CreateObject(\"WScript.Shell\")\n"
                        + "wscript.echo WshShell.SpecialFolders(\"" + folder + "\")\n"
                        + "Set WSHShell = Nothing\n";

                fw.write(vbs);
                fw.close();
                Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
                BufferedReader input =
                        new BufferedReader
                                (new InputStreamReader(p.getInputStream()));
                result = input.readLine();
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (isLinux()) {
            //a linux solution


        }
        return result;
    }

    public static String getExecutionPath(String... args) {

        String path = args[0];
        String type = "";
        if (args.length > 1)
            type = args[1];
        else if (path.contains(".exe"))
            type = "executeable";
        else if (path.contains(".jar") || path.contains("-jar"))
            type = "java";
        else
            type = path.split(".")[path.split(".").length - 1];


            if(type.equals("type")) {
                path = path.replace("javaw -jar", "");
                path = path.replace("java -jar", "");
                return "javaw -jar " + path;
            }
        if(type.equals("executeable"))
                return path;



        return path;
    }

    public static String getCurrentlyRunningScript() {
        return new File(DetectOS.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getAbsoluteFile().toString().replaceAll("%20", " ");
    }

    public static String getCurrentlyRunningScriptDirectory() {
        return new File(getCurrentlyRunningScript()).getParentFile().toString();
    }

    public static boolean isWindows() {

        return (_OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (_OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (_OS.indexOf("nix") >= 0 || _OS.indexOf("aix") > 0);

    }

    public static boolean isLinux() {

        return (_OS.indexOf("nux") >= 0);

    }


    public static boolean isSolaris() {

        return (_OS.indexOf("sun_OS") >= 0);

    }


    public static boolean isAdmin1() {
        String groups[] = (new com.sun.security.auth.module.NTSystem()).getGroupIDs();
        for (String group : groups) {
            if (group.equals("S-1-5-32-544"))
                return true;
        }
        return false;
    }


    public static boolean isAdmin() {
        Preferences prefs = Preferences.systemRoot();
        try {
            prefs.put("foo", "bar"); // SecurityException on Windows
            prefs.remove("foo");
            prefs.flush(); // BackingStoreException on Linux
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
