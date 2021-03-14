package main.com.ae2dms.graphicobjectset;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import main.com.ae2dms.GameObject;
import main.com.ae2dms.graphicobjectset.IObserver;

import java.io.InputStream;

/**
 * The {@code GraphicObject} is the base class for Graphic display
 * The derived objects are in com.ae2dms.graphicobjectset package
 *
 */
public class GraphicObject extends ImageView implements IObserver {
    /**
     * style number for two styles
     */
    public static int styleIndex = 1;
    public GraphicObject(){}
    /**
     * init the appearance of each object, including color,width,height,shape etc.
     */
    public void init() {
        this.setFitHeight(30);
        this.setFitWidth(30);
    }

    @Override
    public void update() {}
}
