package main.com.ae2dms;

import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

/**
 * unit test for {@code GameGrid}
 */
public class GameGridTest {
    private int row = 2;
    private int col = 2;
    private GameGrid gameGrid = new GameGrid(col,row);

    /**
     * test {@link GameGrid#getDimension()}
     */
    @Test
    public void getDimension() {
        Dimension dimension = gameGrid.getDimension();
        assertTrue(dimension.getHeight() == row);
        assertTrue(dimension.getWidth() == col);

    }

    /**
     * test {@link GameGrid#getTargetFromSource(Point, Point)}
     */
    @Test
    public void getTargetFromSource() {
        GameObject crate = GameObject.fromChar('C');
        Point point = new Point(1,0);
        Point source = new Point(0,0);
        gameGrid.putGameObjectAt(crate,point);
        assertTrue(gameGrid.getTargetFromSource(source,point).getCharSymbol() == 'C');
    }

    /**
     * test {@link GameGrid#putGameObjectAt(GameObject, Point)}
     */
    @Test
    public void putGameObjectAt() {
        GameObject crate = GameObject.fromChar('C');
        Point point = new Point(0,0);
        gameGrid.putGameObjectAt(crate,point);
        assertTrue(gameGrid.getGameObjectAt(point).getCharSymbol() == 'C');

        gameGrid.putGameObjectAt(crate,1,0);
        assertTrue(gameGrid.getGameObjectAt(1,0).getCharSymbol() == 'C');
    }

    /**
     * test {@link GameGrid#toString()}
     */
    @Test
    public void testToString() {
        GameObject wall = GameObject.fromChar('W');
        gameGrid.putGameObjectAt(wall,1,0);
        gameGrid.putGameObjectAt(wall,1,1);
        gameGrid.putGameObjectAt(wall,0,0);
        gameGrid.putGameObjectAt(wall,0,1);
        String gameGridString = gameGrid.toString();
        String expected = "WW\nWW";
        assertTrue(gameGridString.toString().compareTo(expected) == 1);
    }

    /**
     * test {@link GameGrid#iterator()}
     */
    @Test
    public  void  testIterator(){
        GameGrid.GridIterator iterator = (GameGrid.GridIterator) gameGrid.iterator();
        assertTrue(iterator.hasNext());
        GameObject wall = GameObject.fromChar('W');
        gameGrid.putGameObjectAt(wall,1,0);
        gameGrid.putGameObjectAt(wall,1,1);
        gameGrid.putGameObjectAt(wall,0,0);
        gameGrid.putGameObjectAt(wall,0,1);
        assertTrue(iterator.next().getCharSymbol() == 'W');
    }

}