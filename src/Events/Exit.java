package Events;

import Data.Memory;
import Windows.SystemTrayManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Yosef on 01/11/2014.
 */
public class Exit implements ActionListener {

    public void performExit() {
        Memory.getInstance().saveSettings();
        SystemTrayManager.clearSystemTray();
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        performExit();
    }
}
