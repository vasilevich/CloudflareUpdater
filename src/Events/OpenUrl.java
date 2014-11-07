package Events;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Yosef on 01/11/2014.
 */
public class OpenUrl implements ActionListener {
    public final URL url;

    public OpenUrl(String u) {
        url = toURL(u);
    }

    public OpenUrl(URL u) {
        url = u;
    }

    private void launchBrowser() {
        try {
            if (Desktop.isDesktopSupported())
                Desktop.getDesktop().browse(url.toURI());
            else
                Runtime.getRuntime().exec("xdg-open " + url.toString());
        } catch (Exception ee) {
        }
    }

    private URL toURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        launchBrowser();
    }
}
