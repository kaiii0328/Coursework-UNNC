package main.com.ae2dms;

import javafx.scene.shape.HLineTo;
import org.junit.Test;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * unit test for {@code GameLogger}
 */
public class GameLoggerTest {
    private GameLogger gameLogger;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Calendar calendar = Calendar.getInstance();

    public GameLoggerTest() throws IOException {
        gameLogger = new GameLogger();
    }

    /**
     * test {@link GameLogger#info(String)}
     */
    @Test
    public void info() {
        String message = "test info";
        gameLogger.info(message);
        String expected = dateFormat.format(calendar.getTime()) +" -- " + message;
        File directory = new File(System.getProperty("user.dir") + "/" + "logs"+"/SokobanFX.log");
        try(BufferedReader reader = new BufferedReader(new FileReader(directory))){
            String line ;
            String lastLine = "";
            while((line = reader.readLine()) != null){
                lastLine = line;
            }
            assertTrue(lastLine.contains(expected));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * test {@link GameLogger#warning(String)}
     */
    @Test
    public void warning() {
        String message = "test info";
        gameLogger.warning(message);
        String expected = dateFormat.format(calendar.getTime()) +" -- " + message;
        File directory = new File(System.getProperty("user.dir") + "/" + "logs"+"/SokobanFX.log");
        try(BufferedReader reader = new BufferedReader(new FileReader(directory))){
            String line ;
            String lastLine = "";
            while((line = reader.readLine()) != null){
                lastLine = line;
            }
            assertTrue(lastLine.contains(expected));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * test {@link GameLogger#severe(String)}
     */
    @Test
    public void severe() {
        String message = "test info";
        gameLogger.severe(message);
        String expected = dateFormat.format(calendar.getTime()) +" -- " + message;
        File directory = new File(System.getProperty("user.dir") + "/" + "logs"+"/SokobanFX.log");
        try(BufferedReader reader = new BufferedReader(new FileReader(directory))){
            String line ;
            String lastLine = "";
            while((line = reader.readLine()) != null){
                lastLine = line;
            }
            assertTrue(lastLine.contains(expected));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}