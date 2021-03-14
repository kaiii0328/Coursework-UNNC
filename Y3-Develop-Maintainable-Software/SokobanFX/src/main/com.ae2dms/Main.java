package main.com.ae2dms;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * main class for sokoban game
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start the application and show the stage.
     * @param primaryStage stage where the scene is set
     * @throws Exception if the fxml is not found or error loading in {@link #GameStartScene()}
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene gameStartScene = GameStartScene();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
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
        });
        primaryStage.setTitle(GameEngine.GAME_NAME);
        primaryStage.setScene(gameStartScene);
        //primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Load gameStart.fxml and return a scene object
     * @return return a Javafx.scene object
     * @throws IOException if fxml is not found or error loading
     */
    public Scene GameStartScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../resources/views/gameStart.fxml"));
        return new Scene(root);
    }
}
