package Events;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Yosef on 11/10/2014.
 */
public class DoubleClick extends MouseAdapter implements ActionListener {
    MouseEvent lastEvent;
    Timer timer;

    public DoubleClick() {
        this((Integer) Toolkit.getDefaultToolkit().
                getDesktopProperty("awt.multiClickInterval"));
    }

    public DoubleClick(int delay) {
        timer = new Timer(delay, this);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 2) return;

        lastEvent = e;

        if (timer.isRunning()) {
            timer.stop();
            doubleClick(lastEvent);
        } else {
            timer.restart();
        }
    }

    public void actionPerformed(ActionEvent e) {
        timer.stop();
        singleClick(lastEvent);
    }

    public void singleClick(MouseEvent e) {
    }

    public void doubleClick(MouseEvent e) {
        System.out.println("Double Clicked");
    }


}
