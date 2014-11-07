package Events;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Yosef on 01/11/2014.
 */
public class MouseToParent implements MouseListener {
    private JComponent parent;

    public void setSource(JComponent source) {
        parent = source;
    }

    private void configureParent(MouseEvent e) {
        if (parent == null)
            parent = ((JComponent) e.getComponent().getParent());
        e.setSource(parent);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        configureParent(e);
        for (MouseListener l : parent.getMouseListeners())
            l.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        configureParent(e);
        for (MouseListener l : parent.getMouseListeners())
            l.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        configureParent(e);
        for (MouseListener l : parent.getMouseListeners())
            l.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        configureParent(e);
        for (MouseListener l : parent.getMouseListeners())
            l.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        configureParent(e);
        for (MouseListener l : parent.getMouseListeners())
            l.mouseExited(e);
    }
}
