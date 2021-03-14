# AE2DMS-CW-20124870

## **Maintenance:**

- Naming convention + code layout

  1. All variables name, method names are using Camel-case naming conventions. e.g. change the method `getcurrentPosition()` in `Level.java` file to `getCurrentPosition()`. 

  2. Change the layout to the sample below. The first brace is at  the same line with method name of if/for statement. Each method are separated with one empty line

     ```
     if (){
     
     }
     
     public void methodName(){
     
     }
     ```

- Fix bugs

  1. fix bugs in `GameEngine.java`. Change `get(currentLevelIndex+1)` to `get(currentLevelIndex)` to avoid skipping levels

## **Refactoring methodologies**

1. Composing methods
   - extract`GameEngine.isKeeperMoved` and `GameEngine.moveObject` from `GameEngine.move`.  `isKeeperMoved()` is the method to conduct moving keeper and crate. It returns true if keeper is moved and record the position of crate if crate is moved(for undo method). `moveOjbect` method exchanges the objects at current and target positions in `Level.objectGrid`.
   - Extract some of the code from `Level` constructor and form `initLevel` method for level initialization
2. Moving methods between objects
   - move `GameGrid.transplate()` to `GameEngine.transplate()` since `transplate` method is mostly used in `GameEngine` class.
3. Delete methods with speculative generality
   - delete `getStringSymbol` in `GameObject`. The method is built for possible future use.
4. Encapsulate fields
   - change `objectGrid` and `diamondsGrid` in `Level.java` from public to private and add getters methods.
5. Rename variable name to self-document
   - rename `curTile` to `currentObject`  in `Level` constructor
6. Replace Type Code with Polymorphism
   - change the **switch** statement in `GameEngine.handlekey()` into a factory pattern
7. Extract interface
   - Add an interface `IMessage` to print debug info. `GameGrid` and `Level`  classes have certain behavior conducted when the `GameEngine.isDebugActive()` returns true and these behaviors are print the debug information of the game. Therefore, I use a message interface `IMessage ` to print the debug info.

## **MVC + Design patterns**

1. model is in com.ae2dms package. Controller is in controller package. View is fxml file in /resources/views folder.
2. Factory pattern
   1. use a `GraphicOjbectFactory` to add graphic object into the JavaFX grid pane.
   2. use a `MovementFactory` to avoid switch statement.(inspired by  https://www.baeldung.com/java-replace-if-statements 3.1)
3. Singleton pattern
   1. only one instance of `GraphicOjbectFactory` 
4. Observer pattern
   1. `GraphicObjectWall` and `GraphicObjectFloor` observes the style number in `GraphicObject。styleIndex`. Once number changes, they will update the path to pictures.

## **Additions**:

- [x] teleportation

  add new `GameObject.GATE` which could teleport  the keeper(mapset1, level1-3).

- [x] trap

  add `GameObject.TRAP` set the keeper to original position

- [x] puppet mode

  use enters ` ctrl+o` to enter puppet mode. User can sequentially control crates that are not with diamond until the crate has a diamond . The move count is counted as usual when crates move.

- [x] god mode

  By entering `ctrl+z`, user could pass the current level with only 1 step cost.

- [x] interesting levels

  1. Add new map(game map2),
  2. new game objects(see teleportation, trap).

- [x] pause menu (add level name and move count!)

  during the game, when user enter `P`, a pause menu will popped out, in which user could 

  1. adjust music volume
  2. change keeper pictures
  3. change map style

- [x] replace rectangles with pictures

- [x] move through wall

  By entering `ctrl+W`, user could easily pass through walls.
  
- [x] WASD keys to control keeper

