package Events;

import Data.Memory;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Yosef on 07/11/2014.
 */
public class selectAllOnClick extends MouseAdapter {

    private boolean alreadyClicked = false;


    @Override
    public void mousePressed(MouseEvent e) {
        JTextField field = (JTextField) e.getSource();
        if ((!alreadyClicked || !Memory.getInstance().isValidated()) && (!field.isFocusOwner())) {

            System.out.println(field.getText());
            field.selectAll();
        }
        alreadyClicked = true;
    }


}
