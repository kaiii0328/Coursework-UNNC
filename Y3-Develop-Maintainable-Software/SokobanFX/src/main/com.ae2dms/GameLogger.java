package main.com.ae2dms;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * The {@code GameLogger} is responsible for logging import message including
 * information, warning and severe damage information during game is running.
 *
 * @see Logger
 */
public class GameLogger extends Logger {

    private static Logger logger = Logger.getLogger("GameLogger");
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Calendar calendar = Calendar.getInstance();

    /**
     * initialize the GameLogger and create log files in logs/
     * set the formatter to log message
     *
     * @throws IOException if save file failed
     */
    public GameLogger() throws IOException {
        super("com.aes2dms.sokoban", null);
        File directory = new File(System.getProperty("user.dir") + "/" + "logs");
        directory.mkdirs();

        FileHandler fh = new FileHandler(directory + "/" + GameEngine.GAME_NAME + ".log",true);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }

    /**
     * add time to the input string, create the formatted string
     * @param message the content
     * @return return a formatted string
     */
    private String createFormattedMessage(String message) {
        return dateFormat.format(calendar.getTime()) + " -- " + message;
    }

    /**
     * log an info message
     * @param message input message
     *  
     * @see Logger#info(String) 
     */
    public void info(String message) {
        logger.info(createFormattedMessage(message));
    }

    /**
     * log a warning message
     * @param message input message
     *                
     * @see Logger#warning(String) 
     */
    public void warning(String message) {
        logger.warning(createFormattedMessage(message));
    }

    /**
     * log a severe message
     * @param message input message
     *                
     * @see Logger#severe(String)
     */
    public void severe(String message) {
        logger.severe(createFormattedMessage(message));
    }
}