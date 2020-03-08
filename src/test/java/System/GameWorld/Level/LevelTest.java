package System.GameWorld.Level;

import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

class LevelTest {

    private Level levelUpOnBlankBeforeWall, levelDownOnWallBeforeBlank,
            levelLeftOnGoalBeforeGoal, levelRightOnBlankBeforeWall;
    private Point pointUpOnBlankBeforeWall, pointDownOnWallBeforeBlank,
            pointLeftOnGoalBeforeGoal, pointRightOnBlankBeforeWall;
    private Direction directionUpOnBlankBeforeWall, directionDownOnWallBeforeBlank,
            directionLeftOnGoalBeforeGoal, directionRightOnBlankBeforeWall;
    private Cell[][] cellsUpOnBlankBeforeWall, cellsDownOnWallBeforeBlank,
            cellsLeftOnGoalBeforeGoal, cellsRightOnBlankBeforeWall;

    private Level levelSimpleBlankUp, levelSimpleWallDown, levelSimpleGoalLeft, levelSimpleBlankRight;
    private Point pointSimpleBlankUp, pointSimpleWallDown, pointSimpleGoalLeft, pointSimpleBlankRight;
    private Direction directionSimpleBlankUp, directionSimpleWallDown, directionSimpleGoalLeft, directionSimpleBlankRight;
    private Cell[][] cellsSimpleBlankUp, cellsSimpleWallDown, cellsSimpleGoalLeft, cellsSimpleBlankRight;

    @BeforeEach
    void setUp() {
        /* Simple field has only one cell */
        pointSimpleBlankUp = new Point(0,0);
        pointSimpleWallDown = new Point(0,0);
        pointSimpleGoalLeft = new Point(0,0);
        pointSimpleBlankRight = new Point(0,0);
        pointUpOnBlankBeforeWall = new Point(1,1);
        pointDownOnWallBeforeBlank = new Point(1,1);
        pointLeftOnGoalBeforeGoal = new Point(1,1);
        pointRightOnBlankBeforeWall = new Point(1,1);

        directionSimpleBlankUp = Direction.UP;
        directionSimpleWallDown = Direction.DOWN;
        directionSimpleGoalLeft = Direction.LEFT;
        directionSimpleBlankRight = Direction.RIGHT;
        directionUpOnBlankBeforeWall = Direction.UP;
        directionDownOnWallBeforeBlank = Direction.DOWN;
        directionLeftOnGoalBeforeGoal = Direction.LEFT;
        directionRightOnBlankBeforeWall = Direction.RIGHT;

        cellsSimpleBlankUp = new Cell[][] {
            { new Cell(CellType.BLANK) }
        };
        cellsSimpleWallDown = new Cell[][] {
            { new Cell(CellType.WALL) }
        };
        cellsSimpleGoalLeft = new Cell[][] {
            { new Cell(CellType.GOAL) }
        };
        cellsSimpleBlankRight = new Cell[][] {
            { new Cell(CellType.BLANK) }
        };
        // The matrix is mirrored as grid over the main diagonal
        cellsUpOnBlankBeforeWall = new Cell[][] {
            { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
            { new Cell(CellType.WALL), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
            { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
        };
        cellsDownOnWallBeforeBlank = new Cell[][] {
            { new Cell(CellType.BLANK), new Cell(CellType.WALL), new Cell(CellType.BLANK) },
            { new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.BLANK) },
            { new Cell(CellType.BLANK), new Cell(CellType.WALL), new Cell(CellType.BLANK) },
        };
        cellsLeftOnGoalBeforeGoal = new Cell[][] {
            { new Cell(CellType.BLANK), new Cell(CellType.GOAL), new Cell(CellType.BLANK) },
            { new Cell(CellType.BLANK), new Cell(CellType.GOAL), new Cell(CellType.BLANK) },
            { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
        };
        cellsRightOnBlankBeforeWall = new Cell[][] {
            { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
            { new Cell(CellType.BLANK), new Cell(CellType.BLANK), new Cell(CellType.BLANK) },
            { new Cell(CellType.BLANK), new Cell(CellType.WALL), new Cell(CellType.BLANK) },
        };

        levelSimpleBlankUp = new Level(pointSimpleBlankUp, directionSimpleBlankUp, cellsSimpleBlankUp);
        levelSimpleWallDown = new Level(pointSimpleWallDown, directionSimpleWallDown, cellsSimpleWallDown);
        levelSimpleGoalLeft = new Level(pointSimpleGoalLeft, directionSimpleGoalLeft, cellsSimpleGoalLeft);
        levelSimpleBlankRight = new Level(pointSimpleBlankRight, directionSimpleBlankRight, cellsSimpleBlankRight);
        levelUpOnBlankBeforeWall = new Level(pointUpOnBlankBeforeWall, directionUpOnBlankBeforeWall, cellsUpOnBlankBeforeWall);
        levelDownOnWallBeforeBlank = new Level(pointDownOnWallBeforeBlank, directionDownOnWallBeforeBlank, cellsDownOnWallBeforeBlank);
        levelLeftOnGoalBeforeGoal = new Level(pointLeftOnGoalBeforeGoal, directionLeftOnGoalBeforeGoal, cellsLeftOnGoalBeforeGoal);
        levelRightOnBlankBeforeWall = new Level(pointRightOnBlankBeforeWall, directionRightOnBlankBeforeWall, cellsRightOnBlankBeforeWall);
    }

    @AfterEach
    void tearDown() {
        pointSimpleBlankUp = null;
        pointSimpleWallDown = null;
        pointSimpleGoalLeft = null;
        pointSimpleBlankRight = null;
        pointUpOnBlankBeforeWall = null;
        pointDownOnWallBeforeBlank = null;
        pointLeftOnGoalBeforeGoal = null;
        pointRightOnBlankBeforeWall = null;

        directionSimpleBlankUp = null;
        directionSimpleWallDown = null;
        directionSimpleGoalLeft = null;
        directionSimpleBlankRight = null;
        directionUpOnBlankBeforeWall = null;
        directionDownOnWallBeforeBlank = null;
        directionLeftOnGoalBeforeGoal = null;
        directionRightOnBlankBeforeWall = null;

        cellsSimpleBlankUp = null;
        cellsSimpleWallDown = null;
        cellsSimpleGoalLeft = null;
        cellsSimpleBlankRight = null;
        cellsUpOnBlankBeforeWall = null;
        cellsDownOnWallBeforeBlank = null;
        cellsLeftOnGoalBeforeGoal = null;
        cellsRightOnBlankBeforeWall = null;

        levelSimpleBlankUp = null;
        levelSimpleWallDown = null;
        levelSimpleGoalLeft = null;
        levelSimpleBlankRight = null;
        levelUpOnBlankBeforeWall = null;
        levelDownOnWallBeforeBlank = null;
        levelLeftOnGoalBeforeGoal = null;
        levelRightOnBlankBeforeWall = null;
    }

    @Test
    void getRobot() {
        assertEquals(pointSimpleBlankUp.x, levelSimpleBlankUp.getRobot().getX());
        assertEquals(pointSimpleBlankUp.y, levelSimpleBlankUp.getRobot().getY());
        assertEquals(directionSimpleBlankUp, levelSimpleBlankUp.getRobot().getDirection());

        assertEquals(pointSimpleWallDown.x, levelSimpleWallDown.getRobot().getX());
        assertEquals(pointSimpleWallDown.y, levelSimpleWallDown.getRobot().getY());
        assertEquals(directionSimpleWallDown, levelSimpleWallDown.getRobot().getDirection());

        assertEquals(pointSimpleGoalLeft.x, levelSimpleGoalLeft.getRobot().getX());
        assertEquals(pointSimpleGoalLeft.y, levelSimpleGoalLeft.getRobot().getY());
        assertEquals(directionSimpleGoalLeft, levelSimpleGoalLeft.getRobot().getDirection());

        assertEquals(pointSimpleBlankRight.x, levelSimpleBlankRight.getRobot().getX());
        assertEquals(pointSimpleBlankRight.y, levelSimpleBlankRight.getRobot().getY());
        assertEquals(directionSimpleBlankRight, levelSimpleBlankRight.getRobot().getDirection());

        assertEquals(pointUpOnBlankBeforeWall.x, levelUpOnBlankBeforeWall.getRobot().getX());
        assertEquals(pointUpOnBlankBeforeWall.y, levelUpOnBlankBeforeWall.getRobot().getY());
        assertEquals(directionUpOnBlankBeforeWall, levelUpOnBlankBeforeWall.getRobot().getDirection());

        assertEquals(pointDownOnWallBeforeBlank.x, levelDownOnWallBeforeBlank.getRobot().getX());
        assertEquals(pointDownOnWallBeforeBlank.y, levelDownOnWallBeforeBlank.getRobot().getY());
        assertEquals(directionDownOnWallBeforeBlank, levelDownOnWallBeforeBlank.getRobot().getDirection());

        assertEquals(pointLeftOnGoalBeforeGoal.x, levelLeftOnGoalBeforeGoal.getRobot().getX());
        assertEquals(pointLeftOnGoalBeforeGoal.y, levelLeftOnGoalBeforeGoal.getRobot().getY());
        assertEquals(directionLeftOnGoalBeforeGoal, levelLeftOnGoalBeforeGoal.getRobot().getDirection());

        assertEquals(pointRightOnBlankBeforeWall.x, levelRightOnBlankBeforeWall.getRobot().getX());
        assertEquals(pointRightOnBlankBeforeWall.y, levelRightOnBlankBeforeWall.getRobot().getY());
        assertEquals(directionRightOnBlankBeforeWall, levelRightOnBlankBeforeWall.getRobot().getDirection());
    }

    @Test
    void getGrid() {
        for (int x = 0; x < levelSimpleBlankUp.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelSimpleBlankUp.getGrid().getHeight(); y++) {
                assertEquals(cellsSimpleBlankUp[x][y], levelSimpleBlankUp.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelSimpleWallDown.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelSimpleWallDown.getGrid().getHeight(); y++) {
                assertEquals(cellsSimpleWallDown[x][y], levelSimpleWallDown.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelSimpleGoalLeft.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelSimpleGoalLeft.getGrid().getHeight(); y++) {
                assertEquals(cellsSimpleGoalLeft[x][y], levelSimpleGoalLeft.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelSimpleBlankRight.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelSimpleBlankRight.getGrid().getHeight(); y++) {
                assertEquals(cellsSimpleBlankRight[x][y], levelSimpleBlankRight.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelUpOnBlankBeforeWall.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelUpOnBlankBeforeWall.getGrid().getHeight(); y++) {
                assertEquals(cellsUpOnBlankBeforeWall[x][y], levelUpOnBlankBeforeWall.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelDownOnWallBeforeBlank.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelDownOnWallBeforeBlank.getGrid().getHeight(); y++) {
                assertEquals(cellsDownOnWallBeforeBlank[x][y], levelDownOnWallBeforeBlank.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelLeftOnGoalBeforeGoal.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelLeftOnGoalBeforeGoal.getGrid().getHeight(); y++) {
                assertEquals(cellsLeftOnGoalBeforeGoal[x][y], levelLeftOnGoalBeforeGoal.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelRightOnBlankBeforeWall.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelRightOnBlankBeforeWall.getGrid().getHeight(); y++) {
                assertEquals(cellsRightOnBlankBeforeWall[x][y], levelRightOnBlankBeforeWall.getGrid().getCellAt(x,y));
            }
        }
    }

    @Test
    void hasWon() {
        assertFalse(levelSimpleBlankUp.hasWon());
        assertFalse(levelSimpleWallDown.hasWon());
        assertTrue(levelSimpleGoalLeft.hasWon());
        assertFalse(levelSimpleBlankRight.hasWon());
        assertFalse(levelUpOnBlankBeforeWall.hasWon());
        assertFalse(levelDownOnWallBeforeBlank.hasWon());
        assertTrue(levelLeftOnGoalBeforeGoal.hasWon());
        assertFalse(levelRightOnBlankBeforeWall.hasWon());
    }

    @Test
    void canNotWalkHere() {
        assertFalse(levelSimpleBlankUp.canNotWalkHere());
        assertTrue(levelSimpleWallDown.canNotWalkHere());
        assertFalse(levelSimpleGoalLeft.canNotWalkHere());
        assertFalse(levelSimpleBlankRight.canNotWalkHere());
        assertFalse(levelUpOnBlankBeforeWall.canNotWalkHere());
        assertTrue(levelDownOnWallBeforeBlank.canNotWalkHere());
        assertFalse(levelLeftOnGoalBeforeGoal.canNotWalkHere());
        assertFalse(levelRightOnBlankBeforeWall.canNotWalkHere());
    }

    @Test
    void canMoveForward() {
        assertFalse(levelSimpleBlankUp.canMoveForward());
        assertFalse(levelSimpleWallDown.canMoveForward());
        assertFalse(levelSimpleGoalLeft.canMoveForward());
        assertFalse(levelSimpleBlankRight.canMoveForward());
        assertFalse(levelUpOnBlankBeforeWall.canMoveForward());
        assertTrue(levelDownOnWallBeforeBlank.canMoveForward());
        assertTrue(levelLeftOnGoalBeforeGoal.canMoveForward());
        assertFalse(levelRightOnBlankBeforeWall.canMoveForward());
    }

    @Test
    void getTypeForward() {
        assertNull(levelSimpleBlankUp.getTypeForward());
        assertNull(levelSimpleWallDown.getTypeForward());
        assertNull(levelSimpleGoalLeft.getTypeForward());
        assertNull(levelSimpleBlankRight.getTypeForward());
        assertEquals(CellType.WALL, levelUpOnBlankBeforeWall.getTypeForward());
        assertEquals(CellType.BLANK, levelDownOnWallBeforeBlank.getTypeForward());
        assertEquals(CellType.GOAL, levelLeftOnGoalBeforeGoal.getTypeForward());
        assertEquals(CellType.WALL, levelRightOnBlankBeforeWall.getTypeForward());
    }
}