package Data;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Yosef on 04/10/2014.
 */
public class Record {
    public final String zone;
    private String name;
    private String value;
    private String type;
    private String id;
    private String ttl;
    private boolean service;
    private boolean IPUpdater;
    private Timer updateInterval = new java.util.Timer();
    private Api_updater updatingTask;
    private Record r = this;
    private String lastIP = "";
    private long lastRefresh = System.currentTimeMillis() / 1000L;
    private long lastScheduledTime = Memory.getInstance().getUpdateInterval();


    public Record(JSONObject record) {

        zone = (String) record.getfb("zone_name");
        name = (String) record.getfb("display_name");
        value = (String) record.getfb("display_content");
        type = (String) record.getfb("type");
        id = (String) record.getfb("rec_id");
        ttl = (String) record.getfb("ttl");
        service = (String) record.getfb("service_mode") == "0" ? false : true;

    }

    public Record(String z) {


        zone = z;
        name = "Example name";
        value = "Example value";
        type = "CNAME";
        id = "54354353453464743743734324";
        ttl = "1";
        service = false;

    }

    public void update() {
        Memory.getInstance().getDomain(zone).addRecord(this);
        new Cloudflare_API().editRecord(this);
        Memory.getInstance().setChangeEvent(true);


    }

    public void delete() {

        setIPUpdater(false);
        new Cloudflare_API().deleteRecord(this);
        Memory.getInstance().removeAutoUpdatingRecord(this);
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        if ((this.value = value).toLowerCase().replace(" ", "").equals("$autoip"))
            setIPUpdater(true);

        else
            setIPUpdater(false);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isService() {
        return service;
    }

    public void setService(boolean service) {
        this.service = service;
    }

    public boolean isIPUpdater() {
        return IPUpdater;
    }

    public void setIPUpdater(boolean IPUpdater) {

        if ((this.IPUpdater = IPUpdater) == true) {
            //  System.out.println(zone + "   " + getName() + " " + getValue() + "   " + this);
            Memory.getInstance().addAutoUpdatingRecord(this);
            if (updatingTask == null)
                updateInterval.schedule(updatingTask = new Api_updater(), 0l, lastScheduledTime);
        } else if (updateInterval != null) {
            Memory.getInstance().removeAutoUpdatingRecord(this);
            if (updatingTask != null) {
                updatingTask.cancel();
                updatingTask = null;
            }
            this.IPUpdater = false;
        }

    }

    public void setIPUpdater(long interval) {
        if ((lastScheduledTime = interval) > 0) {

            setIPUpdater(true);
        } else {
            setIPUpdater(false);
        }
    }

    public long getLastRefresh() {
        return lastRefresh;
    }

    public long getTimeLeft() {
        return (System.currentTimeMillis() / 1000L) - lastRefresh;
    }

    public long getlastScheduledTime() {
        return lastScheduledTime;
    }

    public long getlastScheduledTimeSeconds() {
        return lastScheduledTime / 1000;
    }

    public void setlastScheduledTimeSeconds(long lastScheduledTime) {
        setlastScheduledTime(lastScheduledTime * 1000);
    }

    public void setlastScheduledTime(long lastScheduledTime) {
        setIPUpdater(false);
        this.lastScheduledTime = lastScheduledTime;
        setIPUpdater(true);
    }

    private class Api_updater extends TimerTask {

        public void run() {
            lastRefresh = System.currentTimeMillis() / 1000L;
            r.value = Memory.getInstance().getDuckip_api().IP;

            if (IPUpdater && !Memory.getInstance().getDuckip_api().IP.equals(lastIP)) {
                update();
                IPUpdater = true;
            } else if (!IPUpdater)
                updateInterval.cancel();
            lastIP = Memory.getInstance().getDuckip_api().IP;
            //    System.out.println(r.isIPUpdater()+"  "+r.zone + "   " + r.getName() + " " + r.getValue() + "   " + r);


        }
    }
}
