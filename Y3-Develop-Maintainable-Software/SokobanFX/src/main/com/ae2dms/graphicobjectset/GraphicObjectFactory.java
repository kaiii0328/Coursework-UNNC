package main.com.ae2dms.graphicobjectset;

import main.com.ae2dms.GameEngine;
import main.com.ae2dms.GameObject;

/**
 * The {@code GraphicObjectFactory} instantiates the corresponding graphic objects
 * of the game objects.
 * The graphic objects are derived classes of {@link GraphicObject}.
 */
public class GraphicObjectFactory {
    private static GraphicObjectFactory instance = new GraphicObjectFactory();
    private GraphicObjectFactory(){}
    public GraphicObject getObject(GameObject obj){
        if (obj == GameObject.WALL){
            return new GraphicObjectWall();
        }
        else if (obj == GameObject.CRATE){
            return  new GraphicObjectCrate();
        }
        else if(obj == GameObject.DIAMOND){
            return  new GraphicObjectDiamond();
        }
        else if (obj == GameObject.FLOOR){
            return  new GraphicObjectFloor();
        }
        else if (obj == GameObject.CRATE_ON_DIAMOND){
            return  new GraphicObjectCrateDiamond();
        }
        else if (obj == GameObject.KEEPER){
            return  new GraphicObjectKeeper();
        }
        else if (obj == GameObject.GATE){
            return  new GraphicObjectGate();
        }
        else if (obj == GameObject.KEEPER_ON_GATE){
            return new GraphicObjectKeeperGate();
        }
        else if (obj == GameObject.TRAP){
            return  new GraphicObjectTrap();
        }
        else { //Unknown game object type
            String message = "Error in Level constructor. Object not recognized.";
            GameEngine.logger.severe(message);
            throw new AssertionError(message);
        }
    }
    public static GraphicObjectFactory getInstance(){
        return  instance;
    }
}
