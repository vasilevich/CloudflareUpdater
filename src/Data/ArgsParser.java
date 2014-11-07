package Data;

import java.util.HashMap;

/**
 * Created by Yosef on 01/11/2014.
 */
public class ArgsParser {
    private HashMap<String, String> argsMap = new HashMap<String, String>();

    public ArgsParser(String[] args) {
        String key = "", value = "true";
        String[] argSplit;
        for (String arg : args) {
            if (arg.startsWith("-"))
                arg = arg.substring(1, arg.length());
            if (arg.contains("=")) {
                argSplit = arg.split("=");
                key = argSplit[0].trim();
                value = argSplit[1].trim();
            } else
                key = arg;
            argsMap.put(key, value);
        }
    }

    public String get(String key) {
        if (argsMap.containsKey(key))
            return argsMap.get(key);
        return null;
    }

    public boolean getBolean(String key) {
        if (argsMap.containsKey(key))
            return argsMap.get(key).equals("true") ? true : false;
        return false;
    }
}
