package main.com.ae2dms.graphicobjectset;

import javafx.scene.image.Image;
import java.net.URL;

/**
 * graphic object of keeper_on_gate
 */
public class GraphicObjectKeeperGate extends GraphicObject {
    /**
     * constructor for GraphicObjectKeeperGate
     */
    public GraphicObjectKeeperGate() {
        URL resource = getClass().getResource("../../../resources/img/keeper_on_gate.PNG");
        Image img = new Image(String.valueOf(resource));
        this.setImage(img);
        init();
    }
}
