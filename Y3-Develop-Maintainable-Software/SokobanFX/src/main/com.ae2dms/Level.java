package main.com.ae2dms;

import main.controller.GameRunningController;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The {@code Level} class is about controlling each levels.The class has two {@link GameGrid} components,
 * one is diamond grid and the other is a grid containing objects other than diamonds. This class monitors the
 * positions of all the objects on the map by changing objects' positions on corresponding grids and check whether
 * the level is completed
 */
public final class Level implements Iterable<GameObject>,IMessage{
    /**
     * object grid without diamond
     */
    private final GameGrid objectsGrid;
    /**
     * diamond grid
     */
    private final GameGrid diamondsGrid;
    /**
     * level name
     */
    private final String name;
    /**
     * level index
     */
    private final int index;
    /**
     * number of diamonds in the level
     */
    private int numberOfDiamonds = 0;
    /**
     * keeper position
     */
    private Point keeperPosition = new Point(0, 0);
    /**
     * starting point of the keeper
     */
    private Point iniKeeperPosition = new Point(0, 0);
    /**
     * puppet crate position
     */
    private Point puppetCratePosition = new Point(0,0);
    /**
     * gate position
     */
    private Point firstGatePosition = new Point(0,0);
    /**
     * gate position
     */
    private Point secondGatePosition = new Point(0,0);

    /**
     * constructor for level
     * @param levelName level name
     * @param levelIndex level index
     * @param raw_level objects layout in the level
     */
    public Level(String levelName, int levelIndex, List<String> raw_level) {
        name = levelName;
        index = levelIndex;

        int rows = raw_level.size();
        int columns = raw_level.get(0).trim().length();

        objectsGrid = new GameGrid(rows, columns);
        diamondsGrid = new GameGrid(rows, columns);

        if (GameEngine.isDebugActive()) {
            printDebugInfo();
        }
        initLevel(raw_level, rows, columns);
    }

    /**
     * print the debug info
     */
    @Override
    public void printDebugInfo() {
        System.out.printf("[ADDING LEVEL] LEVEL [%d]: %s\n", getIndex(), getName());
    }

    /**
     * init the level ,put the game object into the game gird with the coordinate(x,y)
     * If the game object is diamond, it will be added into {@code diamondobjectgrid}.
     * If the gmae object are other types, it will be added into {@code objectsGrid}.
     *
     * @param raw_level the string level, containing the layouts
     * @param rows      the rows of the map
     * @param columns   the columns of the map
     *
     * @see GameEngine#isKeeperMoved(Point, Point, GameObject)
     * @see #toString()
     */
    private void initLevel(List<String> raw_level, int rows, int columns) {
        // raw_level.size() -> rows; raw_level.get(row).length()-> columns
        //Wangkai JIN 2020.11.12
        boolean findFirstCrate = false;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                GameObject currentObj = GameObject.fromChar(raw_level.get(row).charAt(col));
                if (currentObj == GameObject.DIAMOND) {
                    numberOfDiamonds++;
                    diamondsGrid.putGameObjectAt(currentObj, row, col);
                    currentObj = GameObject.FLOOR;
                } else if (currentObj == GameObject.KEEPER) {
                    setKeeperPosition(new Point(row, col));
                    iniKeeperPosition = new Point(row,col); //the start point. used if keeper enters trap.
                    currentObj = GameObject.FLOOR;
                }else if (currentObj == GameObject.GATE){
                    if (getFirstGatePosition().x == 0 && getFirstGatePosition().y == 0){
                        firstGatePosition = new Point(row,col);  //remember the location of black holes
                    }else{
                        secondGatePosition = new Point(row,col); //remember the location of black holes
                    }
                    currentObj = GameObject.GATE;
                }
                else if (currentObj == GameObject.CRATE_ON_DIAMOND){
                    numberOfDiamonds ++;
                    diamondsGrid.putGameObjectAt(GameObject.DIAMOND,row,col);
                    currentObj = GameObject.CRATE;
                }
                else if(currentObj == GameObject.KEEPER_ON_DIAMOND){
                    numberOfDiamonds ++;
                    diamondsGrid.putGameObjectAt(GameObject.DIAMOND,row,col);
                    currentObj = GameObject.KEEPER;   //separate keeper and diamond
                }
                else if (currentObj == GameObject.KEEPER_ON_WALL){
                    setKeeperPosition(new Point(row, col));
                    currentObj = GameObject.WALL;  //separate keeper and wall
                }
                else if (currentObj == GameObject.CRATE){
                    if (!findFirstCrate){
                        puppetCratePosition = new Point(row,col);  //find first puppet crate
                        findFirstCrate = true;
                    }

                }

                getObjectsGrid().putGameObjectAt(currentObj, row, col);
                //delete used assignment curTil = null
            }
        }
    }

    /**
     * iterate through the {@code objectGrid} and {@code diamondsGrid} and check whether the
     * crates' positions are same with the diamonds' positions,if true, then the game is completed.
     *
     * @return true if all the crates' positions are inline with the diamonds' positions
     */
    public boolean isComplete() {
        int cratedDiamondsCount = 0;
        boolean puppetInPosition = false;
        for (int row = 0; row < getObjectsGrid().ROWS; row++) {
            for (int col = 0; col < getObjectsGrid().COLUMNS; col++) {
                if (getObjectsGrid().getGameObjectAt(col, row) == GameObject.CRATE && diamondsGrid.getGameObjectAt(col, row) == GameObject.DIAMOND) {
                    cratedDiamondsCount++;
                    if (puppetCratePosition.getX() == col && puppetCratePosition.getY() == row){
                        puppetInPosition = true;
                    }
                }
            }
        }
        for (int row = 0; row < getObjectsGrid().ROWS; row++) {
            for (int col = 0; col < getObjectsGrid().COLUMNS; col++) {
                if (getObjectsGrid().getGameObjectAt(col, row) == GameObject.CRATE && diamondsGrid.getGameObjectAt(col, row) != GameObject.DIAMOND) {
                    if (puppetInPosition){
                        puppetCratePosition = new Point(col,row); //set a new puppet crate
                    }
                }
            }
        }

        //teleport the keeper if keeper is at either of the gate.
        if (keeperPosition.equals(firstGatePosition)){
            setKeeperPosition(new Point(secondGatePosition));
        }else if (keeperPosition.equals(secondGatePosition)){
            setKeeperPosition(new Point(firstGatePosition));
        }

        return cratedDiamondsCount >= numberOfDiamonds;
    }

    /**
     * get the level name
     * @return the name of the level
     */
    public String getName() {
        return name;
    }

    /**
     * get the level index in the level list
     * @return return the index (starts from 1)
     */
    public int getIndex() {
        return index;
    }

    /**
     * get the keeper position
     * @return return the keeper position
     */
    Point getKeeperPosition() {
        return keeperPosition;
    }

    /**
     * get the game object at the given point
     * @param source source point at the game grid
     * @param delta  offset from the source point
     * @return return the game object at the destination point
     *
     * @see GameGrid#getTargetFromSource(Point, Point)
     */
    GameObject getTargetObject(Point source, Point delta) {
        return getObjectsGrid().getTargetFromSource(source, delta);
    }

    /**
     * get the object grid
     * @return return the {@code objectGrid}
     */
    public GameGrid getObjectsGrid() {
        return objectsGrid;
    }

    /**
     * Combine the two game grids: {@code objectGrid} and {@code diamondGrid} into one string for print and write.
     * First get the string format of both grids by using {@link GameGrid#toString()}.
     * Next, get all the indexes of the diamonds in the {@code diamondGrid} string.
     * Then, replacing all the char at these extracted indexes in the {@code objectGrid} string with 'D',if the object
     * on target position is not crate. If it is crate_on_diamond situation, the target object will be set as 'o',crate_on_diamond.
     * The same logic for keeper_on_wall and keeper_on_diamond situations
     *
     * @return return a string contains the layouts of crates,walls, floors,diamonds and keeper of current level.
     */
    @Override
    public String toString() {
        ArrayList<Integer> diamondIndexList = new ArrayList<>();
        StringBuilder objectGridString = new StringBuilder(getObjectsGrid().toString());
        String diamondGridString = diamondsGrid.toString();
        for (int i =0;i<diamondGridString.length();i++){
            if(diamondGridString.charAt(i) == GameObject.DIAMOND.getCharSymbol())
                diamondIndexList.add(i);
        }
        for (Integer integer : diamondIndexList) {
            if (objectGridString.charAt(integer) != GameObject.CRATE.getCharSymbol()) //if it is not crate
                objectGridString.setCharAt(integer, GameObject.DIAMOND.getCharSymbol());
            else{
                objectGridString.setCharAt(integer,GameObject.CRATE_ON_DIAMOND.getCharSymbol()); //set crate_on_diamond
            }
        }
        int keeperIndex = (keeperPosition.x * (objectsGrid.COLUMNS+1)) + keeperPosition.y;
        if (objectGridString.charAt(keeperIndex) != GameObject.DIAMOND.getCharSymbol() &&  //if it is not keeper_on_diamond
                objectGridString.charAt(keeperIndex) != GameObject.WALL.getCharSymbol())   //or keeper_on_wall situations
            objectGridString.setCharAt(keeperIndex, GameObject.KEEPER.getCharSymbol());
        else if (objectGridString.charAt(keeperIndex) == GameObject.DIAMOND.getCharSymbol()){
            objectGridString.setCharAt(keeperIndex, GameObject.KEEPER_ON_DIAMOND.getCharSymbol());
        }
        else if (objectGridString.charAt(keeperIndex) == GameObject.WALL.getCharSymbol()){
            objectGridString.setCharAt(keeperIndex, GameObject.KEEPER_ON_WALL.getCharSymbol());
        }
        return objectGridString.toString() ;
    }

    /**
     * instantiate a new Level Iterator
     * @return return a new iterator
     */
    @Override
    public Iterator<GameObject> iterator() {
        return new LevelIterator();
    }

    /**
     * return puppetCratePosition
     * @return puppetCratePosition
     */
    public Point getPuppetCratePosition() {
        return puppetCratePosition;
    }

    /**
     * get first gate position
     * @return firstGatePosition
     */
    public Point getFirstGatePosition() {
        return firstGatePosition;
    }

    /**
     * get starting point of keeper at current level
     * @return starting point
     */
    public Point getIniKeeperPosition() {
        return iniKeeperPosition;
    }

    /**
     * set the keeper position
     * @param keeperPosition the target position
     */
    public void setKeeperPosition(Point keeperPosition) {
        this.keeperPosition = keeperPosition;
    }

    /**
     * the level iterator which is used to traverse the grid.
     * The traversal is in {@link GameRunningController#reloadGrid()} which converts the game object into
     * graphic object and adds to the {@code gameGrid}
     */
    public class LevelIterator implements Iterator<GameObject> {
        int column = 0;
        int row = 0;

        /**
         * check whether there are objects remaining
         * @return true if the iterator has objects,false else
         */
        @Override
        public boolean hasNext() {
            return !(row == getObjectsGrid().ROWS - 1 && column == getObjectsGrid().COLUMNS);
        }

        /**
         * get the next object
         * @return a game object in the following cell of the grid
         */
        @Override
        public GameObject next() {
            //improve the layout of function next() --- Wangkai JIN 2020.11.1
            if (column >= getObjectsGrid().COLUMNS) {
                column = 0;
                row++;
            }
            GameObject object = getObjectsGrid().getGameObjectAt(column, row);
            GameObject diamond = diamondsGrid.getGameObjectAt(column, row);
            GameObject keeper = GameObject.fromChar('S');
            GameObject retObj = object;
            column++;
            if (diamond == GameObject.DIAMOND) {
                if (object == GameObject.CRATE) {
                    retObj = GameObject.CRATE_ON_DIAMOND;  //show crate_on_diamond symbol
                } else if (object == GameObject.FLOOR) {
                    retObj = diamond;  // show diamond symbol only
                } //remove redundant assignment for retObj
            }

            if (column-1 == keeperPosition.x && row == keeperPosition.y && object != GameObject.CRATE){
                retObj = keeper;
            }else if (column-1 == keeperPosition.x && row == keeperPosition.y && object == GameObject.GATE)
                retObj = GameObject.KEEPER_ON_GATE;
            return retObj;
        }
        /**
         * get current position during iteration
         * @return the current iterating position
         */
        public Point getCurrentPosition() {
            return new Point(column, row);
        }
    }
}