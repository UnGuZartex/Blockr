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

    private Level levelUpOnBlankBeforeWall, levelDownOnGoalBeforeBlank,
            levelLeftOnGoalBeforeGoal, levelRightOnBlankBeforeWall;
    private Point pointUpOnBlankBeforeWall, pointDownOnGoalBeforeBlank,
            pointLeftOnGoalBeforeGoal, pointRightOnBlankBeforeWall;
    private Direction directionUpOnBlankBeforeWall, directionDownOnGoalBeforeBlank,
            directionLeftOnGoalBeforeGoal, directionRightOnBlankBeforeWall;
    private Cell[][] cellsUpOnBlankBeforeWall, cellsDownOnGoalBeforeBlank,
            cellsLeftOnGoalBeforeGoal, cellsRightOnBlankBeforeWall;

    private Level levelSimpleBlankUp, levelSimpleGoalDown, levelSimpleGoalLeft, levelSimpleBlankRight;
    private Point pointSimpleBlankUp, pointSimpleGoalDown, pointSimpleGoalLeft, pointSimpleBlankRight;
    private Direction directionSimpleBlankUp, directionSimpleGoalDown, directionSimpleGoalLeft, directionSimpleBlankRight;
    private Cell[][] cellsSimpleBlankUp, cellsSimpleGoalDown, cellsSimpleGoalLeft, cellsSimpleBlankRight;

    @BeforeEach
    void setUp() {
        /* Simple field has only one cell */
        pointSimpleBlankUp = new Point(0,0);
        pointSimpleGoalDown = new Point(0,0);
        pointSimpleGoalLeft = new Point(0,0);
        pointSimpleBlankRight = new Point(0,0);
        pointUpOnBlankBeforeWall = new Point(1,1);
        pointDownOnGoalBeforeBlank = new Point(1,1);
        pointLeftOnGoalBeforeGoal = new Point(1,1);
        pointRightOnBlankBeforeWall = new Point(1,1);

        directionSimpleBlankUp = Direction.UP;
        directionSimpleGoalDown = Direction.DOWN;
        directionSimpleGoalLeft = Direction.LEFT;
        directionSimpleBlankRight = Direction.RIGHT;
        directionUpOnBlankBeforeWall = Direction.UP;
        directionDownOnGoalBeforeBlank = Direction.DOWN;
        directionLeftOnGoalBeforeGoal = Direction.LEFT;
        directionRightOnBlankBeforeWall = Direction.RIGHT;

        cellsSimpleBlankUp = new Cell[][] {
            { new Cell(CellType.BLANK) }
        };
        cellsSimpleGoalDown = new Cell[][] {
            { new Cell(CellType.GOAL) }
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
        cellsDownOnGoalBeforeBlank = new Cell[][] {
            { new Cell(CellType.BLANK), new Cell(CellType.WALL), new Cell(CellType.BLANK) },
            { new Cell(CellType.WALL), new Cell(CellType.GOAL), new Cell(CellType.BLANK) },
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
        levelSimpleGoalDown = new Level(pointSimpleGoalDown, directionSimpleGoalDown, cellsSimpleGoalDown);
        levelSimpleGoalLeft = new Level(pointSimpleGoalLeft, directionSimpleGoalLeft, cellsSimpleGoalLeft);
        levelSimpleBlankRight = new Level(pointSimpleBlankRight, directionSimpleBlankRight, cellsSimpleBlankRight);
        levelUpOnBlankBeforeWall = new Level(pointUpOnBlankBeforeWall, directionUpOnBlankBeforeWall, cellsUpOnBlankBeforeWall);
        levelDownOnGoalBeforeBlank = new Level(pointDownOnGoalBeforeBlank, directionDownOnGoalBeforeBlank, cellsDownOnGoalBeforeBlank);
        levelLeftOnGoalBeforeGoal = new Level(pointLeftOnGoalBeforeGoal, directionLeftOnGoalBeforeGoal, cellsLeftOnGoalBeforeGoal);
        levelRightOnBlankBeforeWall = new Level(pointRightOnBlankBeforeWall, directionRightOnBlankBeforeWall, cellsRightOnBlankBeforeWall);
    }

    @AfterEach
    void tearDown() {
        pointSimpleBlankUp = null;
        pointSimpleGoalDown = null;
        pointSimpleGoalLeft = null;
        pointSimpleBlankRight = null;
        pointUpOnBlankBeforeWall = null;
        pointDownOnGoalBeforeBlank = null;
        pointLeftOnGoalBeforeGoal = null;
        pointRightOnBlankBeforeWall = null;

        directionSimpleBlankUp = null;
        directionSimpleGoalDown = null;
        directionSimpleGoalLeft = null;
        directionSimpleBlankRight = null;
        directionUpOnBlankBeforeWall = null;
        directionDownOnGoalBeforeBlank = null;
        directionLeftOnGoalBeforeGoal = null;
        directionRightOnBlankBeforeWall = null;

        cellsSimpleBlankUp = null;
        cellsSimpleGoalDown = null;
        cellsSimpleGoalLeft = null;
        cellsSimpleBlankRight = null;
        cellsUpOnBlankBeforeWall = null;
        cellsDownOnGoalBeforeBlank = null;
        cellsLeftOnGoalBeforeGoal = null;
        cellsRightOnBlankBeforeWall = null;

        levelSimpleBlankUp = null;
        levelSimpleGoalDown = null;
        levelSimpleGoalLeft = null;
        levelSimpleBlankRight = null;
        levelUpOnBlankBeforeWall = null;
        levelDownOnGoalBeforeBlank = null;
        levelLeftOnGoalBeforeGoal = null;
        levelRightOnBlankBeforeWall = null;
    }

    @Test
    void LevelPointDirectionCell() {
        try {
            Level level = new Level(new Point(-1,0), Direction.UP, cellsDownOnGoalBeforeBlank);
            assert false;
        } catch (IllegalArgumentException ignored) {};

        try {
            Level level = new Level(new Point(0,-1), Direction.UP, cellsDownOnGoalBeforeBlank);
            assert false;
        } catch (IllegalArgumentException ignored) {};

        try {
            Level level = new Level(new Point(5,1), Direction.UP, cellsDownOnGoalBeforeBlank);
            assert false;
        } catch (IllegalArgumentException ignored) {};

        try {
            Level level = new Level(new Point(1,5), Direction.UP, cellsDownOnGoalBeforeBlank);
            assert false;
        } catch (IllegalArgumentException ignored) {};

        try {
            Level level = new Level(new Point(0, 0), Direction.UP, new Cell[][] {{new Cell(CellType.WALL)}});
            assert false;
        } catch (IllegalArgumentException ignored) {};
    }

    @Test
    void isValidRobotPositionInCells() {
        assertTrue(Level.isValidRobotPositionInCells(pointSimpleBlankUp.x, pointSimpleBlankUp.y, cellsSimpleBlankUp));
        assertTrue(Level.isValidRobotPositionInCells(pointSimpleGoalDown.x, pointSimpleGoalDown.y, cellsSimpleGoalDown));
        assertTrue(Level.isValidRobotPositionInCells(pointSimpleGoalLeft.x, pointSimpleGoalLeft.y, cellsSimpleGoalLeft));
        assertTrue(Level.isValidRobotPositionInCells(pointSimpleBlankRight.x, pointSimpleBlankRight.y, cellsSimpleBlankRight));
        assertTrue(Level.isValidRobotPositionInCells(pointUpOnBlankBeforeWall.x, pointUpOnBlankBeforeWall.y, cellsUpOnBlankBeforeWall));
        assertTrue(Level.isValidRobotPositionInCells(pointDownOnGoalBeforeBlank.x, pointDownOnGoalBeforeBlank.y, cellsDownOnGoalBeforeBlank));
        assertTrue(Level.isValidRobotPositionInCells(pointLeftOnGoalBeforeGoal.x, pointLeftOnGoalBeforeGoal.y, cellsLeftOnGoalBeforeGoal));
        assertTrue(Level.isValidRobotPositionInCells(pointRightOnBlankBeforeWall.x, pointRightOnBlankBeforeWall.y, cellsRightOnBlankBeforeWall));

        assertFalse(Level.isValidRobotPositionInCells(-1, 0, cellsUpOnBlankBeforeWall));
        assertFalse(Level.isValidRobotPositionInCells( 0, -1, cellsUpOnBlankBeforeWall));
        assertFalse(Level.isValidRobotPositionInCells(5, 0, cellsUpOnBlankBeforeWall));
        assertFalse(Level.isValidRobotPositionInCells(0, 5, cellsUpOnBlankBeforeWall));
        assertFalse(Level.isValidRobotPositionInCells(1, 0, cellsUpOnBlankBeforeWall));
    }

    @Test
    void getRobot() {
        assertEquals(pointSimpleBlankUp.x, levelSimpleBlankUp.getRobot().getX());
        assertEquals(pointSimpleBlankUp.y, levelSimpleBlankUp.getRobot().getY());
        assertEquals(directionSimpleBlankUp, levelSimpleBlankUp.getRobot().getDirection());

        assertEquals(pointSimpleGoalDown.x, levelSimpleGoalDown.getRobot().getX());
        assertEquals(pointSimpleGoalDown.y, levelSimpleGoalDown.getRobot().getY());
        assertEquals(directionSimpleGoalDown, levelSimpleGoalDown.getRobot().getDirection());

        assertEquals(pointSimpleGoalLeft.x, levelSimpleGoalLeft.getRobot().getX());
        assertEquals(pointSimpleGoalLeft.y, levelSimpleGoalLeft.getRobot().getY());
        assertEquals(directionSimpleGoalLeft, levelSimpleGoalLeft.getRobot().getDirection());

        assertEquals(pointSimpleBlankRight.x, levelSimpleBlankRight.getRobot().getX());
        assertEquals(pointSimpleBlankRight.y, levelSimpleBlankRight.getRobot().getY());
        assertEquals(directionSimpleBlankRight, levelSimpleBlankRight.getRobot().getDirection());

        assertEquals(pointUpOnBlankBeforeWall.x, levelUpOnBlankBeforeWall.getRobot().getX());
        assertEquals(pointUpOnBlankBeforeWall.y, levelUpOnBlankBeforeWall.getRobot().getY());
        assertEquals(directionUpOnBlankBeforeWall, levelUpOnBlankBeforeWall.getRobot().getDirection());

        assertEquals(pointDownOnGoalBeforeBlank.x, levelDownOnGoalBeforeBlank.getRobot().getX());
        assertEquals(pointDownOnGoalBeforeBlank.y, levelDownOnGoalBeforeBlank.getRobot().getY());
        assertEquals(directionDownOnGoalBeforeBlank, levelDownOnGoalBeforeBlank.getRobot().getDirection());

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

        for (int x = 0; x < levelSimpleGoalDown.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelSimpleGoalDown.getGrid().getHeight(); y++) {
                assertEquals(cellsSimpleGoalDown[x][y], levelSimpleGoalDown.getGrid().getCellAt(x,y));
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

        for (int x = 0; x < levelDownOnGoalBeforeBlank.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelDownOnGoalBeforeBlank.getGrid().getHeight(); y++) {
                assertEquals(cellsDownOnGoalBeforeBlank[x][y], levelDownOnGoalBeforeBlank.getGrid().getCellAt(x,y));
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
        assertTrue(levelSimpleGoalDown.hasWon());
        assertTrue(levelSimpleGoalLeft.hasWon());
        assertFalse(levelSimpleBlankRight.hasWon());
        assertFalse(levelUpOnBlankBeforeWall.hasWon());
        assertTrue(levelDownOnGoalBeforeBlank.hasWon());
        assertTrue(levelLeftOnGoalBeforeGoal.hasWon());
        assertFalse(levelRightOnBlankBeforeWall.hasWon());
    }

    @Test
    void canNotWalkHere() {
        assertFalse(levelSimpleBlankUp.canNotWalkHere());
        assertFalse(levelSimpleGoalDown.canNotWalkHere());
        assertFalse(levelSimpleGoalLeft.canNotWalkHere());
        assertFalse(levelSimpleBlankRight.canNotWalkHere());
        assertFalse(levelUpOnBlankBeforeWall.canNotWalkHere());
        assertFalse(levelDownOnGoalBeforeBlank.canNotWalkHere());
        assertFalse(levelLeftOnGoalBeforeGoal.canNotWalkHere());
        assertFalse(levelRightOnBlankBeforeWall.canNotWalkHere());
    }

    @Test
    void canMoveForward() {
        assertFalse(levelSimpleBlankUp.canMoveForward());
        assertFalse(levelSimpleGoalDown.canMoveForward());
        assertFalse(levelSimpleGoalLeft.canMoveForward());
        assertFalse(levelSimpleBlankRight.canMoveForward());
        assertFalse(levelUpOnBlankBeforeWall.canMoveForward());
        assertTrue(levelDownOnGoalBeforeBlank.canMoveForward());
        assertTrue(levelLeftOnGoalBeforeGoal.canMoveForward());
        assertFalse(levelRightOnBlankBeforeWall.canMoveForward());
    }

    @Test
    void getTypeForward() {
        assertNull(levelSimpleBlankUp.getTypeForward());
        assertNull(levelSimpleGoalDown.getTypeForward());
        assertNull(levelSimpleGoalLeft.getTypeForward());
        assertNull(levelSimpleBlankRight.getTypeForward());
        assertEquals(CellType.WALL, levelUpOnBlankBeforeWall.getTypeForward());
        assertEquals(CellType.BLANK, levelDownOnGoalBeforeBlank.getTypeForward());
        assertEquals(CellType.GOAL, levelLeftOnGoalBeforeGoal.getTypeForward());
        assertEquals(CellType.WALL, levelRightOnBlankBeforeWall.getTypeForward());
    }
}