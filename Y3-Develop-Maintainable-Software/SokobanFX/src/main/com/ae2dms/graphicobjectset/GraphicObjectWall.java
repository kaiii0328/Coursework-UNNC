package main.com.ae2dms.graphicobjectset;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * graphic object of wall
 */
public class GraphicObjectWall extends GraphicObject{
    /**
     * path to wall picture
     */
    public static String path = "../../../resources/img/wall1.png";
    /**
     * constructor for GraphicObjectWall
     */
    public GraphicObjectWall(){
        URL resource = getClass().getResource(path);
        Image img = new Image(String.valueOf(resource));
        this.setImage(img);
        init();
    }

    /**
     * update wall pictures
     */
    @Override
    public void update(){
        path = "../../../resources/img/wall" + GraphicObject.styleIndex + ".png";
    }

}
