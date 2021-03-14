package main.com.ae2dms.graphicobjectset;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * graphic object of keeper
 */
public class GraphicObjectKeeper extends GraphicObject {
    /**
     * keeper picture name
     */
    public static String keeperPic = "keeper.jpg";
    /**
     * constructor for GraphicObjectKeeper
     */
    public GraphicObjectKeeper() {
        String path = "../../../resources/img/";
        URL resource = getClass().getResource(path + keeperPic);
        Image img = new Image(String.valueOf(resource));
        this.setImage(img);
        init();
    }
}
