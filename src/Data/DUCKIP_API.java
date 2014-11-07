package Data;

import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Yosef on 04/10/2014.
 */
public class DUCKIP_API {
    public final String url = "http://duckip.info/data";
    public final String IP;
    public final String ISP;
    public final String country;
    public final String ct;
    public final String timestamp;

    public DUCKIP_API() throws IOException {
        Scanner s = new Scanner(new URL(url).openStream(), "UTF-8");
        JSONArray JSON = new JSONArray(s.useDelimiter("\\A").next());
        s.close();
        IP = JSON.getString(0);
        ISP = JSON.getString(1);
        country = JSON.getString(2);
        ct = JSON.getString(3);
        timestamp = JSON.getString(4);
    }
}

