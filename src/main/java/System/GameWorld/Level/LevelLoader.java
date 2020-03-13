package System.GameWorld.Level;

import System.GameState.GameState;
import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;

import java.awt.*;

/**
 * A class to load levels.
 *
 * @author Alpha-team
 */
public class LevelLoader {

//    private Cell[][] level = new Cell[][]{new Cell[]{new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK)},
//            new Cell[]{new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK)},
//                    new Cell[]{new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK)},
//                            new Cell[]{new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.GOAL)}};

    private Cell[][] cells = new Cell[][]{new Cell[]{new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
            new Cell[]{new Cell(CellType.WALL), new Cell(CellType.BLANK), new Cell(CellType.GOAL)},
            new Cell[]{new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)}};

    /**
     * Load a level which is hardcoded in this level loader. This level is
     * set in the game state.
     */
    public void loadLevel() {
        GameState.setCurrentLevel(new Level(new Point(1,1), Direction.LEFT, cells));
    }
}
