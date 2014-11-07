package Events;

import Windows.Menu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Yosef on 31/10/2014.
 */
public class StartNoStart implements ActionListener {
    private ImageIcon autostart = Menu.autostart;
    private ImageIcon nostart = Menu.nostart;

    public StartNoStart() {
    }

    public StartNoStart(ImageIcon s, ImageIcon n) {
        autostart = s;
        nostart = n;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JCheckBoxMenuItem m = ((JCheckBoxMenuItem) e.getSource());
        perform(m.getState());
        if (m.getState())
            m.setIcon(autostart);
        else
            m.setIcon(nostart);
    }

    protected void perform(boolean state) {


    }


}