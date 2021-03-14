package main.controller;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import main.com.ae2dms.*;
import main.com.ae2dms.graphicobjectset.*;

/**
 * The {@code GameRunningController} class  contains several useful class fields
 * and methods.
 * This controller class is specified to control gameRunning.fxml.
 * It will be instantiated during the fxml file loading process.
 * <p>
 *     The first part of the functions in the class is related to menuBar.
 *     Functions like {@link #saveGame()},{@link #loadGame()},{@link #undo()}, etc
 *     are the functions called once the corresponding menu item is clicked.
 * </p>
 * <p>
 *     The second part is controlling the display of the game map. Functions like
 *     {@link #reloadGrid()},{@link #initializeGame(InputStream)}, and {@link #setEventFilter()}
 *     etc are functions controlling the GUI display of the game. These functions display the
 *     latest game map after every keyboard input.
 * </p>
 * <p>
 *     The last part of the function is responsible of controlling the actions of pause menu.
 *     {@link #switchMenu()},{@link #returnHome()} are functions called when corresponding buttons
 *     are clicked.
 * </p>
 *
 *
 */
public class GameRunningController implements Initializable {
    /**
     * game grid
     */
    @FXML
    private GridPane gameGrid;
    /**
     * menubar
     */
    @FXML
    private MenuBar menubar;

    /**
     * main pause menu
     */
    @FXML
    private VBox pauseMenu;

    /**
     * setting menu
     */
    @FXML
    private VBox pauseMenu2;
    /**
     * timer in main pause menu
     */
    @FXML
    private Label timer;

    /**
     * volumeSlider in setting menu
     */
    @FXML
    private Slider volumeSlider;

    /**
     * VBox with game map 1 image
     */
    @FXML
    private VBox gameMap1VBox;

    /**
     * VBox with game map 2 image
     */
    @FXML
    private VBox gameMap2VBox;

    /**
     * label showing level information in main pause menu
     */
    @FXML
    private Label levelInfoLabel;

    /**
     * label showing move information in main pause menu
     */
    @FXML
    private Label moveInfoLabel;
    /**
     * current showing stage
     */
    private Stage primaryStage;
    /**
     * the game engine
     */
    private GameEngine gameEngine=null;
    /**
     * media player for music
     */
    private MediaPlayer mediaPlayer;
    /**
     * file path of the game file
     */
    private String filepath;
    /**
     * whether the game use default file
     */
    private boolean defaultFile = true;
    /**
     * list of graphic object observers
     */
    private List<GraphicObject> observers;

    /**
     * Automatically called when gameRunning.fxml is loaded by {@code loader.load()}
     * Instantiate a media player and by default, autoplay the music file in
     * resources/music/puzzle_theme.wav
     * Also set a slider in the pause menu enables users to control the music volume manually
     *
     * @param location url location
     * @param resources resources bundle
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources){
        Media media = new Media(new File("src/main/resources/music/puzzle_theme.wav").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        //set up the music player
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO)); // loop playback the music
        mediaPlayer.setAutoPlay(true);
        volumeSlider.setValue(mediaPlayer.getVolume()*100);
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volumeSlider.getValue()/100);
                //associate the value of media player to the ratio of volume slider
            }
        });

        // initialize observer list
        observers = new ArrayList<GraphicObject>();
        GraphicObjectWall wallStyleChanger = new GraphicObjectWall();
        observers.add(wallStyleChanger);
        GraphicObjectFloor floorStyleChanger = new GraphicObjectFloor();
        observers.add(floorStyleChanger);

    }

    /**
     * switch between two pause menus. The second menu is the sub menu of
     * the first menu, containing the setting options. For preview or adding elements
     * to the menus, refer to gameRunning.fxml.
     */
    @FXML
    void switchMenu() {
        pauseMenu.setVisible(!pauseMenu.isVisible());
        pauseMenu2.setVisible(!pauseMenu2.isVisible());
    }

    /**
     * start the game after user clicking the blank page.
     * load the default file to the current stage
     *
     * @see #loadDefaultSaveFile(Stage)
     */
    @FXML
    void startGame() {
        menubar.setVisible(true);
        gameMap1VBox.setVisible(false);
        gameMap2VBox.setVisible(false);

        //get the current showing stage
        this.primaryStage =(Stage) gameGrid.getScene().getWindow() ;
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event ->{GameStartController.deleteTempFile();System.exit(0); } );
        Thread timerThread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            while (true) {
                try {
                    Thread.sleep(1000); //1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String time = simpleDateFormat.format(new Date());
                Platform.runLater(() -> {
                    timer.setText(time);
                });
            }
        });
        timerThread.start();
        //load the default game file into current stage
        loadDefaultSaveFile(primaryStage);
    }

    /**
     * Load the default game file , instantiate a new scene and set the new scene 
     * to be displayed by the current stage
     * 
     * The relative path of the default game file is defined at {@code filepath}.
     * The file is read in as InputStream form and used for function {@code initializeGame()}.
     *
     * @param primaryStage the current stage
     *
     * @see #initializeGame(InputStream)
     *
     */
    void loadDefaultSaveFile(Stage primaryStage) {
        this.primaryStage = primaryStage;

        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filepath);
        initializeGame(in);
        setEventFilter(); //add event filter to current stage
    }

    /**
     * Instantiate a new GameEngine for GameRunningController.
     * Send the game file InputStream to load the levels of the game
     *
     * @param input the InputStream of game file
     *
     * @see GameEngine#loadGameFile(InputStream)
     */
    private void initializeGame(InputStream input) {
        gameEngine = new GameEngine(input);
        reloadGrid();
    }

    /**
     * set an event handler to current showing scene.
     * the event handler can handle the keyboard input.
     */
    private void setEventFilter() {
        Scene currentScene = primaryStage.getScene();
        currentScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.P){
                pause();   // pop the pause menu
                event.consume();   // the keyboard input will not be passed to the child node
            }else{
                gameEngine.handleKey(event.getCode());
            }
            reloadGrid();
        });
        //cheat code to skip level
        KeyCodeCombination godMode = new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN);
        //cheat code to enter puppet mode
        KeyCodeCombination puppetMode = new KeyCodeCombination(KeyCode.O,KeyCodeCombination.CONTROL_DOWN);
        //cheat code to move through walls
       KeyCodeCombination throughWallMode = new KeyCodeCombination(KeyCode.W,KeyCodeCombination.CONTROL_DOWN);
        currentScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (godMode.match(keyEvent)){
                    gameEngine.skipLevel();
                }
                if (puppetMode.match(keyEvent)){
                    gameEngine.setThroughWallMode(false);
                    gameEngine.setPuppetMode(!gameEngine.isPuppetMode());
                }
                if (throughWallMode.match(keyEvent)){
                    gameEngine.setPuppetMode(false);
                    gameEngine.setThroughWallMode(!gameEngine.isThroughWallMode());
                }
                reloadGrid();
            }
        });
    }

    /**
     * Open an dialog for user to choose the game file.
     * The file have "skb" extensions. If the game file successfully loaded,
     * the game will be re-initialized, else the method will throw a FileNotFoundException.
     *
     * If the game file is a completely fresh new game file, it will not have records for
     * {@code GameEngine#totalMoveCount} and {@code GameEngine#currentLevelMoveCount}.
     * If the game file is a half-way file, which is a saved file and the game is not completed,
     * it will will have {@code GameEngine#totalMoveCount} and {@code GameEngine#currentLevelMoveCount}.
     *
     * @throws FileNotFoundException  game file is not found
     *
     * @see GameEngine#loadGameFile(InputStream) to understand how the game load different game files
     */
    private void loadGameFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file", "*.skb"));
        File saveFile = fileChooser.showOpenDialog(primaryStage);       //selected file in the dialog
        if (saveFile != null)
            filepath = saveFile.getAbsolutePath();
        defaultFile = false;   //not using the default game file since a new file is selected

        if (saveFile != null) {
            if (GameEngine.isDebugActive()) {
                GameEngine.logger.info("Loading save file: " + saveFile.getName());
            }
            initializeGame(new FileInputStream(saveFile));
        }
    }

    /**
     * save the game records from the current level to the end of the level.
     * store the {@code GameEngine#totalMoveCount} and {@code GameEngine#currentLevelMoveCount}
     * for records
     *
     * @throws IOException exception in saving game file
     *
     * @see GameEngine#saveGame(File)
     */
    @FXML
    public void saveGame() throws IOException {
        FileChooser saveFileChooser = new FileChooser();
        saveFileChooser.setTitle("Save game file");
        saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Sokoban save file","*.skb"));
        File savedFile =  saveFileChooser.showSaveDialog(primaryStage);  //select the file to write to
        if (savedFile !=null) {
            gameEngine.saveGame(savedFile);
        }
        Stage currentStage = (Stage)gameGrid.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("../resources/views/gameStart.fxml"));
        currentStage.setScene(new Scene(root));
    }

    /**
     * load the game file
     *
     * @see #loadGameFile()
     */
    @FXML
    public void loadGame() {
        try {
            loadGameFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * reset the current level
     *
     * @throws Exception the game file is not found or fails loading
     *
     * @see GameEngine#resetCurrentLevel(InputStream)
     */
    @FXML
    public void resetLevel() throws Exception {
        InputStream in;
        if (defaultFile){
            in = this.getClass().getClassLoader().getResourceAsStream("main/resources/level/SampleGame.skb");
        }
        else
            in = new FileInputStream(filepath);
        gameEngine.resetCurrentLevel(in);
        reloadGrid();
    }

    /**
     * If the game is completed, the method will switch the showing scene to
     * game-end scene where the players could see their performance and save records.
     * The elements in game-end scene is in /resources/views/gameEnd.fxml
     *
     * If the game is not completed, the method will use a LevelIterator{@link main.com.ae2dms.Level.LevelIterator}
     * to traverse the object grid and add graphical objects to the FXMl component {@code gameGrid}
     * for displaying.During iterating, a factory pattern {@link GraphicObjectFactory} is used for
     * initializing graphical objects and enhance maintainability
     *
     * @see GraphicObjectFactory
     *
     */
    private void reloadGrid()  {
        if (gameEngine.isGameComplete()) {
            try {
                startGameEndScene();    //switch to the game-end scene
            }catch (Exception e){
                gameEngine.logger.warning("Exception in starting game-end scene");
                e.printStackTrace();
            }
            return;
        }
        Level currentLevel = gameEngine.getCurrentLevel();  //get the current level
        Level.LevelIterator levelGridIterator = (Level.LevelIterator) currentLevel.iterator(); //return a new Iterator
        gameGrid.getChildren().clear();   //clear the grid pane for reloading
        GraphicObjectFactory goFactory = GraphicObjectFactory.getInstance();
        while (levelGridIterator.hasNext()) {  //while the iterator has elements left, do the followings
            //get the Graphical object by using factory methods
            GameObject object = levelGridIterator.next();
            GraphicObject graphicObject = goFactory.getObject(object);
            Point location = levelGridIterator.getCurrentPosition();
            gameGrid.add(graphicObject,location.y,location.x);
        }

        primaryStage.sizeToScene();
    }


    /**
     * load the fxml file to show keeper pictures that can be changed.
     * @throws IOException if the fxml fails is not found or fails loading
     */
    @FXML
    private void changeKeeper() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/views/keeperSet.fxml"));
        final Stage showScoresStage = new Stage();
        showScoresStage.initModality(Modality.APPLICATION_MODAL);
        showScoresStage.initOwner(primaryStage);
        showScoresStage.setResizable(false);
        showScoresStage.setTitle("Select one character");
        showScoresStage.setScene(new Scene(root,400,400));
        showScoresStage.show();
    }


    /**
     * Switch the current showing scene to game end scene.
     * The fxml file of the game-end scene is in /resources/views/gameEnd.fxml.
     * The game-end scene is prompted in a new window where players could enter
     * their nickname and decide whether they want to save the record or not.
     * The game-end scene also shows players' performance (the steps they take to complete)
     * After saving records or not saving records, the game page will go beck to
     * game-start scene
     *
     * @throws IOException if the fxml file is not found or error loading
     */
    public void startGameEndScene() throws IOException {
        mediaPlayer.setMute(true);  //turn off the music
        primaryStage.removeEventFilter(KeyEvent.KEY_PRESSED, event -> {
            gameEngine.handleKey(event.getCode());
            reloadGrid();
        });   //remove the event filer

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/views/gameEnd.fxml"));
        Parent root = loader.load();
        GameEndController controller = loader.getController();
        String message = "You completed " + gameEngine.getMapSetName() + " in " + gameEngine.getTotalMoveCount() + " moves!";
        //send the data of move count and map set name for initialization
        controller.initData(message, gameEngine.getMapSetName(), gameEngine.getTotalMoveCount());
        final Stage gameEndScene = new Stage();
        gameEndScene.initModality(Modality.APPLICATION_MODAL);
        //the gameEndScene lays on top of the game-running scene
        //the game-running scene will not be accessed until players save their records
        // or close the game-end scene
        gameEndScene.initOwner(primaryStage);
        gameEndScene.setResizable(false);
        gameEndScene.setTitle("Game over!");
        gameEndScene.setScene(new Scene(root,400,400));
        gameEndScene.show();
    }

    /**
     * exit the game
     */
    @FXML
    public void closeGame() {
        GameStartController.deleteTempFile();
        System.exit(0);
    }

    /**
     * This method will undo the move the keeper and the previous moved crate
     * It only remember the last move, not a history of moves. For detailed implementation,
     * refer to {@link GameEngine#undo()}
     */
    @FXML
    public void undo() {
        gameEngine.undo();
        reloadGrid();
    }

    /**
     * Pop a new windowing showing the creator of the game.
     * The contents and elements is in /resources/views/showAbout.fxml
     *
     * @throws IOException if the fxml file is not found or error loading
     */
    @FXML
    public void showAbout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../resources/views/showAbout.fxml"));
        final Stage showAboutStage = new Stage();
        showAboutStage.initModality(Modality.APPLICATION_MODAL);
        showAboutStage.initOwner(primaryStage);
        showAboutStage.setResizable(false);
        showAboutStage.setTitle("About this game!");
        showAboutStage.setScene(new Scene(root,400,250));
        showAboutStage.show();
    }

    /**
     * Mute the music if not muted and Un-mute the music if muted
     */
    @FXML
    public void toggleMusic() {
        mediaPlayer.setMute(!mediaPlayer.isMute());
    }

    /**
     * Set the game to debug mode,which is controlled by GameEngine.
     *
     * If the game engine is in debug mode, the program will print debug info after every move
     * of the keeper. The method to print debug info is in interface {@link main.com.ae2dms.IMessage},
     * the implementations are {@link GameEngine#printDebugInfo(Point)},{@link GameGrid#printDebugInfo()},
     * {@link Level#printDebugInfo()}.
     *
     */
    @FXML
    public void toggleDebug() {
        gameEngine.toggleDebug();
        reloadGrid();
    }

    /**
     * Pause the game and show the pause menu. The elements in menu is in {@code pauseMenu} and {@code pauseMenu2}
     *
     */
    @FXML
    private void pause() {
        gameEngine.setPause(!gameEngine.isPause());
        String levelInfo = "Level " + gameEngine.getCurrentLevel().getIndex() + " : " + gameEngine.getCurrentLevel().getName();
        levelInfoLabel.setText(levelInfo);
        String moveInfo = "Current Level Move Count : " + gameEngine.getCurrentLevelMoveCount();
        moveInfoLabel.setText(moveInfo);
        pauseMenu.setVisible(!pauseMenu.isVisible());
        reloadGrid();
    }

    /**
     * Terminate the game and return to home page.
     * All the records for the running game will be lost.
     * The method is called only in the pause menu when return-home button
     * is clicked.
     *
     * @throws IOException if fxml is not found or error loading
     */
    @FXML
    private void returnHome() throws IOException {
        mediaPlayer.setMute(true);  //turn off the music
        Parent root = FXMLLoader.load(getClass().getResource("../resources/views/gameStart.fxml"));
        primaryStage.setScene(new Scene(root));
    }

    /**
     * set the map file path
     * @param event mouse clicks button
     */
    @FXML
    void setMap1(MouseEvent event) {
        filepath ="main/resources/level/SampleGame.skb";
        startGame();
    }

    /**
     * set the map file path
     * @param event mouse clicks
     */
    @FXML
    void setMap2(MouseEvent event) {
        filepath = "main/resources/level/adventure.skb";
        startGame();
    }

    /**
     * change the styleIndex in {@link GraphicObject#styleIndex}
     * @param event mouse click
     */
    @FXML
    void changeStyle1(MouseEvent event) {
        GraphicObject.styleIndex = 1;
        notifyAllObservers();
    }

    /**
     * change the styleIndex in {@link GraphicObject#styleIndex}
     * @param event mouse click
     */
    @FXML
    void changeStyle2(MouseEvent event) {
        GraphicObject.styleIndex = 2;
        notifyAllObservers();
    }

    /**
     * notify all the observers in the list
     */
    private void notifyAllObservers(){
        for (GraphicObject observer : observers){
            observer.update();
        }
    }
}
