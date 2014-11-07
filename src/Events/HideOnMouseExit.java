package Events;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Yosef on 01/11/2014.
 */
public class HideOnMouseExit extends MouseAdapter {


    @Override
    public void mouseExited(MouseEvent e) {
        Component component = e.getComponent();
        Point mouse = MouseInfo.getPointerInfo().getLocation(), componentLoc = component.getLocationOnScreen();

        if (mouse.getX() <= componentLoc.getX()
                || mouse.getY() <= componentLoc.getY()
                || mouse.getX() >= (componentLoc.getX() + component.getWidth())
                || mouse.getY() >= (componentLoc.getY() + component.getHeight())
                ) {
            component.setVisible(false);
        }
    }
}



