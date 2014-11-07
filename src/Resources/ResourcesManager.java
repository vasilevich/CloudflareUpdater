package Resources;

import javax.swing.*;
import java.net.URL;

/**
 * Created by Yosef on 11/10/2014.
 */
public class ResourcesManager {


    public static URL getImageUrl(String img) {

        return new ResourcesManager().getClass().getResource("/Resources/images/" + img);

    }

    public static ImageIcon getImageIco(String img) {

        return new ImageIcon(getImageUrl(img));

    }

}
