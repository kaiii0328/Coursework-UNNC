package main.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.com.ae2dms.GameEngine;

/**
 * The {@code GameStartController} class contains several useful class fields
 * and methods.
 * This controller class is specified to control gameStart.fxml.
 * It will be instantiated during the fxml file loading process.
 * <p>
 *     The controller class has basic functions for game starting page,
 *     like starting game{@link #gameStart}, showing introduction of the game{@link #showInfo(MouseEvent)}
 *     ,showing high score lists{@link #showScores(MouseEvent)}.
 * </p>
 *
 * since 0.1
 */
public class GameStartController {
    /**
     * information box for game introduction
     */
    @FXML
    private VBox infoBox;

    /**
     * The following method is called when exit option button
     * is clicked.
     * <p>
     * Terminate the system with exit code 0, it calls  {@code System.exit(n)}
     * </p>
     *
     * @param event  mouse event when clicking button
     */
    @FXML
    void exitGame(MouseEvent event) {
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        deleteTempFile();
        currentStage.close();
        System.exit(0);
    }

    /**
     * delete the temp file created for storing level records
     */
    static void  deleteTempFile(){
        String dir= System.getProperty("user.dir") + "/src/main/resources/records/temp.txt".replace("/",System.getProperty("file.separator"));
        Path recordFilePath = Paths.get(dir);
        if (Files.exists(recordFilePath)){
            try {
                Files.delete(recordFilePath);
            } catch (IOException e) {
                GameEngine.logger.warning("Exception in deleting temp file");
            }
        }
    }

    /**
     * This method is called when the start game button is clicked.
     * It loads the gameRunning fxml file and is responsible for switching between the
     * game-start page and game-running page
     *
     * @param event mouse event when clicking button
     * @throws IOException if {@code gameRunning.fxml} is not found or failed loading
     *
     */
    @FXML
    void gameStart(MouseEvent event) throws IOException {
        infoBox.setVisible(false);
        Parent root = FXMLLoader.load(getClass().getResource("../resources/views/gameRunning.fxml"));
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root,605,665));
    }

    /**
     * Control the visibility of the information box{@code infobox}
     * The introduction of the Sokoban game is pre-written in SceneBuilder
     * For modifying the information of the game, simply refer to resources/views/gameStart.fxml
     * to change the content
     *
     * @param event mouse click
     */
    @FXML
    void showInfo(MouseEvent event) {
        infoBox.setVisible(true);
    }

    /**
     * Prompt a window to to show permanent high scores list.
     * Current version requires user to see the high scores list for two maps
     *
     * @param event mouse event when the Scores button is clicked
     * @throws IOException if the fxml is not found or error loading
     */
    @FXML
    void showScores(MouseEvent event) throws IOException {
        infoBox.setVisible(false);
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../resources/views/showTotalScores.fxml"));
        final Stage showScoresStage = new Stage();
        showScoresStage.initModality(Modality.APPLICATION_MODAL);
        showScoresStage.initOwner(currentStage);
        showScoresStage.setResizable(false);
        showScoresStage.setTitle("Permanent High Score List!");
        showScoresStage.setScene(new Scene(root,400,270));
        showScoresStage.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {}

}
