package main.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.com.ae2dms.graphicobjectset.GraphicObjectKeeper;

/**
 * The {@code ChangeKeeperController} is the control to show keeper pictures that user can
 * select.
 */
public class ChangeKeeperController {
    /**
     * change to keeper number 0
     * @param event mouse click
     */
    @FXML
    void changeKeeper0(MouseEvent event) {
        GraphicObjectKeeper.keeperPic = "keeper.jpg";
        closeWindow(event);
    }

    /**
     * change to keeper number 1
     * @param event mouse click
     */
    @FXML
    void changeKeeper1(MouseEvent event) {
        GraphicObjectKeeper.keeperPic = "keeper1.PNG";
        closeWindow(event);
    }

    /**
     * change to keeper number 2
     * @param event mouse click
     */
    @FXML
    void changeKeeper2(MouseEvent event) {
        GraphicObjectKeeper.keeperPic = "keeper2.PNG";
        closeWindow(event);
    }

    /**
     * change to keeper number 3
     * @param event mouse click
     */
    @FXML
    void changeKeeper3(MouseEvent event) {
        GraphicObjectKeeper.keeperPic = "keeper3.PNG";
        closeWindow(event);
    }

    /**
     * change to keeper number 4
     * @param event mouse click
     */
    @FXML
    void changeKeeper4(MouseEvent event) {
        GraphicObjectKeeper.keeperPic = "keeper4.PNG";
        closeWindow(event);
    }

    /**
     * change to keeper number 5
     * @param event mouse click
     */
    @FXML
    void changeKeeper5(MouseEvent event) {
        GraphicObjectKeeper.keeperPic = "keeper5.PNG";
        closeWindow(event);
    }

    /**
     * close window showing keeper pictures
     * @param event mouse click
     */
    private void closeWindow(MouseEvent event){
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.close();
    }


}
