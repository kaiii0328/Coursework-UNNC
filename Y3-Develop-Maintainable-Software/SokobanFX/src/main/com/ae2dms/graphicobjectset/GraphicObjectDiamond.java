package main.com.ae2dms.graphicobjectset;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import main.com.ae2dms.GameEngine;

import java.net.URL;

/**
 * graphic object of diamond
 */
public class GraphicObjectDiamond extends GraphicObject {
    /**
     * constructor for GraphicObjectDiamond
     */
    public GraphicObjectDiamond(){
        URL resource = getClass().getResource("../../../resources/img/diamond.jpg");
        Image img = new Image(String.valueOf(resource));
        this.setImage(img);
        init();
        if (GameEngine.isDebugActive()) {
            FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
            ft.setFromValue(1.0);
            ft.setToValue(0.2);
            ft.setCycleCount(Timeline.INDEFINITE);
            ft.setAutoReverse(true);
            ft.play();
        }

    }
}
