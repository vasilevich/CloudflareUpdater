package Data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yosef on 04/10/2014.
 */
public class Cloudflare_API {

    private final String email = Memory.getInstance().getUsername();
    private final String token = Memory.getInstance().getToken();
    private URL url = URL("https://www.cloudflare.com/api_json.html");

    public static void main(String[] args) {

        Cloudflare_API cf = new Cloudflare_API();
        Memory.getInstance().clearCache(cf.getDomains().get(0));
        //
        // List<Domain> domains = cf.getDomains();

        // Record r = cf.getRecords("yosef.tk").get(1);
        // r.setType("CNAME");
        // r.setValue("google.com");
        // r.setService(false);
        //  cf.editRecord(r);
        //System.out.print(r.getName());

    }

    private URL URL(String url) {

        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String paramsToPost(Map<String, String> params) {


        try {
            StringBuilder postData = new StringBuilder();
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (postData.length() != 0) postData.append('&');
                postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                postData.append('=');
                postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            return postData.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String fetchData(String... action) {
        Map<String, String> params = new LinkedHashMap<String, String>();
        if (action.length > 0)
            params.put("a", action[0]);
        if (action.length > 1)
            params.put("z", action[1]);
        return fetchData(params);
    }

    private String fetchData(Map<String, String> params) {

        params.put("email", email);
        params.put("tkn", token);
        String postData = paramsToPost(params);
        String output = Memory.getInstance().retrieveCacheData(postData);
        if (output.isEmpty())
            Memory.getInstance().setCacheData(postData, output = postData(postData));
        //System.out.println(token);
        return output;
    }

    private String postData(Map<String, String> params) {
        params.put("email", email);
        params.put("tkn", token);
        return postData(paramsToPost(params));
    }

    private String postData(String postData) {
        String output = "";
        try {
            byte[] postDataBytes = postData.getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);

            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            for (int c; (c = in.read()) >= 0; output += ((char) c)) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public boolean isValidUser() {
        return isValidUser(fetchData("zone_load_multi"));
    }

    public boolean isValidUser(String data) {
        try {
            if (new JSONObject(data).getString("msg").toLowerCase().contains("invalid"))
                return false;
        } catch (Exception e) {

        }
        return true;

    }

    public List<Domain> getDomains() {
        try {
            JSONArray domainList = new JSONObject(fetchData("zone_load_multi")).getJSONObject("response").getJSONObject("zones").getJSONArray("objs");

            for (int loop = 0, length = domainList.length(); loop < length; loop++)
                Memory.getInstance().addDomain(new Domain((JSONObject) domainList.get(loop)));
        } catch (Exception e) {
        }
        ;
        return Memory.getInstance().getDomains();
    }

    public List<Record> getRecords(String zone) {
        return getRecords(Memory.getInstance().getDomain(zone));
    }

    public List<Record> getRecords(Domain d) {

        JSONObject response = new JSONObject(fetchData("rec_load_all", d.name)).getJSONObject("response").getJSONObject("recs");
        if (response.has("obj"))
            return d.getRecords();
        JSONArray recordList = response.getJSONArray("objs");
        for (int loop = 0, length = recordList.length(); loop < length; loop++)
            d.addRecord(new Record(recordList.getJSONObject(loop)));
        return d.getRecords();
    }

    public void editRecord(Record r) {
        deleteRecord(r, true);
        newRecord(r);

    }

    public void newRecord(Record r) {

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("a", "rec_new");
        params.put("z", r.zone);
        params.put("id", r.getId());
        params.put("ttl", r.getTtl());
        params.put("type", r.getType());
        params.put("name", r.getName());
        params.put("content", r.getValue());
        params.put("service_mode", r.isService() ? "1" : "0");
        Memory.getInstance().clearCache(r);

        try {
            String d = fetchData(params);
            //    System.out.println("Sent to cf:"+r.zone+" "+r.getName() + " "+r.getValue());

            r.setId(new JSONObject(d).getJSONObject("response").getJSONObject("rec").getJSONObject("obj").getString("rec_id"));


            // Memory.getInstance().getDomain(r.zone).addRecord(new Record(new JSONObject(d).getJSONObject("response").getJSONObject("rec").getJSONObject("obj")));
        } catch (Exception e) {
            //    System.out.print("Error!");
            Memory.getInstance().clearCache(r.zone);
        }

    }

    public void deleteRecord(Record r) {
        deleteRecord(r, false);
    }

    public void deleteRecord(Record r, boolean onlycache) {

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("a", "rec_delete");
        params.put("z", r.zone);
        params.put("id", r.getId());
        postData(params);
        if (!onlycache)
            Memory.getInstance().getDomain(r.zone).removeRecord(r);
        Memory.getInstance().clearCache(r.zone);

    }
}
