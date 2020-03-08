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

    private Level level1, level2, level3, level4;
    private Point point1, point2, point3, point4;
    private Direction direction1, direction2, direction3, direction4;
    private Cell[][] cells1, cells2, cells3, cells4;

    private Level levelSimpleBlankUp, levelSimpleWallDown, levelSimpleGoalLeft, levelSimpleBlankRight;
    private Point pointSimpleBlankUp, pointSimpleWallDown, pointSimpleGoalLeft, pointSimpleBlankRight;
    private Direction directionSimpleBlankUp, directionSimpleWallDown, directionSimpleGoalLeft, directionSimpleBlankRight;
    private Cell[][] cellsSimpleBlankUp, cellsSimpleWallDown, cellsSimpleGoalLeft, cellsSimpleBlankRight;

    @BeforeEach
    void setUp() {
        /* Simple field has size (0,0) */
        pointSimpleBlankUp = new Point(0,0);
        pointSimpleWallDown = new Point(0,0);
        pointSimpleGoalLeft = new Point(0,0);
        pointSimpleBlankRight = new Point(0,0);
        point1 = new Point(0,0);
        point2 = new Point(0,0);
        point3 = new Point(0,0);
        point4 = new Point(0,0);

        directionSimpleBlankUp = Direction.UP;
        directionSimpleWallDown = Direction.DOWN;
        directionSimpleGoalLeft = Direction.LEFT;
        directionSimpleBlankRight = Direction.RIGHT;
        direction1 = Direction.UP;
        direction2 = Direction.DOWN;
        direction3 = Direction.LEFT;
        direction4 = Direction.RIGHT;

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
        cells1 = new Cell[1][1]; // TODO initialise cells
        cells2 = new Cell[1][1];
        cells3 = new Cell[1][1];
        cells4 = new Cell[1][1];

        levelSimpleBlankUp = new Level(pointSimpleBlankUp, directionSimpleBlankUp, cellsSimpleBlankUp);
        levelSimpleWallDown = new Level(pointSimpleWallDown, directionSimpleWallDown, cellsSimpleWallDown);
        levelSimpleGoalLeft = new Level(pointSimpleGoalLeft, directionSimpleGoalLeft, cellsSimpleGoalLeft);
        levelSimpleBlankRight = new Level(pointSimpleBlankRight, directionSimpleBlankRight, cellsSimpleBlankRight);
        level1 = new Level(point1, direction1, cells1);
        level2 = new Level(point2, direction2, cells2);
        level3 = new Level(point3, direction3, cells3);
        level4 = new Level(point4, direction4, cells4);
    }

    @AfterEach
    void tearDown() {
        pointSimpleBlankUp = null;
        pointSimpleWallDown = null;
        pointSimpleGoalLeft = null;
        pointSimpleBlankRight = null;
        point1 = null;
        point2 = null;
        point3 = null;
        point4 = null;

        directionSimpleBlankUp = null;
        directionSimpleWallDown = null;
        directionSimpleGoalLeft = null;
        directionSimpleBlankRight = null;
        direction1 = null;
        direction2 = null;
        direction3 = null;
        direction4 = null;

        cellsSimpleBlankUp = null;
        cellsSimpleWallDown = null;
        cellsSimpleGoalLeft = null;
        cellsSimpleBlankRight = null;
        cells1 = null;
        cells2 = null;
        cells3 = null;
        cells4 = null;

        levelSimpleBlankUp = null;
        levelSimpleWallDown = null;
        levelSimpleGoalLeft = null;
        levelSimpleBlankRight = null;
        level1 = null;
        level2 = null;
        level3 = null;
        level4 = null;
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

        Assert.assertEquals(point1.x, level1.getRobot().getX());
        Assert.assertEquals(point1.y, level1.getRobot().getY());
        Assert.assertEquals(direction1, level1.getRobot().getDirection());

        Assert.assertEquals(point2.x, level2.getRobot().getX());
        Assert.assertEquals(point2.y, level2.getRobot().getY());
        Assert.assertEquals(direction2, level2.getRobot().getDirection());

        Assert.assertEquals(point3.x, level3.getRobot().getX());
        Assert.assertEquals(point3.y, level3.getRobot().getY());
        Assert.assertEquals(direction3, level3.getRobot().getDirection());

        Assert.assertEquals(point4.x, level4.getRobot().getX());
        Assert.assertEquals(point4.y, level4.getRobot().getY());
        Assert.assertEquals(direction4, level4.getRobot().getDirection());
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

        for (int x = 0; x < level1.getGrid().getWidth(); x++) {
            for (int y = 0; y < level1.getGrid().getHeight(); y++) {
                Assert.assertEquals(cells1[x][y], level1.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < level2.getGrid().getWidth(); x++) {
            for (int y = 0; y < level2.getGrid().getHeight(); y++) {
                Assert.assertEquals(cells2[x][y], level2.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < level3.getGrid().getWidth(); x++) {
            for (int y = 0; y < level3.getGrid().getHeight(); y++) {
                Assert.assertEquals(cells3[x][y], level3.getGrid().getCellAt(x,y));
            }
        }

        for (int x = 0; x < level4.getGrid().getWidth(); x++) {
            for (int y = 0; y < level4.getGrid().getHeight(); y++) {
                Assert.assertEquals(cells4[x][y], level4.getGrid().getCellAt(x,y));
            }
        }
    }

    @Test
    void hasWon() {
        Assert.assertFalse(levelSimpleBlankUp.hasWon());
        Assert.assertFalse(levelSimpleWallDown.hasWon());
        Assert.assertTrue(levelSimpleGoalLeft.hasWon());
        Assert.assertFalse(levelSimpleBlankRight.hasWon());
    }

    @Test
    void canNotWalkHere() {
        Assert.assertFalse(levelSimpleBlankUp.canNotWalkHere());
        Assert.assertTrue(levelSimpleWallDown.canNotWalkHere());
        Assert.assertFalse(levelSimpleGoalLeft.canNotWalkHere());
        Assert.assertFalse(levelSimpleBlankRight.canNotWalkHere());
        // TODO robot will not walk outside grid
    }

    @Test
    void canMoveForward() {
        Assert.assertFalse(levelSimpleBlankUp.canMoveForward());
        Assert.assertFalse(levelSimpleWallDown.canMoveForward());
        Assert.assertFalse(levelSimpleGoalLeft.canMoveForward());
        Assert.assertFalse(levelSimpleBlankRight.canMoveForward());
        // TODO robot will not walk outside grid
    }

    @Test
    void getTypeForward() {
        Assert.assertNull(levelSimpleBlankUp.getTypeForward());
        Assert.assertNull(levelSimpleWallDown.getTypeForward());
        Assert.assertNull(levelSimpleGoalLeft.getTypeForward());
        Assert.assertNull(levelSimpleBlankRight.getTypeForward());
        // TODO robot will not walk out of grid
    }

}