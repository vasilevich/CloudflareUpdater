package Windows;

/**
 * Created by Yosef on 17/10/2014.
 */

import Resources.ResourcesManager;

import javax.swing.*;
import java.awt.*;

public class SplashScreen {
    private static int AwaitingHide = 0;
    private static JWindow window;

    public static void showSplash() {

        if (AwaitingHide <= 0) {
            window = new JWindow();
            JPanel content = (JPanel) window.getContentPane();
            content.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));

            // Set the window's bounds, centering the window
            int width = 195;
            int height = 244;
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screen.width - width) / 2;
            int y = (screen.height - height) / 2;
            window.setBounds(x, y, width, height);

            // Build the splash screen
            JLabel label = new JLabel(ResourcesManager.getImageIco("splash.gif"));
            JLabel copyrt = new JLabel(ResourcesManager.getImageIco("loading.gif"), JLabel.CENTER);

            content.add(label, BorderLayout.CENTER);

            label.setLayout(new GridBagLayout());
            Font font = copyrt.getFont();
            copyrt.setFont(font.deriveFont(Font.BOLD, 26f));
            copyrt.setOpaque(true);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            label.add(copyrt, gbc);
            content.setOpaque(false);
            window.setBackground(new Color(0, 0, 0, 0));
            window.setLocationRelativeTo(null);


            // Display it
            window.setVisible(true);
        }
        AwaitingHide++;


    }

    public static void hideSplash() {
        AwaitingHide--;
        if (AwaitingHide <= 0) {
            if (window != null)
                window.setVisible(false);
            window = null;
            AwaitingHide = 0;
        }
    }

}