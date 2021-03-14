package main.com.ae2dms.graphicobjectset;

import javafx.scene.image.Image;
import java.net.URL;
/**
 * graphic object of gate
 */
public class GraphicObjectGate extends GraphicObject {
    /**
     * constructor for GraphicObjectGate
     */
    public GraphicObjectGate() {
        URL resource = getClass().getResource("../../../resources/img/gate1.jpg");
        Image img = new Image(String.valueOf(resource));
        this.setImage(img);
        init();
    }
}
