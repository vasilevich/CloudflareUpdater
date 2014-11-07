package Windows;

import Data.Record;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Yosef on 05/10/2014.
 */
public class RecordDeleter extends JPanel {
    private final Record r;


    public RecordDeleter(Record inr) {
        r = inr;

        setLayout(new GridLayout(6, 1));

        add(new JLabel("Are you sure you wish to delete"));
        add(new JLabel("Record:"));
        add(new JLabel("Zone: " + r.zone));
        add(new JLabel("Name: " + r.getName()));
        add(new JLabel("Type: " + r.getType()));
        add(new JLabel("Value: " + r.getValue()));

        int result = JOptionPane.showConfirmDialog(null, this,
                "Delete record for zone:  " + r.zone, JOptionPane.YES_NO_OPTION);
        //     System.out.println(r.isIPUpdater());
        if (result == JOptionPane.YES_OPTION)
            r.delete();


    }


    public Record getRecord() {
        return r;
    }


}
