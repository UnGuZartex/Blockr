package System.GameWorld.Level;

import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import static org.junit.jupiter.api.Assertions.*;

import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class LevelTest {

    private Level levelUpOnBlankBeforeWall, levelDownOnGoalBeforeBlank,
            levelLeftOnGoalBeforeGoal, levelRightOnBlankBeforeWall;
    private Position pointUpOnBlankBeforeWall, pointDownOnGoalBeforeBlank,
            pointLeftOnGoalBeforeGoal, pointRightOnBlankBeforeWall;
    private Direction directionUpOnBlankBeforeWall, directionDownOnGoalBeforeBlank,
            directionLeftOnGoalBeforeGoal, directionRightOnBlankBeforeWall;
    private Cell[][] cellsUpOnBlankBeforeWall, cellsDownOnGoalBeforeBlank,
            cellsLeftOnGoalBeforeGoal, cellsRightOnBlankBeforeWall;

    private Level levelSimpleBlankUp, levelSimpleGoalDown, levelSimpleGoalLeft, levelSimpleBlankRight;
    private Position pointSimpleBlankUp, pointSimpleGoalDown, pointSimpleGoalLeft, pointSimpleBlankRight;
    private Direction directionSimpleBlankUp, directionSimpleGoalDown, directionSimpleGoalLeft, directionSimpleBlankRight;
    private Cell[][] cellsSimpleBlankUp, cellsSimpleGoalDown, cellsSimpleGoalLeft, cellsSimpleBlankRight;

    @BeforeEach
    void setUp() {
        /* Simple field has only one cell */
        pointSimpleBlankUp = new Position(0,0);
        pointSimpleGoalDown = new Position(0,0);
        pointSimpleGoalLeft = new Position(0,0);
        pointSimpleBlankRight = new Position(0,0);
        pointUpOnBlankBeforeWall = new Position(1,1);
        pointDownOnGoalBeforeBlank = new Position(1,1);
        pointLeftOnGoalBeforeGoal = new Position(1,1);
        pointRightOnBlankBeforeWall = new Position(1,1);

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
            Level level = new Level(new Position(-1,0), Direction.UP, cellsDownOnGoalBeforeBlank);
            assert false;
        } catch (IllegalArgumentException ignored) {};

        try {
            Level level = new Level(new Position(0,-1), Direction.UP, cellsDownOnGoalBeforeBlank);
            assert false;
        } catch (IllegalArgumentException ignored) {};

        try {
            Level level = new Level(new Position(5,1), Direction.UP, cellsDownOnGoalBeforeBlank);
            assert false;
        } catch (IllegalArgumentException ignored) {};

        try {
            Level level = new Level(new Position(1,5), Direction.UP, cellsDownOnGoalBeforeBlank);
            assert false;
        } catch (IllegalArgumentException ignored) {};

        try {
            Level level = new Level(new Position(0, 0), Direction.UP, new Cell[][] {{new Cell(CellType.WALL)}});
            assert false;
        } catch (IllegalArgumentException ignored) {};
    }

    @Test
    void isValidRobotPositionInCells() {
        assertTrue(Level.isValidRobotPositionInCells(pointSimpleBlankUp.getX(), pointSimpleBlankUp.getY(), cellsSimpleBlankUp));
        assertTrue(Level.isValidRobotPositionInCells(pointSimpleGoalDown.getX(), pointSimpleGoalDown.getY(), cellsSimpleGoalDown));
        assertTrue(Level.isValidRobotPositionInCells(pointSimpleGoalLeft.getX(), pointSimpleGoalLeft.getY(), cellsSimpleGoalLeft));
        assertTrue(Level.isValidRobotPositionInCells(pointSimpleBlankRight.getX(), pointSimpleBlankRight.getY(), cellsSimpleBlankRight));
        assertTrue(Level.isValidRobotPositionInCells(pointUpOnBlankBeforeWall.getX(), pointUpOnBlankBeforeWall.getY(), cellsUpOnBlankBeforeWall));
        assertTrue(Level.isValidRobotPositionInCells(pointDownOnGoalBeforeBlank.getX(), pointDownOnGoalBeforeBlank.getY(), cellsDownOnGoalBeforeBlank));
        assertTrue(Level.isValidRobotPositionInCells(pointLeftOnGoalBeforeGoal.getX(), pointLeftOnGoalBeforeGoal.getY(), cellsLeftOnGoalBeforeGoal));
        assertTrue(Level.isValidRobotPositionInCells(pointRightOnBlankBeforeWall.getX(), pointRightOnBlankBeforeWall.getY(), cellsRightOnBlankBeforeWall));

        assertFalse(Level.isValidRobotPositionInCells(-1, 0, cellsUpOnBlankBeforeWall));
        assertFalse(Level.isValidRobotPositionInCells( 0, -1, cellsUpOnBlankBeforeWall));
        assertFalse(Level.isValidRobotPositionInCells(5, 0, cellsUpOnBlankBeforeWall));
        assertFalse(Level.isValidRobotPositionInCells(0, 5, cellsUpOnBlankBeforeWall));
        assertFalse(Level.isValidRobotPositionInCells(1, 0, cellsUpOnBlankBeforeWall));
    }

    @Test
    void getRobot() {
        assertEquals(pointSimpleBlankUp.getX(), levelSimpleBlankUp.getRobot().getX());
        assertEquals(pointSimpleBlankUp.getY(), levelSimpleBlankUp.getRobot().getY());
        assertEquals(directionSimpleBlankUp, levelSimpleBlankUp.getRobot().getDirection());

        assertEquals(pointSimpleGoalDown.getX(), levelSimpleGoalDown.getRobot().getX());
        assertEquals(pointSimpleGoalDown.getY(), levelSimpleGoalDown.getRobot().getY());
        assertEquals(directionSimpleGoalDown, levelSimpleGoalDown.getRobot().getDirection());

        assertEquals(pointSimpleGoalLeft.getX(), levelSimpleGoalLeft.getRobot().getX());
        assertEquals(pointSimpleGoalLeft.getY(), levelSimpleGoalLeft.getRobot().getY());
        assertEquals(directionSimpleGoalLeft, levelSimpleGoalLeft.getRobot().getDirection());

        assertEquals(pointSimpleBlankRight.getX(), levelSimpleBlankRight.getRobot().getX());
        assertEquals(pointSimpleBlankRight.getY(), levelSimpleBlankRight.getRobot().getY());
        assertEquals(directionSimpleBlankRight, levelSimpleBlankRight.getRobot().getDirection());

        assertEquals(pointUpOnBlankBeforeWall.getX(), levelUpOnBlankBeforeWall.getRobot().getX());
        assertEquals(pointUpOnBlankBeforeWall.getY(), levelUpOnBlankBeforeWall.getRobot().getY());
        assertEquals(directionUpOnBlankBeforeWall, levelUpOnBlankBeforeWall.getRobot().getDirection());

        assertEquals(pointDownOnGoalBeforeBlank.getX(), levelDownOnGoalBeforeBlank.getRobot().getX());
        assertEquals(pointDownOnGoalBeforeBlank.getY(), levelDownOnGoalBeforeBlank.getRobot().getY());
        assertEquals(directionDownOnGoalBeforeBlank, levelDownOnGoalBeforeBlank.getRobot().getDirection());

        assertEquals(pointLeftOnGoalBeforeGoal.getX(), levelLeftOnGoalBeforeGoal.getRobot().getX());
        assertEquals(pointLeftOnGoalBeforeGoal.getY(), levelLeftOnGoalBeforeGoal.getRobot().getY());
        assertEquals(directionLeftOnGoalBeforeGoal, levelLeftOnGoalBeforeGoal.getRobot().getDirection());

        assertEquals(pointRightOnBlankBeforeWall.getX(), levelRightOnBlankBeforeWall.getRobot().getX());
        assertEquals(pointRightOnBlankBeforeWall.getY(), levelRightOnBlankBeforeWall.getRobot().getY());
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