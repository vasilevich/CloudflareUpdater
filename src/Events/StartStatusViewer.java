package Events;

import Windows.StatusViewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Yosef on 31/10/2014.
 */
public class StartStatusViewer implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        new StatusViewer();
    }
}
