package main.com.ae2dms;

import javafx.scene.input.KeyCode;
import main.controller.GameStartController;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * unit test for GameEngine
 */
public class GameEngineTest {

    private GameEngine gameEngine ;

    /**
     * initialize game engine
     */
    @Before
    public void initialize(){
        String filepath = "main/resources/level/SampleGame.skb";
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filepath);
        gameEngine = new GameEngine(in);
    }

    /**
     * test {@link GameEngine#isDebugActive()}
     */
    @Test
    public void isDebugActive() {
        gameEngine.toggleDebug();
        assertEquals(false,GameEngine.isDebugActive());
    }

    /**
     * test {@link GameEngine#handleKey(KeyCode)}}, whether the expected direction is given
     */
    @Test
    public void handleKey() {
        Point keeperPosition = gameEngine.getCurrentLevel().getKeeperPosition();
        Point keeperPositionPrevious = new Point(keeperPosition);
        gameEngine.handleKey(KeyCode.LEFT);
        Point keeperPositionAfter = gameEngine.getCurrentLevel().getKeeperPosition();
        assertEquals(keeperPositionPrevious.getY(), (keeperPositionAfter.getY()+1), 0.1);
        assertEquals(keeperPositionPrevious.getX(), (keeperPositionAfter.getX()), 0.1);

        keeperPosition = gameEngine.getCurrentLevel().getKeeperPosition();
        keeperPositionPrevious = new Point(keeperPosition);
        gameEngine.handleKey(KeyCode.UP);
        keeperPositionAfter = gameEngine.getCurrentLevel().getKeeperPosition();
        assertEquals(keeperPositionPrevious.getY(), (keeperPositionAfter.getY()), 0.1);
        assertEquals(keeperPositionPrevious.getX(), (keeperPositionAfter.getX()+1), 0.1);

        gameEngine.handleKey(KeyCode.LEFT);
        keeperPosition = gameEngine.getCurrentLevel().getKeeperPosition();
        keeperPositionPrevious = new Point(keeperPosition);
        gameEngine.handleKey(KeyCode.RIGHT);
        keeperPositionAfter = gameEngine.getCurrentLevel().getKeeperPosition();
        assertEquals(keeperPositionPrevious.getY(), (keeperPositionAfter.getY()-1), 0.1);
        assertEquals(keeperPositionPrevious.getX(), (keeperPositionAfter.getX()), 0.1);

        keeperPosition = gameEngine.getCurrentLevel().getKeeperPosition();
        keeperPositionPrevious = new Point(keeperPosition);
        gameEngine.handleKey(KeyCode.DOWN);
        keeperPositionAfter = gameEngine.getCurrentLevel().getKeeperPosition();
        assertEquals(keeperPositionPrevious.getY(), (keeperPositionAfter.getY()), 0.1);
        assertEquals(keeperPositionPrevious.getX(), (keeperPositionAfter.getX()-1), 0.1);


    }

    /**
     * test {@link GameEngine#undo()}.
     */
    @Test
    public void undo() {
        gameEngine.handleKey(KeyCode.LEFT);
        Point keeperPosition = gameEngine.getCurrentLevel().getKeeperPosition();
        Point keeperPrevious = new Point(keeperPosition);
        gameEngine.undo();
        keeperPosition = gameEngine.getCurrentLevel().getKeeperPosition();
        assertEquals(keeperPrevious.getY(), (keeperPosition.getY()-1), 0.1);
        assertEquals(keeperPrevious.getX(), (keeperPosition.getX()), 0.1);

    }

    /**
     * test {@link GameEngine#saveGame(File)}
     * @throws IOException if game file fails loading
     */
    @Test
    public void saveGame() throws IOException{
        String dir= System.getProperty("user.dir") + "/src/main/resources/level/test.skb".replace("/",System.getProperty("file.separator"));
        try{
            Files.createFile(Paths.get(dir));
        }catch (IOException e){
            e.printStackTrace();
        }
        gameEngine.saveGame(Paths.get(dir).toFile());
        assertTrue(Files.exists(Path.of(dir)));
        Files.delete(Paths.get(dir));

    }

    /**
     * test {@link GameEngine#getNextLevel()}
     */
    @Test
    public void getNextLevel() {
        int levelIndex = gameEngine.getCurrentLevel().getIndex();
        int nextLevelIndex = gameEngine.getNextLevel().getIndex();
        assertEquals(levelIndex,nextLevelIndex-1);
    }

    /**
     * test {@link GameEngine#resetCurrentLevel(InputStream)}
     */
    @Test
    public void resetCurrentLevel() {
        Point keeperPosition = gameEngine.getCurrentLevel().getKeeperPosition();
        Point keeperPositionPrevious = new Point(keeperPosition);
        gameEngine.handleKey(KeyCode.LEFT);
        gameEngine.handleKey(KeyCode.UP);

        String filepath = "main/resources/level/SampleGame.skb";
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(filepath);
        gameEngine.resetCurrentLevel(in);

        Point keeperPositionAfter = gameEngine.getCurrentLevel().getKeeperPosition();
        assertEquals(keeperPositionPrevious.getX(),keeperPositionAfter.getX(),0.5);
        assertEquals(keeperPositionPrevious.getY(),keeperPositionAfter.getY(),0.5);
    }

    /**
     * test {@link GameEngine#toggleDebug()}
     */
    @Test
    public void toggleDebug() {
        assertFalse(GameEngine.isDebugActive());
        gameEngine.toggleDebug();
        assertTrue(GameEngine.isDebugActive());
    }

    /**
     * test {@link GameEngine#skipLevel()}
     */
    @Test
    public void skipLevel() {
        int prevIndex = gameEngine.getCurrentLevel().getIndex();
        gameEngine.skipLevel();
        int afterIndex = gameEngine.getCurrentLevel().getIndex();
        assertSame(prevIndex,afterIndex-1);
    }

    /**
     * test {@link GameEngine#translatePoint(Point, Point)}
     */
    @Test
    public void translatePoint() {
        Point source = new Point(0,0);
        Point delta = new Point(1,0);
        Point destination = GameEngine.translatePoint(source,delta);
        assertEquals(source.getY(),destination.getY(),0.1);
        assertEquals(source.getX(),destination.getX()-1,0.1);
    }

    /**
     * test {@link GameEngine#isGameComplete()}
     */
    @Test
    public void isGameComplete() {
        assertFalse(gameEngine.isGameComplete());
        while (gameEngine.getCurrentLevel() != null){
            System.out.println("cur index " + gameEngine.getCurrentLevel().getIndex());
            gameEngine.setCurrentLevel(gameEngine.getNextLevel());
        }
        assertTrue(gameEngine.isGameComplete());
    }

    /**
     * test {@link GameEngine#getCurrentLevel()}
     */
    @Test
    public void getCurrentLevelMoveCount() {
        gameEngine.handleKey(KeyCode.UP);
        assertTrue(gameEngine.getCurrentLevelMoveCount() == 0);

        gameEngine.handleKey(KeyCode.LEFT);
        gameEngine.handleKey(KeyCode.UP);
        assertTrue(gameEngine.getCurrentLevelMoveCount() ==2);

    }

    /**
     * test {@link GameEngine#getTotalMoveCount()}
     */
    @Test
    public void getTotalMoveCount() {
        gameEngine.handleKey(KeyCode.LEFT);
        gameEngine.handleKey(KeyCode.UP);
        gameEngine.skipLevel(); //skip level by 1 step
        assertEquals(gameEngine.getTotalMoveCount(),3);
    }

    /**
     * test {@link GameEngine#isPuppetMode()}
     */
    @Test
    public void isPuppetMode() {
        assertEquals(false,gameEngine.isPuppetMode());
        gameEngine.setPuppetMode(true);
        assertEquals(true,gameEngine.isPuppetMode());
    }

    /**
     * test {@link GameEngine#setPuppetMode(boolean)}
     */
    @Test
    public void setPuppetMode() {
        assertEquals(false,gameEngine.isPuppetMode());
        gameEngine.setPuppetMode(true);
        assertEquals(true,gameEngine.isPuppetMode());
    }

    /**
     * test {@link GameEngine#isThroughWallMode()}
     */
    @Test
    public void isThroughWallMode() {
        assertEquals(false,gameEngine.isThroughWallMode());
    }

    /**
     * test {@link GameEngine#setThroughWallMode(boolean)}
     */
    @Test
    public void setThroughWallMode() {
        assertEquals(false,gameEngine.isThroughWallMode());
        gameEngine.setThroughWallMode(true);
        assertEquals(true,gameEngine.isThroughWallMode());
    }
}