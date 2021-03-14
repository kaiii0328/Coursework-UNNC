package main.com.ae2dms;

import javafx.scene.input.KeyCode;
import main.controller.GameRunningController;

import java.awt.*;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The {@code GameEngine} class contains several methods and fields for game running.
 * <p>
 *     First part is about control the movements of game objects. Methods {@link #move(Point)},
 *     {@link #handleKey(KeyCode)}, {@link #undo()} etc are responsible for controlling the move and
 *     count the steps in each level.
 * </p>
 * <p>
 *     The second part is about game level control.Methods {@link #getNextLevel()},{@link #resetCurrentLevel(InputStream)},
 *     {@link #getCurrentLevel()} are responsible to reset and switch between the levels.
 * </p>
 * <p>
 *     The third part is about game file management. Methods {@link #saveGame(File)},{@link #loadGameFile(InputStream)} are
 *     responsible for saving and loading the game file.
 * </p>
 */
public class GameEngine {
    /**
     * name of the game
     */
    public static final String GAME_NAME = "SokobanFX";
    /**
     * game logger for key messages
     */
    public static GameLogger logger;
    /**
     * flag variable for puppet mode
     */
    private boolean puppetMode =false;
    /**
     * flag variable for through-wall mode
     */
    private boolean throughWallMode =false;
    /**
     * flag variable for pause mode
     */
    private boolean isPause =false;
    /**
     * current level count
     */
    private int currentLevelMoveCount = 0;
    /**
     * total move count
     */
    private int totalMoveCount = 0;
    /**
     * map set name
     */
    private String mapSetName;
    /**
     * flag variable for debug
     */
    private static boolean debug = false;
    /**
     * current level
     */
    private Level currentLevel;
    /**
     * all the levels for selected map
     */
    private List<Level> levels;
    /**
     * if game is completed
     */
    private boolean gameComplete = false;
    /**
     * return delta for {@link #undo()}
     */
    private Point returnMove;
    /**
     * check if keeper moved
     */
    private boolean crateMoved;
    /**
     * stores current crate position for {@link #undo()}
     */
    private Point currentCratePosition;


    /**
     * Initialize the game engine.Components to be initialized includes the followings
     * <ul>
     *     <li>initialize the GameLogger {@link GameLogger} to log info</li>
     *     <li>load the game file {@link #loadGameFile(InputStream)}</li>
     *     <li>get the start level {@link #getNextLevel()}</li>
     * </ul>
     * @param input input stream of the game file
     */
    public GameEngine(InputStream input) {
        try {
            gameComplete = false;
            logger = new GameLogger();
            levels = loadGameFile(input);
            currentLevel = null;
            currentLevel = getNextLevel();
        } catch (IOException x) {
            System.out.println("Cannot create logger.");
        } catch (NoSuchElementException e) {
            logger.warning("Cannot load the default save file: " + e.getStackTrace());
        }
    }

    /**
     * return the debug state of the gameEngine.
     * @return return a boolean value,true if gameEngine is in debug mode, false if not
     */
    public static boolean isDebugActive() {
        return debug;
    }

    /**
     * Handle the keyboard input to control the keeper.
     * The method uses a predefined factory {@link MovementFactory} which contains a hashmap of
     * keycode-point map set to get the corresponding movement of the keyboard input.
     * If the keycode is predefined, the movement direction will be returned. Otherwise, the factory
     * method will return a Point with zero values for both x and y. The keeper will remain in the
     * same place.
     *
     * @param code keyboard input.
     *
     * @see GameRunningController#reloadGrid()
     * @see MovementFactory
     */
    public void handleKey(KeyCode code) {
        if (isDebugActive()) {
            System.out.println(code);
        }
        //factory pattern to get the direction of next move
       Point point = MovementFactory.getMovement(code).orElse(new Point(0,0));
        if (isPause){
            return;
        }
       if (point.getX() == 0 && point.getY()==0){
          return;  // if the values of point.x and point.y are zero, do nothing.
       }
       else {
           move(point);
       }
    }

    /**
     * This function will move keeper. The actual function to handle movement is {@link #isKeeperMoved(Point, Point, GameObject)},
     * where the function breaks down the situation for movements into three parts, in front of crate, in front of wall and
     * in fron of nothing(floor).
     *
     * If the keeper is moved({@code KeeperMoved} is true), this method will store the {@code returnMove}(the opposite direction of {@code delta}),
     * add the move count for current level or add {@code currentLeveMoveCount} to {@code totalMoveCount} and get next level if the game is complete.
     *
     * @param delta the direction of the move.
     */
    private void move(Point delta) {
        if (isGameComplete()) {
            return;
        }
        Point keeperPosition = currentLevel.getKeeperPosition();
        if (isPuppetMode()){
            keeperPosition = currentLevel.getPuppetCratePosition();
        }
        Point targetObjectPoint = translatePoint(keeperPosition, delta);
        GameObject keeperTarget = currentLevel.getObjectsGrid().getGameObjectAt(targetObjectPoint);

        crateMoved = false;
        boolean keeperMoved = isKeeperMoved(delta, targetObjectPoint,keeperTarget);
        if (keeperMoved) {
            keeperPosition.translate((int) delta.getX(), (int) delta.getY());

            currentLevelMoveCount++;
            if (currentLevel.isComplete()) {
                if (isDebugActive()) {
                    System.out.println("Level complete!");
                }
                try{
                    //save current level record to temp file
                    saveCurrentLevelRecord();
                }catch (IOException e){
                    logger.warning("Error storing level record");
                }

                totalMoveCount +=currentLevelMoveCount;
                currentLevelMoveCount = 0;
                currentLevel = getNextLevel();
            }
        }
        if (GameEngine.isDebugActive()) {
            printDebugInfo(delta);
        }

    }

    /**
     * The method moves the keeper. It will return true if keeper is moved,false if not.
     * Case that keeper will not move is that the destination point is a wall.
     * If the keeper moves the crate, the flag {@code crateMoved} will be true also.
     *
     * @param delta the move of the object
     * @param targetObjectPoint the position of the keeper
     * @param target the target at the destination
     * @return return true if keeper moved, false if not
     *
     * @see #moveObject(Point, Point, GameObject)
     */
    private boolean isKeeperMoved(Point delta,Point targetObjectPoint,GameObject target) {
        boolean keeperMoved = false;
        GameObject keeper = null;
        GameObject keeperTarget = target;
        Point keeperPosition = currentLevel.getKeeperPosition() ;
        if (isPuppetMode()){
            keeperPosition = currentLevel.getPuppetCratePosition();
            keeper = currentLevel.getObjectsGrid().getGameObjectAt(keeperPosition);
        }
        switch (keeperTarget) {
            case CRATE:
                GameObject crateTarget = currentLevel.getTargetObject(targetObjectPoint, delta);
                if (crateTarget != GameObject.FLOOR) {
                    break;
                }
                moveObject(delta, targetObjectPoint, keeperTarget); // move the target crate
                if (isPuppetMode()){
                    moveObject(delta,keeperPosition, keeper);           // move puppet crate
                }
                returnMove = new Point((int)-delta.getX(),(int)-delta.getY());
                keeperMoved = true;
                crateMoved  = true;
                break;
            case GATE:
                if (!puppetMode)
                    keeperMoved = true;  //teleportation only for real keeper
                returnMove = new Point(0,0);
                break;
            case TRAP:
                if (!isPuppetMode())
                    currentLevel.setKeeperPosition(new Point(currentLevel.getIniKeeperPosition())); //back to start
                break;
            case WALL:
                if (isThroughWallMode()){
                    keeperMoved = true;  // only true in through-wall mode
                }
                break;
            case FLOOR:
                if (isPuppetMode()){
                    moveObject(delta,keeperPosition, keeper);          //move puppet crate
                }
                keeperMoved = true;
                returnMove = new Point((int)-delta.getX(),(int)-delta.getY());
                break;

            default:
                logger.severe("The object to be moved was not a recognised GameObject.");
                throw new AssertionError("This should not have happened. Report this problem to the developer.");
        }
        if (crateMoved){
            currentCratePosition = translatePoint(targetObjectPoint, delta);  //stores crate position for undo function
        }
        return keeperMoved;
    }

    /**
     * switch the position of the object and the position of target object
     *
     * @param delta the move of the object
     * @param ObjectPoint current position of the  object to be moved
     * @param object the object to be moved
     */
    private void moveObject(Point delta, Point ObjectPoint, GameObject object) {
        GameObject targetOfObject = currentLevel.getObjectsGrid().getGameObjectAt(translatePoint(ObjectPoint, delta));
        currentLevel.getObjectsGrid().putGameObjectAt(targetOfObject, ObjectPoint);
        currentLevel.getObjectsGrid().putGameObjectAt(object, translatePoint(ObjectPoint, delta));
    }

    /**
     * This method will undo the last move of the keeper.
     * If only keeper moved in the last move, keeper will go back to its previous position and the steps count {@code currentLevelMoveCount}
     * will decrease.
     * If crate also moved in the last move, crate will go back to its previous position.
     *
     * The method can only undo the latest move, it doesn't stores a series of history moves.
     */
    public void undo(){
        if (isGameComplete()) {
            return;
        }

        Point keeperPosition = currentLevel.getKeeperPosition();
        if (isPuppetMode()){
            keeperPosition = currentLevel.getPuppetCratePosition(); //puppet position
        }
        GameObject keeper = currentLevel.getObjectsGrid().getGameObjectAt(keeperPosition);
        Point targetObjectPoint = translatePoint(keeperPosition, returnMove);
        GameObject keeperTarget = currentLevel.getObjectsGrid().getGameObjectAt(targetObjectPoint);

        if (GameEngine.isDebugActive()) {
            printDebugInfo(returnMove);
        }

        boolean keeperMoved = false;
        switch (keeperTarget) {
            case KEEPER:
            case WALL:
                break;

            case FLOOR:
                moveObject(returnMove,keeperPosition, keeper);
                keeperMoved = true;
                break;
            case GATE:
                keeperMoved = true;
                break;
            default:
                logger.severe("The object to be moved was not a recognised GameObject.");
                throw new AssertionError("This should not have happened. Report this problem to the developer.");
        }

        if (crateMoved){
            GameObject crateObject = currentLevel.getObjectsGrid().getGameObjectAt(currentCratePosition);
            moveObject(returnMove,currentCratePosition,crateObject);  //move to previous position
            crateMoved =false;
        }

        if (keeperMoved) {
            keeperPosition.translate((int) returnMove.getX(), (int) returnMove.getY());  //switch back to previous position
            currentLevel.isComplete();
            returnMove = new Point(0,0);
            currentLevelMoveCount--;  //decrease the count
        }

    }

    /**
     * print the debug info, including the state of the level,
     * layout of the level, position of the keeper and the position
     * of the target object.
     *
     * @param  delta move direction
     */
    public void printDebugInfo(Point delta) {
        Point keeperPosition = currentLevel.getKeeperPosition();
        GameObject keeper = currentLevel.getObjectsGrid().getGameObjectAt(keeperPosition);
        Point targetObjectPoint = translatePoint(keeperPosition, delta);
        GameObject keeperTarget = currentLevel.getObjectsGrid().getGameObjectAt(targetObjectPoint);
        System.out.println("Puppet mode open: " + isPuppetMode());
        System.out.println("Current level state:");
        System.out.println(currentLevel.toString());
        System.out.println("Keeper pos: " + keeperPosition);
        System.out.println("Movement source obj: " + keeper);
        System.out.printf("Target object: %s at [%s]\n", keeperTarget, targetObjectPoint);
    }

    /**
     * Save the counts number and the layout of the levels starting from the current level to the
     * end of the Level list to a file.
     *
     * First write the map set name, totalMoveCount and CurrentLevelMoveCount to the beginning
     * of the file. After that, iterate all the levels starting from the current to the end of the Level
     * list {@code levels} and write the layouts of the walls, crates, diamonds and keeper to the file.
     * @param savedFile the file that the data is write to
     * @throws IOException if file not found or error saving
     */
    public void saveGame(File savedFile) throws IOException {
        String savedFilePath = savedFile.getAbsolutePath();
        FileWriter filewriter = new FileWriter(savedFilePath);
        PrintWriter printwriter = new PrintWriter(filewriter);
        printwriter.print("MapSetName: " + getMapSetName() + "\n");
        printwriter.print("TotalMoveCount: " + getTotalMoveCount() + "\n");
        printwriter.print("CurrentLevelMoveCount: " + getCurrentLevelMoveCount() + "\n");
        while (getCurrentLevel() != null) { //while there are levels in the level list
            printwriter.print("LevelName: " + getCurrentLevel().getName() + "\n");
            printwriter.print(getCurrentLevel().toString());  //write the layout to the file
            setCurrentLevel(getNextLevel());
            printwriter.print("\n");
        }
        printwriter.close();
    }

    /**
     * The method loads the game file and save the records of total move count and
     * current level count if the game file contains these data.
     *
     * The method uses a BufferedReader to read the game file line by line.
     * <ul>
     *     <li>If the line contains "MapSetName: " the string after the identifier will be
     *     stored as the map set name</li>
     *     <li>If the line contains "TotalMoveCount: " the string after the identifier will
     *     be converted into int and saved as {@code totalMoveCount}</li>
     *     <li>If the line contains "CurrentLevelMoveCount:" the string after the identifier will
     *     be converted into int and saved as {@code currentLevelMoveCount} </li>
     *     <li>If the line contains "LevelName: " the string after the identifier will be stored
     *     as the level name</li>
     *     <li>If the line has regular expression that contains enum types of {@link GameObject},
     *     the line will be added to a string Level </li>
     *     <li>If the line is null, instantiate a level object with levelName, levelIndex and string level</li>
     * </ul>
     * @param input input stream of game file
     * @return returns a list containing all the levels
     *
     * @see Level
     */
    private List<Level> loadGameFile(InputStream input) {
        List<Level> levels = new ArrayList<>(5);
        //initialize the counts and level index
        int levelIndex = 0;
        totalMoveCount = 0;
        currentLevelMoveCount = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            boolean parsedFirstLevel = false;
            List<String> rawLevel = new ArrayList<>();
            String levelName = "";

            while (true) {
                String line = reader.readLine();

                if (line == null) {  //if the line is null, instantiate a new level object and add to Level list
                    if (rawLevel.size() != 0) {
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                    }
                    break;
                }
                if (line.contains("TotalMoveCount: ")) {
                    //if the line contains the identifier string
                    //store the count number after the string
                    totalMoveCount = Integer.parseInt(line.replace("TotalMoveCount: ", ""));
                    continue;
                }
                if (line.contains("CurrentLevelMoveCount: ")) {
                    //if the line contains the identifier string
                    //store the count number after the string
                    currentLevelMoveCount = Integer.parseInt(line.replace("CurrentLevelMoveCount: ", ""));
                    continue;
                }
                if (line.contains("MapSetName")) {
                    //if the line contains the identifier string
                    //store the mapSetName after the string
                    mapSetName = line.replace("MapSetName: ", "");
                    continue;
                }
                if (line.contains("LevelName")) {
                    //if the line contains the identifier string
                    //store the LevelName after the string
                    if (parsedFirstLevel) {
                        //if the current level is not the first level
                        //once reaching the levelName identifier string
                        //the level before it will be instantiated and add to level list
                        Level parsedLevel = new Level(levelName, ++levelIndex, rawLevel);
                        levels.add(parsedLevel);
                        rawLevel.clear();
                    } else {
                        parsedFirstLevel = true;
                    }

                    levelName = line.replace("LevelName: ", "");
                    continue;
                }

                line = line.trim();
                line = line.toUpperCase();
                if (line.matches(".*W.*W.*")) {
                    rawLevel.add(line);
                }
            }

        } catch (IOException e) {
            logger.severe("Error trying to load the game file: " + e);
        } catch (NullPointerException e) {
            logger.severe("Cannot open the requested file: " + e);
        }

        return levels;
    }

    /**
     * If the method is called in {@link #GameEngine(InputStream)} constructor,
     * it will return the first level in the list by default since there are no levels
     * currently running.
     *
     * If the method is called in {@link #move(Point)}，it is the case that current level
     * is completed and the method will get the next level through index in the level list.
     *
     * If all the levels in the level list is used, then it indicates the game is completed.
     * {@link GameRunningController#reloadGrid()} will exam whether the game is completed
     * and it will start game-end scene if the game is completed.
     * @return return a level object
     */
    public Level getNextLevel() {
        if (currentLevel == null) {
            return levels.get(0);
        }
        int currentLevelIndex = currentLevel.getIndex();
        if (currentLevelIndex < levels.size()) {
            //change get(currentLevelIndex+1) to get(currentLevelIndex) to avoid skipping levels
            return levels.get(currentLevelIndex);
        }
        gameComplete = true;
        return null;
    }

    /**
     * The method resets the current level that the user is playing.
     * It first stores the index of the current level in the Level list.
     * Then reload the game file and get the reset level through (index-1) since
     * the index of the list starts from 0. Set the {@code currentLevelMoveCount}
     * to 0 since the level is loaded and the previous move steps for the reset level
     * will not be counted.
     *
     * @param input input stream of the game file
     */
    public void resetCurrentLevel(InputStream input){
        int currentLevelIndex = currentLevel.getIndex();
        levels = loadGameFile(input);
        currentLevel = levels.get(currentLevelIndex-1);
        currentLevelMoveCount = 0;
    }

    /**
     * set the GameEngine debug state to the opposite one.
     * The GameEngine only has two states, debug = true or debug = false
     */
    public void toggleDebug() {
        debug = !debug;
    }

    /**
     * Save the move count of the current level to a temporary file.
     * It will create a temp file if there doesn't exists one.
     * The file is located in /resources/records folder.
     *
     * @throws IOException if save record to file fails
     */
    private void saveCurrentLevelRecord() throws IOException {
        String dir= System.getProperty("user.dir") + "/src/main/resources/records/temp.txt".replace("/",System.getProperty("file.separator"));
        Path recordFilePath = Paths.get(dir);
        if (!Files.exists(recordFilePath)){
            try{
                Files.createFile(recordFilePath);   //create a file. throws an FileAlreadyExistsException if the file exists
            }catch (Exception e){logger.warning("Temp file already exists");}
        }

        String record = currentLevel.getName() + ": " + currentLevelMoveCount;  //format the record message
        FileWriter filewriter = new FileWriter(dir,true);
        PrintWriter printwriter = new PrintWriter(filewriter);
        printwriter.print(record + "\n");
        printwriter.close();
    }

    /**
     * The method used for skipping the current level. It is called by
     * {@link GameRunningController#setEventFilter()}. When using press 'ctrl + z',
     * the method will be called and allow the user pass the current level with a
     * one step cost.
     */
    public void skipLevel(){
        currentLevelMoveCount++;
        try{
            saveCurrentLevelRecord();
        }catch (IOException e){
            logger.warning("Error storing level record");
        }

        totalMoveCount +=currentLevelMoveCount;
        currentLevelMoveCount = 0;
        currentLevel = getNextLevel();
    }


    /**
     * Calculate the move of Point class.The method get two point classes as
     * parameters. The first one is the current position of the object, the
     * second one is the delta which the object will move. The return of the
     * method is the destination point of the object after move.
     *
     * @param sourceLocation the current location of the object
     * @param delta the direction where the object will move
     * @return  return the destination of the object
     *
     * @see Point#translate(int, int)
     */
    static Point translatePoint(Point sourceLocation, Point delta) {
        Point translatedPoint = new Point(sourceLocation);
        translatedPoint.translate((int) delta.getX(), (int) delta.getY());
        return translatedPoint;
    }

    /**
     * return true if game completed, false if not
     * @return true if game completed, false if not
     */
    public boolean isGameComplete() {
        return gameComplete;
    }

    /************* getter and setter methods **************/

    /**
     * return the current level pointer
     * @return the current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Set the current level to the input level.
     * @param targetLevel the input level
     *
     */
    public void  setCurrentLevel(Level targetLevel) {
         currentLevel = targetLevel;
    }

    /**
     * return the map set name
     * @return the map set name
     */
    public String getMapSetName(){
        return mapSetName;
    }

    /**
     * return current level move count
     * @return return the current level move count
     */
    public int getCurrentLevelMoveCount(){
        return  currentLevelMoveCount;
    }

    /**
     * return the total move count
     * @return the total move count starts from the beginning
     */
    public int getTotalMoveCount(){
        return  totalMoveCount;
    }

    /**
     * return true if game is in puppet mode
     * @return true if game is in puppet mode, false otherwise.
     */
    public boolean isPuppetMode() {
        return puppetMode;
    }

    /**
     * set the bool value of {@code puppetMode}
     * @param puppetMode boolean value to set puppetMode flag variable
     */
    public void setPuppetMode(boolean puppetMode) {
        this.puppetMode = puppetMode;
    }

    /**
     * return true if in through-wall mode, false if not.
     * @return true if in through-wall mode, false if not
     */
    public boolean isThroughWallMode() {
        return throughWallMode;
    }

    /**
     * set the through-wall mode
     * @param throughWallMode boolean value to set the flag variable
     */
    public void setThroughWallMode(boolean throughWallMode) {
        this.throughWallMode = throughWallMode;
    }

    /**
     * return ture if game is paused,false otherwise
     * @return ture if game is paused, false otherwise
     */
    public boolean isPause() {
        return isPause;
    }

    /**
     * set pause flag variable
     * @param pause boolean value to set the flag variable
     */
    public void setPause(boolean pause) {
        isPause = pause;
    }
}