package Events;

import Data.DetectOS;
import Data.Memory;

import java.io.*;
import java.text.Normalizer;

/**
 * Created by Yosef on 01/11/2014.
 */
public class StartupManager extends StartNoStart {

    private static boolean Startup = false;
    private static String startfolder = fixPathLanguage(DetectOS.getSpecialFolder(DetectOS.SF_STARTUP) + "\\");
    private static boolean startupInstalled = false;

    public StartupManager() {

    }

    public StartupManager(boolean state) {


    }

    private static void setStartup(boolean s) {
        Startup = s;
        if (s)
            enableStartup();
        else
            disableStartup();

    }

    private static String fixPathLanguage(String path) {
        String correctPath = System.getProperty("user.home");
        path = correctPath + path.substring(path.indexOf("\\", correctPath.lastIndexOf("\\") + 1));
        return path;
    }

    private static void setStartupScript(String filename, String content) {
        try {
            if (!(filename.contains(".bat") || filename.contains(".cmd")))
                filename += ".bat";
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(startfolder + filename)));
            out.write(content);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addStartupProgram(String name, String path) {
        setStartupScript(name, path);
    }

    private static String normalizeUnicode(String str) {
        Normalizer.Form form = Normalizer.Form.NFD;
        if (!Normalizer.isNormalized(str, form)) {
            return Normalizer.normalize(str, form);
        }
        return str;
    }

    private static void removeStartupProgram(String name) {

        File folder = new File(startfolder);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().contains(name))
                listOfFiles[i].delete();
        }
    }

    public static boolean isStartupEnabled(String name) {

        File folder = new File(startfolder);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().contains(name)) {
                return startupInstalled = true;
            }
        }
        return false;
    }

    public static void updateStartup() {
        if (isStartupEnabled("CFUpdater")) {
            disableStartup();
            enableStartup();
        }
    }


    public static void enableStartup() {

        //addStartupProgram("CFUpdater","javaw -jar CloudflareUpdater.jar minimized");
        Memory.getInstance().setAutoStartup(true);
        //addStartupProgram("CFUpdater", "cd \\d \""+DetectOS.getCurrentlyRunningScriptDirectory()+"\" & javaw -jar \"" + DetectOS.getCurrentlyRunningScript() + "\" minimized");


        try {
            File file = File.createTempFile("shortcutCreator", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            //    System.out.println(DetectOS.getCurrentlyRunningScriptDirectory());
            String vbs = " Set oWS = WScript.CreateObject(\"WScript.Shell\")\n" +
                    "   sLinkFile = \"" + startfolder + "CFUpdater.LNK\"\n" +
                    "   Set oLink = oWS.CreateShortcut(sLinkFile)\n" +
                    "   \n" +
                    "   oLink.TargetPath = \"" + DetectOS.getCurrentlyRunningScript() + "\"\n" +
                    "   oLink.Arguments = \"minimized\"\n" +
                    "   oLink.Description = \"CloudFlare IP Updater\"\n" +
                    "   oLink.HotKey = \"ALT+CTRL+C\"\n" +
                    "   oLink.IconLocation = \"" + DetectOS.getCurrentlyRunningScript() + ", 0\"\n" +
                    "   oLink.WindowStyle = \"1\"\n" +
                    "   oLink.WorkingDirectory = \"" + DetectOS.getCurrentlyRunningScriptDirectory() + "\\\"\n" +
                    "   oLink.Save";

            //      System.out.println(file.getAbsolutePath());
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input =
                    new BufferedReader
                            (new InputStreamReader(p.getInputStream()));
            String result = input.readLine();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void disableStartup() {
        Memory.getInstance().setAutoStartup(false);
        removeStartupProgram("CFUpdater");

    }

    public static void main(String[] args) throws Exception {
        StartupManager.enableStartup();
        StartupManager.disableStartup();

    }

    @Override
    protected void perform(boolean state) {
        if (state)
            enableStartup();
        else
            disableStartup();


    }


}
