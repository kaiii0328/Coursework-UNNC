package main.com.ae2dms;

import javafx.scene.input.KeyCode;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The {@code MovementFactory} class stores all the predefined actions for specific
 * keyboard input. The Keycode-action mapping is stored in a hash map  and a {@link #getMovement(KeyCode)}
 * method is used to get the corresponding actions.
 */
public class MovementFactory {
    /**
     * map stores keycode-point pairs
     */
    static Map<KeyCode, Point> moveMap = new HashMap<>();
    //insert actions into the hashmap
    //both "WASD" and "UP/DOWN/RIGHT/LEFT" can control the keeper
    static {
        moveMap.put(KeyCode.W,new Point(-1,0));
        moveMap.put(KeyCode.UP,new Point(-1,0));
        moveMap.put(KeyCode.D,new Point(0, 1));
        moveMap.put(KeyCode.RIGHT,new Point(0, 1));
        moveMap.put(KeyCode.DOWN,new Point(1,0));
        moveMap.put(KeyCode.S,new Point(1,0));
        moveMap.put(KeyCode.A,new Point(0,-1));
        moveMap.put(KeyCode.LEFT,new Point(0,-1));
    }

    /**
     * get the corresponding values of the input key.
     * If the keycode is not defined, return null. Otherwise, return
     * the move.
     *
     * @param code keyboard input
     * @return return null if the keyboard input is not defined,return the actions if the input is find.
     */
    public static Optional<Point> getMovement(KeyCode code){
        return Optional.ofNullable(moveMap.get(code));
    }

}
