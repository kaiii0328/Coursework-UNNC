package main.com.ae2dms.graphicobjectset;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * graphic object of crate_on_diamond
 */
public class GraphicObjectCrateDiamond extends GraphicObject {
    /**
     * constructor for GraphicObjectCrateDiamond
     */
    public GraphicObjectCrateDiamond() {
        URL resource = getClass().getResource("../../../resources/img/crate_on_diamond.png");
        Image img = new Image(String.valueOf(resource));
        this.setImage(img);
        init();
    }
}