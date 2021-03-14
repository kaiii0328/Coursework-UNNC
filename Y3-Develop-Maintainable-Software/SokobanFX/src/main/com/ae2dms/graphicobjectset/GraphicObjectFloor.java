package main.com.ae2dms.graphicobjectset;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * graphic object of floor
 */
public class GraphicObjectFloor extends GraphicObject {
    /**
     * path to floor picture
     */
    public static String path  ="../../../resources/img/floor1.png";
    /**
     * constructor for GraphicObjectFloor
     */
    public GraphicObjectFloor() {

        URL resource = getClass().getResource(path);
        Image img = new Image(String.valueOf(resource));
        this.setImage(img);
        init();
    }

    /**
     * update the floor for different styles
     */
    @Override
    public void update(){
        path = "../../../resources/img/floor" + styleIndex +".png";
    }
}