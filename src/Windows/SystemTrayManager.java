package Windows;

import Events.HideOnMouseExit;
import Resources.ResourcesManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * Created by Yosef on 31/10/2014.
 */
public class SystemTrayManager {
    private static final TrayIcon trayIcon = new TrayIcon(ResourcesManager.getImageIco("logoicon.png").getImage(), "CloudFlare Updater", null);
    private static boolean inTray = false;

    public SystemTrayManager(final JFrame frame) {
        final SystemTray tray = SystemTray.getSystemTray();
        final JPopupMenu jpopup = new Menu().getPopUpMenu();

        jpopup.addMouseListener(new HideOnMouseExit());


        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {

                if (e.isPopupTrigger()) {
                    jpopup.setLocation(e.getX() - 200, e.getY() - 200);
                    jpopup.setInvoker(jpopup);
                    jpopup.setVisible(true);


                } else {
                    frame.setVisible(true);
                    frame.setState(Frame.NORMAL);
                    clearSystemTray();
                }

            }
        });


        frame.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == frame.ICONIFIED) {
                    frame.setVisible(false);
                    setSystemTray();
                }
            }
        });
    }


    public static void clearSystemTray() {
        if (inTray) {
            SystemTray.getSystemTray().remove(SystemTrayManager.trayIcon);
            inTray = false;
        }
    }

    public static void setSystemTray() {
        if (!inTray) {
            try {
                SystemTray.getSystemTray().add(SystemTrayManager.trayIcon);
                inTray = true;
            } catch (AWTException e) {
                System.out.println("unable to add to tray");
            }
        }
    }

}