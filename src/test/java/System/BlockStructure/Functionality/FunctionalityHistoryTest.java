package System.BlockStructure.Functionality;

import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Grid;
import GameWorld.Level;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;
import GameWorldUtility.Actions.TurnLeftAction;
import RobotCollection.Robot.*;
import RobotCollection.Utility.GridPosition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionalityHistoryTest {

    Action action;
    BlockFunctionality functionality;
    Level level;
    GridPosition pos;
    Cell[][] cells;
    Direction direction;
    Robot robot;
    Grid grid;

    @BeforeEach
    void setUp() {
        direction = Direction.UP;
        pos = new GridPosition(1,1);
        robot = new Robot(pos, direction);
        cells = new Cell[][] {
                { new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL) },
                { new Cell(CellType.WALL), new Cell(CellType.BLANK), new Cell(CellType.GOAL) },
                { new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL) },
        };
        grid = new Grid(cells);
        level = new Level(robot, grid);
        action = new TurnLeftAction();
        functionality = new ActionFunctionality(action, level);
    }

    @AfterEach
    void tearDown() {
        level = null;
        action = null;
        functionality = null;
    }

    @Test
    void snapshotTest() {
        assertEquals(pos.getX(), level.getRobot().getGridPosition().getX());
        assertEquals(pos.getY(), level.getRobot().getGridPosition().getY());
        assertEquals(direction.name(), level.getRobot().getDirection());

        assertEquals(Result.SUCCESS, functionality.execute());

        assertEquals(pos.getX(), level.getRobot().getGridPosition().getX());
        assertEquals(pos.getY(), level.getRobot().getGridPosition().getY());
        assertEquals(direction.turnLeft().name(), level.getRobot().getDirection());

        functionality.loadCurrentSnapshot();

        assertEquals(pos.getX(), level.getRobot().getGridPosition().getX());
        assertEquals(pos.getY(), level.getRobot().getGridPosition().getY());
        assertEquals(direction.name(), level.getRobot().getDirection());

        functionality.loadCurrentSnapshot();

        assertEquals(pos.getX(), level.getRobot().getGridPosition().getX());
        assertEquals(pos.getY(), level.getRobot().getGridPosition().getY());
        assertEquals(direction.turnLeft().name(), level.getRobot().getDirection());
    }
}
