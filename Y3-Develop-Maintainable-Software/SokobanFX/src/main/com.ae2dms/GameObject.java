package main.com.ae2dms;

/**
 * Enum types of GameObject.
 */
public enum GameObject {
    /**
     * game object gate(for transporting keeper)
     */
    GATE('G'),
    /**
     * game object keeper_on_gate
     */
    KEEPER_ON_GATE('K'),
    /**
     * game object wall
     */
    WALL('W'),
    /**
     * game object floor
     */
    FLOOR(' '),
    /**
     * game object crate
     */
    CRATE('C'),
    /**
     * game object diamond
     */
    DIAMOND('D'),
    /**
     * game object keeper
     */
    KEEPER('S'),
    /**
     * game object trap
     */
    TRAP('T'),
    /**
     * game object keeper_on_diamond
     */
    KEEPER_ON_DIAMOND('K'),
    /**
     * game object keeper_on_wall(through wall mode)
     */
    KEEPER_ON_WALL('R'),
    /**
     * game object crate_on_diamond
     */
    CRATE_ON_DIAMOND('O'),
    /**
     * game object debug object
     */
    DEBUG_OBJECT('=');

    /**
     * the symbol of the game object
     */
    public final char symbol;

    /**
     * initialize the GameObject
     * @param symbol the symbol of the the GameObject
     */
    GameObject(final char symbol) {
        this.symbol = symbol;
    }

    /**
     * get the game object by using defined char symbol
     * @param c get GameObject from char symbol
     * @return return the GameObject corresponding to the char
     */
    public static GameObject fromChar(char c) {
        for (GameObject t : GameObject.values()) {
            if (Character.toUpperCase(c) == t.symbol) {
                return t;
            }
        }
        return WALL;
    }
    //delete getStringSymbol -- speculative generality

    /**
     * return char symbol of the object
     * @return return symbol
     */
    public char getCharSymbol() {
        return symbol;
    }
}