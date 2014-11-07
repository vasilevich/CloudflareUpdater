package Data;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yosef on 04/10/2014.
 */
public class Domain {
    public final String name;
    public String id;
    private Cloudflare_API cf = new Cloudflare_API();
    private List<Record> records = new ArrayList<Record>();

    public Domain(String name) {
        this.name = name;
        records = cf.getRecords(name);
    }

    public Domain(JSONObject zone) {
        name = zone.getString("display_name");

        id = zone.getString("zone_id");
        records = cf.getRecords(this);
    }

    public List<Record> getRecords() {
        return records;
    }

    public void addRecord(Record record) {
        Record r;
        if ((r = getRecord(record)) != null) {
            r.setId(record.getId());
            r.setName(record.getName());
            //  if(!r.isIPUpdater())
            //     r.setValue(record.getValue());
            //  r.setlastScheduledTimeSeconds(record.getlastScheduledTime());
            // r.setIPUpdater(record.isIPUpdater());
            r.setService(record.isService());
            r.setTtl(record.getTtl());
            r.setType(record.getType());
        } else
            records.add(record);
    }


    public Record getRecord(Record r) {
        for (Record rec : records)
            if (rec.getName().contains(r.getName()))
                return rec;
        return null;
    }

    public void removeRecord(Record r) {
        for (Record rec : records)
            if (rec.getName().contains(r.getName())) {
                records.remove(rec);
                break;
            }

    }

}
