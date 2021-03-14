package main.com.ae2dms;

import java.awt.*;
import java.util.Iterator;

/**
 * The {@code GameGrid} contains methods for game object operations in game grid.
 * Methods like {@link #putGameObjectAt(GameObject, int, int)} and {@link #getGameObjectAt(Point)}
 * are used for setting and putting the game objects to corresponding places in game grid
 *
 * @see Level#objectsGrid
 */
public class GameGrid implements Iterable,IMessage {
    /**
     * width of the map
     */
    final int COLUMNS;
    /**
     * height of the map
     */
    final int ROWS;

    /**
     * game objects grid
     */
    private GameObject[][] gameObjects;

    /**
     * initialize the {@code gameObjects array}
     * @param columns width of the game map
     * @param rows   height of the game map
     */
    public GameGrid(int columns, int rows) {
        COLUMNS = columns;
        ROWS = rows;

        // Initialize the array
        gameObjects = new GameObject[COLUMNS][ROWS];
    }

    /**
     *
     * @return return a {@code Dimension} object with same size of the game map
     */
    public Dimension getDimension() {
        return new Dimension(COLUMNS, ROWS);
    }

    /**
     * get the game object on the game grid with input of a source position and an offset
     * @param source position of the object on game grid
     * @param delta  offset from the map
     * @return a Game object at the target point
     */
    GameObject getTargetFromSource(Point source, Point delta) {
        return getGameObjectAt(GameEngine.translatePoint(source, delta));
    }

    /**
     * Similar to {@link #getTargetFromSource(Point, Point)},{@link #getGameObjectAt(Point)}
     * @param col coordinate y
     * @param row coordinate x
     * @return a GameObject at the target point
     * @throws ArrayIndexOutOfBoundsException if the coordinate is out of game map
     */
    public GameObject getGameObjectAt(int col, int row) throws ArrayIndexOutOfBoundsException {
        if (isPointOutOfBounds(col, row)) { //check if the coordinate is within scope
            if (GameEngine.isDebugActive()) {
                printDebugInfo();
            }
            throw new ArrayIndexOutOfBoundsException("The point [" + col + ":" + row + "] is outside the map.");
        }

        return gameObjects[col][row];
    }

    /**
     * Similar to {@link #getTargetFromSource(Point, Point)},{@link #getTargetFromSource(Point, Point)}
     * @param p point on the game grid
     * @return a Game object at the target point
     */
    public GameObject getGameObjectAt(Point p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null.");
        }

        return getGameObjectAt((int) p.getX(), (int) p.getY());
    }

    /**
     * put the game object at the specified position on the game grid
     * @param gameObject the game object to be put
     * @param x  coordinate x on game grid
     * @param y  coordinate y on game grid
     * @return  ture if the gameObject is set, false if not
     */
    public boolean putGameObjectAt(GameObject gameObject, int x, int y) {
        if (isPointOutOfBounds(x, y)) {
            return false;
        }

        gameObjects[x][y] = gameObject;
        return gameObjects[x][y] == gameObject;
    }

    /**
     * put the game object at the specified position on the game grid
     * Similar to {@link #putGameObjectAt(GameObject, int, int)}
     * @param gameObject the game object to be put
     * @param p point on game grid
     * @return ture if the gameObject is set, false if not
     */
    public boolean putGameObjectAt(GameObject gameObject, Point p) {
        return p != null && putGameObjectAt(gameObject, (int) p.getX(), (int) p.getY());
    }

    /**
     * check if the given coordinate is within map or not
     * @param x coordinate x
     * @param y coordinate y
     * @return true if the point is within map,false else
     */
    private boolean isPointOutOfBounds(int x, int y) {
        return (x < 0 || y < 0 || x >= COLUMNS || y >= ROWS);
    }

    /**
     * condense the two-dimension {@code gameObjects} array in a string for print
     * @return a string containing the contents of the grid
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(gameObjects.length);

        for (GameObject[] gameObject : gameObjects) {
            for (GameObject aGameObject : gameObject) {
                if (aGameObject == null) {
                    aGameObject = GameObject.DEBUG_OBJECT;
                }
                sb.append(aGameObject.getCharSymbol());
            }

            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * print debug info
     */
    @Override
    public void printDebugInfo() {
        System.out.printf("Trying to get null GameObject from COL: %d  ROW: %d", getDimension().width, getDimension().height);
    }

    /**
     *
     * @return an GameObject Iterator
     */
    @Override
    public Iterator<GameObject> iterator() {
        return new GridIterator();
    }

    /**
     * the iterator of gameGird
     */
    public class GridIterator implements Iterator<GameObject> {
        int row = 0;
        int column = 0;

        /**
         *
         * @return true if it has object within scope
         */
        @Override
        public boolean hasNext() {
            return !(row == ROWS && column == COLUMNS);
        }

        /**
         *
         * @return the game object in the next column
         */
        @Override
        public GameObject next() {
            if (column >= COLUMNS) {
                column = 0;
                row++;
            }
            return getGameObjectAt(column++, row);
        }
    }
}