package System.BlockStructure.Functionality;

import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import System.GameWorld.Level.Level;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MoveForwardFunctionalityTest {

    private MoveForwardFunctionality moveForward;

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
        moveForward = new MoveForwardFunctionality();

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
        moveForward = null;

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
    void getEvaluation() {
        assertFalse(moveForward.getEvaluation());
    }

    @Test
    void evaluate() {
        moveForward.evaluate(levelSimpleBlankUp);
        assertEquals(pointSimpleBlankUp.x, levelSimpleBlankUp.getRobot().getX());
        assertEquals(pointSimpleBlankUp.y, levelSimpleBlankUp.getRobot().getY());
        assertEquals(directionSimpleBlankUp, levelSimpleBlankUp.getRobot().getDirection());

        moveForward.evaluate(levelSimpleGoalDown);
        assertEquals(pointSimpleGoalDown.x, levelSimpleGoalDown.getRobot().getX());
        assertEquals(pointSimpleGoalDown.y, levelSimpleGoalDown.getRobot().getY());
        assertEquals(directionSimpleGoalDown, levelSimpleGoalDown.getRobot().getDirection());

        moveForward.evaluate(levelSimpleGoalLeft);
        assertEquals(pointSimpleGoalLeft.x, levelSimpleGoalLeft.getRobot().getX());
        assertEquals(pointSimpleGoalLeft.y, levelSimpleGoalLeft.getRobot().getY());
        assertEquals(directionSimpleGoalLeft, levelSimpleGoalLeft.getRobot().getDirection());

        moveForward.evaluate(levelSimpleBlankRight);
        assertEquals(pointSimpleBlankRight.x, levelSimpleBlankRight.getRobot().getX());
        assertEquals(pointSimpleBlankRight.y, levelSimpleBlankRight.getRobot().getY());
        assertEquals(directionSimpleBlankRight, levelSimpleBlankRight.getRobot().getDirection());

        moveForward.evaluate(levelUpOnBlankBeforeWall);
        assertEquals(pointUpOnBlankBeforeWall.x, levelUpOnBlankBeforeWall.getRobot().getX());
        assertEquals(pointUpOnBlankBeforeWall.y, levelUpOnBlankBeforeWall.getRobot().getY());
        assertEquals(directionUpOnBlankBeforeWall, levelUpOnBlankBeforeWall.getRobot().getDirection());

        moveForward.evaluate(levelDownOnGoalBeforeBlank);
        assertEquals(pointDownOnGoalBeforeBlank.x, levelDownOnGoalBeforeBlank.getRobot().getX());
        assertEquals(pointDownOnGoalBeforeBlank.y + 1, levelDownOnGoalBeforeBlank.getRobot().getY());
        assertEquals(directionDownOnGoalBeforeBlank, levelDownOnGoalBeforeBlank.getRobot().getDirection());

        moveForward.evaluate(levelLeftOnGoalBeforeGoal);
        assertEquals(pointLeftOnGoalBeforeGoal.x - 1, levelLeftOnGoalBeforeGoal.getRobot().getX());
        assertEquals(pointLeftOnGoalBeforeGoal.y, levelLeftOnGoalBeforeGoal.getRobot().getY());
        assertEquals(directionLeftOnGoalBeforeGoal, levelLeftOnGoalBeforeGoal.getRobot().getDirection());

        moveForward.evaluate(levelRightOnBlankBeforeWall);
        assertEquals(pointRightOnBlankBeforeWall.x, levelRightOnBlankBeforeWall.getRobot().getX());
        assertEquals(pointRightOnBlankBeforeWall.y, levelRightOnBlankBeforeWall.getRobot().getY());
        assertEquals(directionRightOnBlankBeforeWall, levelRightOnBlankBeforeWall.getRobot().getDirection());
    }
}