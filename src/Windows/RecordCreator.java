package Windows;

import Data.Record;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Yosef on 05/10/2014.
 */
public class RecordCreator extends JPanel {
    private final Record r;

    private final JTextField nameField;
    private final JComboBox typeList;
    private final JTextField valueField;

    public RecordCreator(Record inr) {
        r = inr;
        nameField = new JTextField(r.getName());

        valueField = new JTextField(r.getValue());

        String[] Types = {"A", "CNAME", "MX"};
        typeList = new JComboBox(Types);
        typeList.setSelectedItem(r.getType());
        typeList.addActionListener(new onChange());


        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Type:"));
        add(typeList);
        add(new JLabel("Value:"));
        add(valueField);
        int result = JOptionPane.showConfirmDialog(null, this,
                "Create record for zone:  " + r.zone, JOptionPane.OK_CANCEL_OPTION);
        //   System.out.println(r.isIPUpdater());
        if (result == JOptionPane.OK_OPTION)
            if (!(r.getName().equals(nameField.getText()) && r.getType().equals(typeList.getSelectedItem().toString()) && r.getValue().equals(valueField.getText()) && !r.isIPUpdater())) {
                r.setName(nameField.getText());
                r.setType(typeList.getSelectedItem().toString());
                r.setValue(valueField.getText());
                r.update();


            }
    }


    public Record getRecord() {
        return r;
    }

    private class onChange implements java.awt.event.ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (typeList.getSelectedItem().toString().equals("A"))
                valueField.setText("$AutoIP");
            else
                valueField.setText(r.getValue());
        }
    }
}
