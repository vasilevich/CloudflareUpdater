package Windows;


import Data.ArgsParser;
import Data.Memory;
import Events.Exit;
import Resources.ResourcesManager;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Yosef on 04/10/2014.
 */
public class Main extends JFrame {
    private ImageIcon logo = ResourcesManager.getImageIco("logoicon.png");

    public Main(String[] args) {

        //changes the feel of the gui.
        setWindowLookAndFeel();

        SplashScreen.showSplash();
        Memory.getInstance().loadSettings();

        setJMenuBar(new Menu());
        settings s = new settings();

        add(s.panel);

        windowSetup();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                new Exit().performExit();
            }
        });


        new SystemTrayManager(this);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();

        SplashScreen.hideSplash();


        startupActions(new ArgsParser(args));


    }


    public Main(boolean extension) {
        windowSetup();

    }

    public static void main(String[] args) {
        new Main(args);
        System.gc();
    }

    private void startupActions(ArgsParser args) {
        if (args.getBolean("minimized") || args.getBolean("minimize") || args.getBolean("minimizedonstart"))
            setState(ICONIFIED);

        if (args.getBolean("exit") || args.getBolean("close") || args.getBolean("quit"))
            new Exit().performExit();

    }

    private void setWindowLookAndFeel() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    protected void windowSetup() {


        setTitle("Cloudflare Domains Updater");
        setIconImage(logo.getImage());
        setSize(500, 500);
        //This will center the JFrame in the middle of the screen

        setLocationRelativeTo(null);

        setVisible(true);


    }


}
