package Windows;


import Data.Memory;
import Data.Record;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;


/**
 * Created by Yosef on 11/10/2014.
 */
public class StatusViewer extends Main {

    private java.util.Timer updateInterval = new java.util.Timer();
    private List<StatusLine> statusLines = new ArrayList<StatusLine>();
    private JLabel s1 = new JLabel("No records are currently auto updating"), s2 = new JLabel("Please click Help to find out more.");

    public StatusViewer() {

        super(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Records Status");
        setSize(0, 0);
        Memory.getInstance().setChangeEvent(true);
        refreshProgressList();
        setLayout(new GridLayout(Memory.getInstance().getRefreshingRecords().size(), 1));


        pack();
        updateInterval.schedule(new StatusUpdater(), 0l, 1000L);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                updateInterval.cancel();
            }
        });

    }

    public static void main(String[] args) {

        new StatusViewer();

    }

    private void refreshProgressList() {

        if (Memory.getInstance().isChangeEvent()) {
            System.out.println(Memory.getInstance().getRefreshingRecords());
            Memory.getInstance().setChangeEvent(false);
            remove(s1);
            remove(s2);
            for (StatusLine line : statusLines)
                remove(line.getPanel());


            statusLines.clear();
            for (Record r : Memory.getInstance().getRefreshingRecords()) {
                StatusLine line = new StatusLine(r);
                add(line.getPanel());
                statusLines.add(line);

            }
            // System.out.println(Memory.getInstance().getRefreshingRecords().size()+" "+statusLines.size());
            if (statusLines.size() == 0) {
                add(s1);
                add(s2);
            }
            pack();

        } else
            for (StatusLine line : statusLines)
                line.updateLine();

    }

    private class StatusUpdater extends TimerTask {

        public void run() {
            // System.out.println("Updated status");
            refreshProgressList();
        }
    }
}
