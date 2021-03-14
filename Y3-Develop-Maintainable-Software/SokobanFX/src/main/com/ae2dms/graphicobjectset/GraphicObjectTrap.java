package main.com.ae2dms.graphicobjectset;

import javafx.scene.image.Image;
import java.net.URL;

/**
 * graphic object of trap
 */
public class GraphicObjectTrap extends GraphicObject{
    /**
     * constructor for GraphicObjectTrap
     */
    public GraphicObjectTrap(){
        String path = "../../../resources/img/trap.png";
        URL resource = getClass().getResource(path);
        Image img = new Image(String.valueOf(resource));
        this.setImage(img);
        init();
    }
}
