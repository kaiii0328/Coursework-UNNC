package main.com.ae2dms.graphicobjectset;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * graphic object of crate
 */
public class GraphicObjectCrate extends GraphicObject {
    /**
     * constructor for GraphicObjectCrate
     */
    public GraphicObjectCrate(){
        URL resource = getClass().getResource("../../../resources/img/crate1.png");
        Image img = new Image(String.valueOf(resource));
        this.setImage(img);
        init();
    }

}
