package Data;


import Events.StartupManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

/**
 * Created by Yosef on 05/10/2014.
 */
public class Memory {

    public final String homepage = "http://duckip.tk";
    private DUCKIP_API duckip_api;
    private String Username = "Your email here";
    private String token = "Your token here";
    private boolean validated = false;
    private Timer updateInterval;
    private boolean update_duckip_api = true;
    private long cf_updateInterval = 5000l;
    private boolean autoStartup = false;
    private List<Domain> domains = new ArrayList<Domain>();
    private boolean changeEvent = true;
    private boolean cacheModified = true;
    private JSONObject jsondata = new JSONObject();
    private List<Record> refreshingRecords = new ArrayList<Record>();


    public Memory() {
        updateInterval = new Timer();
        updateInterval.schedule(new Api_updater(), 0l, 5000l);
        presetSettings();
    }

    public static void main(String[] args) throws IOException {
        Memory.getInstance().loadSettings();
        Cloudflare_API cf = new Cloudflare_API();


        for (Domain d : cf.getDomains()) {
            if (d.name.contains("yosef.tk")) {

                for (Record r : d.getRecords())
                    if (r.getName().contains("laptop")) {
                        //      System.out.println("memory  " + r.zone + "   " + r.getName() + " " + r.getValue() + "   " + r);
                        r.setValue("$autoip");
                        //                  r.delete();
                        break;
                        // r.delete();
                        //  r.setValue("$autoip");
                        //     // r.setValue("10.0.0.1");
                    }

            }
        }


    }

    public static Memory getInstance() {
        return SingletonHolder._instance;
    }

    public boolean isValidated() {
        if (validated == false)
            return validated = new Cloudflare_API().isValidUser();


        return validated;
    }

    public boolean isChangeEvent() {
        return changeEvent;
    }

    public void setChangeEvent(boolean ch) {
        changeEvent = ch;
    }

    public void addDomain(Domain d) {
        if (!containsDomain(d))
            domains.add(d);
    }

    public boolean containsDomain(Domain d) {
        for (Domain dm : domains)
            if (dm.name.contains(d.name))
                return true;
        return false;
    }

    public void removeDomain(Domain d) {
        domains.remove(d);
    }

    public List<Domain> getDomains() {
        return domains;
    }

    public Domain getDomain(String zone) {
        for (Domain d : domains) {
            if (d.name.equals(zone))
                return d;
        }
        return null;
    }

    public JSONObject retrieveCacheJsonData() {

        try {
            File cacheFile = new File("cache.json");


            if (cacheFile.exists()) {
                if (!cacheModified)
                    return jsondata;
                else {
                    Scanner s = new Scanner(cacheFile);
                    jsondata = new JSONObject(s.useDelimiter("\\A").next());
                    s.close();
                }
            } else
                cacheFile.createNewFile();

            cacheModified = false;
        } catch (Exception ignored) {

        }

        return jsondata;
    }

    public String retrieveCacheData(String params) {
        if (retrieveCacheJsonData().has(params))
            return retrieveCacheJsonData().get(params).toString();
        return "";
    }

    public void saveCacheData() {
        saveCacheData(jsondata);
    }

    public void saveCacheData(JSONObject jsondata) {
        try {

            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("cache.json"), "UTF-8"));
            cacheModified = true;
            out.write((jsondata).toString());

            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setCacheData(String params, String data) {
        JSONObject jsondata = retrieveCacheJsonData();
        if (data.isEmpty()) {
            if (jsondata.has(params))
                jsondata.remove(params);
        } else
            jsondata.put(params, data);
        saveCacheData(jsondata);
    }

    public void clearCache(String q) {
        for (String key : retrieveCacheJsonData().keySet())
            if (key.contains(q)) {
                setCacheData(key, "");
                clearCache(q);
                break;
            }
    }

    public void clearCache(Domain d) {
        for (String key : retrieveCacheJsonData().keySet())
            if (key.contains("z=" + d.name))
                setCacheData(key, "");
    }

    public void clearCache(Record r) {
        JSONObject cache = retrieveCacheJsonData();
        for (String key : cache.keySet())
            if (cache.get(key).toString().contains("\"rec_id\":\"" + r.getId() + "\""))
                setCacheData(key, "");
    }

    public void presetSettings() {
        JSONObject settings = retrieveCacheJsonData();
        if (!settings.has("autoUpdatingRecords"))
            settings.put("autoUpdatingRecords", new JSONArray());
        if (!settings.has("username"))
            settings.put("username", Username);
        if (!settings.has("token"))
            settings.put("token", token);
        if (!settings.has("update_duckip_api"))
            settings.put("update_duckip_api", update_duckip_api);

        saveCacheData(settings);


    }

    public void setSingleValue(String key, Object value) {
        JSONObject settings = retrieveCacheJsonData();
        settings.put(key, value);
        saveCacheData(settings);
    }

    public void setSingleValue(boolean value) {
        JSONObject settings = retrieveCacheJsonData();
        settings.put("autoStartup", value);
        saveCacheData(settings);
    }

    public boolean isAutoStartup() {
        return autoStartup;
    }

    public void setAutoStartup(boolean autoStartup) {
        setSingleValue(this.autoStartup = autoStartup);
    }

    public List<Record> getRefreshingRecords() {
        return refreshingRecords;
    }

    public void loadSettings() {

        JSONObject settings = retrieveCacheJsonData();

        final JSONArray records = settings.getJSONArray("autoUpdatingRecords");
        if (settings.has("username"))
            Username = settings.get("username").toString();
        if (settings.has("token"))
            token = settings.get("token").toString();
        if (settings.has("update_duckip_api"))
            update_duckip_api = settings.getBoolean("update_duckip_api");

        if (settings.has("autoStartup"))
            autoStartup = settings.getBoolean("autoStartup");

        if (autoStartup)
            StartupManager.enableStartup();
        else
            StartupManager.disableStartup();


        if (settings.has("autoUpdatingRecords")) {

            Cloudflare_API cf = new Cloudflare_API();
            cf.getDomains();
            boolean rflag;
            JSONObject jsonr;
            for (int loop = 0, length = records.length(); loop < length; loop++) {
                jsonr = ((JSONObject) records.get(loop));
                rflag = true;
                for (Record r : cf.getRecords(((JSONObject) records.get(loop)).getString("zone"))) {
                    if (r.getName().contains(jsonr.getString("name"))) {
                        rflag = false;
                        r.setIPUpdater(jsonr.getLong("interval"));
                        break;
                    }
                }
                if (rflag)
                    if (jsonr.has("name"))
                        removeAutoUpdatingRecord(jsonr);

            }


        }

    }

    public void saveSettings() {
        JSONObject settings = retrieveCacheJsonData();
        settings.put("username", Username);
        settings.put("token", token);
        settings.put("update_duckip_api", update_duckip_api);
        settings.put("autoStartup", autoStartup);
        saveCacheData(settings);
    }

    public long getUpdateInterval() {

        return cf_updateInterval;
    }

    public void addAutoUpdatingRecord(Record r) {

        JSONObject settings = retrieveCacheJsonData();


        JSONArray records = settings.getJSONArray("autoUpdatingRecords");
        JSONObject record;
        boolean exsist = false;
        if ((record = retrieveAutoUpdatingRecord(r)) != null)
            exsist = true;
        else
            record = new JSONObject();

        record.put("id", r.getId());
        record.put("zone", r.zone);
        record.put("name", r.getName());
        record.put("interval", r.getlastScheduledTime());

        if (!exsist) {
            records.put(record);

        }
        refreshingRecords.remove(r);
        refreshingRecords.add(r);
        settings.put("autoUpdatingRecords", records);
        saveCacheData(settings);

        //System.out.println(r);


    }

    public void removeAutoUpdatingRecord(Record r) {

        JSONObject settings = retrieveCacheJsonData();
        JSONArray records = settings.getJSONArray("autoUpdatingRecords");
        for (int loop = 0, length = records.length(); loop < length; loop++) {

            if (records.getJSONObject(loop).getString("name").equals(r.getName()) && records.getJSONObject(loop).getString("zone").equals(r.zone)) {
                //  System.out.println(records.getJSONObject(loop).getString("name"));
                //  System.out.println(records.getJSONObject(loop).getString("zone"));
                records.remove(loop);
                break;
                //  loop = 0;
                //  length = records.length();
            }
        }

        saveCacheData(settings);
        refreshingRecords.remove(r);

    }

    public JSONObject retrieveAutoUpdatingRecord(Record r) {
        JSONArray records = retrieveCacheJsonData().getJSONArray("autoUpdatingRecords");
        for (int loop = 0, length = records.length(); loop < length; loop++) {
            if (records.getJSONObject(loop).getString("name").equals(r.getName()) && records.getJSONObject(loop).getString("zone").equals(r.zone)) {
                return records.getJSONObject(loop);
            }
        }
        return null;
    }

    public void removeAutoUpdatingRecord(JSONObject jsonr) {
        Record r = new Record(jsonr);
        removeAutoUpdatingRecord(r);
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
        validated = new Cloudflare_API().isValidUser();

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        validated = new Cloudflare_API().isValidUser();
    }


    public DUCKIP_API getDuckip_api() {
        if (duckip_api != null)
            return duckip_api;
        else

            try {
                return duckip_api = new DUCKIP_API();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
    }

    public void setDuckip_api(DUCKIP_API duckip_api) {
        this.duckip_api = duckip_api;
    }

    public boolean isUpdate_duckip_api() {
        return update_duckip_api;
    }

    public void setUpdate_duckip_api(boolean update_duckip_api) {
        this.update_duckip_api = update_duckip_api;
    }

    private static class SingletonHolder {
        protected static final Memory _instance = new Memory();
    }

    private class Api_updater extends TimerTask {

        public void run() {
            try {

                if (update_duckip_api)
                    duckip_api = new DUCKIP_API();
            } catch (IOException ignored) {
            }
        }
    }


}
