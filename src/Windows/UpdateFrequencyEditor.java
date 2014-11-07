package Windows;

import Data.Record;

import javax.swing.*;

/**
 * Created by Yosef on 17/10/2014.
 */
public class UpdateFrequencyEditor extends JPanel {

    private final Record r;

    private final JTextField frequencyField;

    public UpdateFrequencyEditor(Record inr) {
        r = inr;
        frequencyField = new JTextField(Long.toString(r.getlastScheduledTimeSeconds()));
        frequencyField.setColumns(frequencyField.getText().length() + 5);

        add(new JLabel("Update Frequency:"));
        add(frequencyField);

        int result = JOptionPane.showConfirmDialog(null, this,
                "Edit record for zone:  " + r.zone, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
            r.setlastScheduledTimeSeconds(Long.parseLong(frequencyField.getText()));

    }


}
