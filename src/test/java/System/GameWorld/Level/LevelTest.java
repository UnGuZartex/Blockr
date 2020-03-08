package System.GameWorld.Level;

import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import org.junit.Assert;
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
        Assert.assertEquals(pointSimpleBlankUp.x, levelSimpleBlankUp.getRobot().getX());
        Assert.assertEquals(pointSimpleBlankUp.y, levelSimpleBlankUp.getRobot().getY());
        Assert.assertEquals(directionSimpleBlankUp, levelSimpleBlankUp.getRobot().getDirection());

        Assert.assertEquals(pointSimpleWallDown.x, levelSimpleWallDown.getRobot().getX());
        Assert.assertEquals(pointSimpleWallDown.y, levelSimpleWallDown.getRobot().getY());
        Assert.assertEquals(directionSimpleWallDown, levelSimpleWallDown.getRobot().getDirection());

        Assert.assertEquals(pointSimpleGoalLeft.x, levelSimpleGoalLeft.getRobot().getX());
        Assert.assertEquals(pointSimpleGoalLeft.y, levelSimpleGoalLeft.getRobot().getY());
        Assert.assertEquals(directionSimpleGoalLeft, levelSimpleGoalLeft.getRobot().getDirection());

        Assert.assertEquals(pointSimpleBlankRight.x, levelSimpleBlankRight.getRobot().getX());
        Assert.assertEquals(pointSimpleBlankRight.y, levelSimpleBlankRight.getRobot().getY());
        Assert.assertEquals(directionSimpleBlankRight, levelSimpleBlankRight.getRobot().getDirection());

        Assert.assertEquals(pointUpOnBlankBeforeWall.x, levelUpOnBlankBeforeWall.getRobot().getX());
        Assert.assertEquals(pointUpOnBlankBeforeWall.y, levelUpOnBlankBeforeWall.getRobot().getY());
        Assert.assertEquals(directionUpOnBlankBeforeWall, levelUpOnBlankBeforeWall.getRobot().getDirection());

        Assert.assertEquals(pointDownOnWallBeforeBlank.x, levelDownOnWallBeforeBlank.getRobot().getX());
        Assert.assertEquals(pointDownOnWallBeforeBlank.y, levelDownOnWallBeforeBlank.getRobot().getY());
        Assert.assertEquals(directionDownOnWallBeforeBlank, levelDownOnWallBeforeBlank.getRobot().getDirection());

        Assert.assertEquals(pointLeftOnGoalBeforeGoal.x, levelLeftOnGoalBeforeGoal.getRobot().getX());
        Assert.assertEquals(pointLeftOnGoalBeforeGoal.y, levelLeftOnGoalBeforeGoal.getRobot().getY());
        Assert.assertEquals(directionLeftOnGoalBeforeGoal, levelLeftOnGoalBeforeGoal.getRobot().getDirection());

        Assert.assertEquals(pointRightOnBlankBeforeWall.x, levelRightOnBlankBeforeWall.getRobot().getX());
        Assert.assertEquals(pointRightOnBlankBeforeWall.y, levelRightOnBlankBeforeWall.getRobot().getY());
        Assert.assertEquals(directionRightOnBlankBeforeWall, levelRightOnBlankBeforeWall.getRobot().getDirection());
    }

    @Test
    void getGrid() {
        for (int x = 0; x < levelSimpleBlankUp.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelSimpleBlankUp.getGrid().getHeight(); y++) {
                Assert.assertEquals(cellsSimpleBlankUp[x][y], levelSimpleBlankUp.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelSimpleWallDown.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelSimpleWallDown.getGrid().getHeight(); y++) {
                Assert.assertEquals(cellsSimpleWallDown[x][y], levelSimpleWallDown.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelSimpleGoalLeft.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelSimpleGoalLeft.getGrid().getHeight(); y++) {
                Assert.assertEquals(cellsSimpleGoalLeft[x][y], levelSimpleGoalLeft.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelSimpleBlankRight.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelSimpleBlankRight.getGrid().getHeight(); y++) {
                Assert.assertEquals(cellsSimpleBlankRight[x][y], levelSimpleBlankRight.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelUpOnBlankBeforeWall.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelUpOnBlankBeforeWall.getGrid().getHeight(); y++) {
                Assert.assertEquals(cellsUpOnBlankBeforeWall[x][y], levelUpOnBlankBeforeWall.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelDownOnWallBeforeBlank.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelDownOnWallBeforeBlank.getGrid().getHeight(); y++) {
                Assert.assertEquals(cellsDownOnWallBeforeBlank[x][y], levelDownOnWallBeforeBlank.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelLeftOnGoalBeforeGoal.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelLeftOnGoalBeforeGoal.getGrid().getHeight(); y++) {
                Assert.assertEquals(cellsLeftOnGoalBeforeGoal[x][y], levelLeftOnGoalBeforeGoal.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < levelRightOnBlankBeforeWall.getGrid().getWidth(); x++) {
            for (int y = 0; y < levelRightOnBlankBeforeWall.getGrid().getHeight(); y++) {
                Assert.assertEquals(cellsRightOnBlankBeforeWall[x][y], levelRightOnBlankBeforeWall.getGrid().getCellAt(x,y));
            }
        }
    }

    @Test
    void hasWon() {
        Assert.assertFalse(levelSimpleBlankUp.hasWon());
        Assert.assertFalse(levelSimpleWallDown.hasWon());
        Assert.assertTrue(levelSimpleGoalLeft.hasWon());
        Assert.assertFalse(levelSimpleBlankRight.hasWon());
        Assert.assertFalse(levelUpOnBlankBeforeWall.hasWon());
        Assert.assertFalse(levelDownOnWallBeforeBlank.hasWon());
        Assert.assertTrue(levelLeftOnGoalBeforeGoal.hasWon());
        Assert.assertFalse(levelRightOnBlankBeforeWall.hasWon());
    }

    @Test
    void canNotWalkHere() {
        Assert.assertFalse(levelSimpleBlankUp.canNotWalkHere());
        Assert.assertTrue(levelSimpleWallDown.canNotWalkHere());
        Assert.assertFalse(levelSimpleGoalLeft.canNotWalkHere());
        Assert.assertFalse(levelSimpleBlankRight.canNotWalkHere());
        Assert.assertFalse(levelUpOnBlankBeforeWall.canNotWalkHere());
        Assert.assertTrue(levelDownOnWallBeforeBlank.canNotWalkHere());
        Assert.assertFalse(levelLeftOnGoalBeforeGoal.canNotWalkHere());
        Assert.assertFalse(levelRightOnBlankBeforeWall.canNotWalkHere());
    }

    @Test
    void canMoveForward() {
        Assert.assertFalse(levelSimpleBlankUp.canMoveForward());
        Assert.assertFalse(levelSimpleWallDown.canMoveForward());
        Assert.assertFalse(levelSimpleGoalLeft.canMoveForward());
        Assert.assertFalse(levelSimpleBlankRight.canMoveForward());
        Assert.assertFalse(levelUpOnBlankBeforeWall.canMoveForward());
        Assert.assertTrue(levelDownOnWallBeforeBlank.canMoveForward());
        Assert.assertTrue(levelLeftOnGoalBeforeGoal.canMoveForward());
        Assert.assertFalse(levelRightOnBlankBeforeWall.canMoveForward());
    }

    @Test
    void getTypeForward() {
        Assert.assertNull(levelSimpleBlankUp.getTypeForward());
        Assert.assertNull(levelSimpleWallDown.getTypeForward());
        Assert.assertNull(levelSimpleGoalLeft.getTypeForward());
        Assert.assertNull(levelSimpleBlankRight.getTypeForward());
        Assert.assertEquals(CellType.WALL, levelUpOnBlankBeforeWall.getTypeForward());
        Assert.assertEquals(CellType.BLANK, levelDownOnWallBeforeBlank.getTypeForward());
        Assert.assertEquals(CellType.GOAL, levelLeftOnGoalBeforeGoal.getTypeForward());
        Assert.assertEquals(CellType.WALL, levelRightOnBlankBeforeWall.getTypeForward());
    }
}