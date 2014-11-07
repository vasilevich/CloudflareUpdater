package Windows;

import Data.DetectOS;
import Data.Memory;
import Events.*;
import Resources.ResourcesManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Yosef on 11/10/2014.
 */
public class Menu extends JMenuBar {
    public static ImageIcon autostart = ResourcesManager.getImageIco("autostart.png");
    public static ImageIcon nostart = ResourcesManager.getImageIco("nostart.png");
    public static ImageIcon exit = ResourcesManager.getImageIco("exit.png");
    private static ImageIcon operations = ResourcesManager.getImageIco("operations.png");
    private static ImageIcon help = ResourcesManager.getImageIco("help.png");
    private static ImageIcon aboutus = ResourcesManager.getImageIco("aboutus.png");
    private static JMenuItem miOperations, miHelp, miAbout, miExit;
    private static JCheckBoxMenuItem cbAutoStart, cbGlobalRefresher;
    private JMenu menu = new JMenu("File");

    public Menu() {
        menu.setMnemonic(KeyEvent.VK_S);
        menu.getAccessibleContext().setAccessibleDescription(
                "Options And Settings");

        add(menu);

        fillMenuItems(menu);
    }

    public static String getmiHelpUrl() {
        for (ActionListener ac : miHelp.getActionListeners())
            if (ac.getClass().getName().contains("OpenUrl"))
                return ((OpenUrl) ac).url.toString();
        return null;
    }

    private void fillMenuItems(JMenu inMenu) {


//a group of JMenuItems
        miOperations = new JMenuItem("Current Refreshing Operations", operations);
        miOperations.setMnemonic(KeyEvent.VK_O);
        miOperations.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.ALT_MASK));
        miOperations.addActionListener(new StartStatusViewer());
        menu.add(miOperations);


//a group of check box menu items
        //Start with windows or not?
        menu.addSeparator();
        cbAutoStart = new JCheckBoxMenuItem("Start with " + new DetectOS().OS, Memory.getInstance().isAutoStartup() ? autostart : nostart);


        cbAutoStart.setMnemonic(KeyEvent.VK_S);
        cbAutoStart.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.ALT_MASK));

        cbAutoStart.addActionListener(new StartupManager());
        cbAutoStart.setState(Memory.getInstance().isAutoStartup());

        menu.add(cbAutoStart);


        //Global Refresher
        cbGlobalRefresher = new JCheckBoxMenuItem("Global IPS Refresher", nostart);
        cbGlobalRefresher.setMnemonic(KeyEvent.VK_G);
        cbGlobalRefresher.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        cbGlobalRefresher.addActionListener(new StartNoStart());
        //menu.add(cbGlobalRefresher);

//Tutorials help and credits
        menu.addSeparator();
        miHelp = new JMenuItem("Help", help);
        miHelp.setMnemonic(KeyEvent.VK_H);
        miHelp.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_H, ActionEvent.ALT_MASK));
        miHelp.addActionListener(new OpenUrl(Memory.getInstance().homepage + "/cfupdater#help"));
        menu.add(miHelp);

        miAbout = new JMenuItem("About us", aboutus);
        miAbout.setMnemonic(KeyEvent.VK_A);
        miAbout.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_A, ActionEvent.ALT_MASK));
        miAbout.addActionListener(new OpenUrl(Memory.getInstance().homepage + "/cfupdater#about"));

        menu.add(miAbout);


        menu.addSeparator();
        miExit = new JMenuItem("Exit", exit);
        miExit.setMnemonic(KeyEvent.VK_E);
        miExit.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.ALT_MASK));
        miExit.addActionListener(new Exit());
        menu.add(miExit);

        for (int loop = 0, length = menu.getItemCount(); loop < length; loop++)
            if (menu.getItem(loop) != null)
                menu.getItem(loop).addMouseListener(new MouseToParent());


    }


    public JPopupMenu getPopUpMenu() {
        return menu.getPopupMenu();
    }


}
