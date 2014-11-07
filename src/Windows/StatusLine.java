package Windows;

import Data.Record;
import Events.DoubleClick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Yosef on 12/10/2014.
 */
public class StatusLine {
    private final Record r;
    private JProgressBar rProgress;
    private JLabel rName;
    private JPanel panel;

    public StatusLine(Record r) {
        this.r = r;
        rName.setText(r.zone + " " + r.getName());
        // rName.setMinimumSize(width, height);

        // rName.setMaximumSize(width, height);
        rProgress.setMinimum(0);
        updateLine();
        panel.setVisible(true);
        panel.addMouseListener(new editTime());
    }


    public void updateLine() {
        long tleft = (r.getlastScheduledTimeSeconds() - r.getTimeLeft());
        rProgress.setValue((int) tleft);
        rProgress.setMaximum((int) r.getlastScheduledTimeSeconds());

        if (tleft > 1) {
            rProgress.setString(tleft + " Seconds");
            rProgress.setForeground(Color.orange);
            rName.setForeground(Color.black);
        } else if (tleft == 1) {
            rProgress.setString(tleft + " Second");

        } else {
            rProgress.setString(tleft + " Seconds");
            rProgress.setForeground(Color.green);
            rName.setForeground(Color.green);
        }

    }


    public JPanel getPanel() {

        return panel;
    }

    private class editTime extends DoubleClick {

        @Override
        public void doubleClick(MouseEvent e) {
            new UpdateFrequencyEditor(r);
        }

    }
}
