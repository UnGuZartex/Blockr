package System.BlockStructure.Functionality;

import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import System.GameWorld.Level.Level;
import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveForwardFunctionalityTest {

    private MoveForwardFunctionality moveForward;

    private Level levelUpOnBlankBeforeWall, levelDownOnGoalBeforeBlank,
            levelLeftOnGoalBeforeGoal, levelRightOnBlankBeforeWall;
    private Position PositionUpOnBlankBeforeWall, PositionDownOnGoalBeforeBlank,
            PositionLeftOnGoalBeforeGoal, PositionRightOnBlankBeforeWall;
    private Direction directionUpOnBlankBeforeWall, directionDownOnGoalBeforeBlank,
            directionLeftOnGoalBeforeGoal, directionRightOnBlankBeforeWall;
    private Cell[][] cellsUpOnBlankBeforeWall, cellsDownOnGoalBeforeBlank,
            cellsLeftOnGoalBeforeGoal, cellsRightOnBlankBeforeWall;

    private Level levelSimpleBlankUp, levelSimpleGoalDown, levelSimpleGoalLeft, levelSimpleBlankRight;
    private Position PositionSimpleBlankUp, PositionSimpleGoalDown, PositionSimpleGoalLeft, PositionSimpleBlankRight;
    private Direction directionSimpleBlankUp, directionSimpleGoalDown, directionSimpleGoalLeft, directionSimpleBlankRight;
    private Cell[][] cellsSimpleBlankUp, cellsSimpleGoalDown, cellsSimpleGoalLeft, cellsSimpleBlankRight;

    @BeforeEach
    void setUp() {
        moveForward = new MoveForwardFunctionality();

        PositionSimpleBlankUp = new Position(0,0);
        PositionSimpleGoalDown = new Position(0,0);
        PositionSimpleGoalLeft = new Position(0,0);
        PositionSimpleBlankRight = new Position(0,0);
        PositionUpOnBlankBeforeWall = new Position(1,1);
        PositionDownOnGoalBeforeBlank = new Position(1,1);
        PositionLeftOnGoalBeforeGoal = new Position(1,1);
        PositionRightOnBlankBeforeWall = new Position(1,1);

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

        levelSimpleBlankUp = new Level(PositionSimpleBlankUp, directionSimpleBlankUp, cellsSimpleBlankUp);
        levelSimpleGoalDown = new Level(PositionSimpleGoalDown, directionSimpleGoalDown, cellsSimpleGoalDown);
        levelSimpleGoalLeft = new Level(PositionSimpleGoalLeft, directionSimpleGoalLeft, cellsSimpleGoalLeft);
        levelSimpleBlankRight = new Level(PositionSimpleBlankRight, directionSimpleBlankRight, cellsSimpleBlankRight);
        levelUpOnBlankBeforeWall = new Level(PositionUpOnBlankBeforeWall, directionUpOnBlankBeforeWall, cellsUpOnBlankBeforeWall);
        levelDownOnGoalBeforeBlank = new Level(PositionDownOnGoalBeforeBlank, directionDownOnGoalBeforeBlank, cellsDownOnGoalBeforeBlank);
        levelLeftOnGoalBeforeGoal = new Level(PositionLeftOnGoalBeforeGoal, directionLeftOnGoalBeforeGoal, cellsLeftOnGoalBeforeGoal);
        levelRightOnBlankBeforeWall = new Level(PositionRightOnBlankBeforeWall, directionRightOnBlankBeforeWall, cellsRightOnBlankBeforeWall);
    }

    @AfterEach
    void tearDown() {
        moveForward = null;

        PositionSimpleBlankUp = null;
        PositionSimpleGoalDown = null;
        PositionSimpleGoalLeft = null;
        PositionSimpleBlankRight = null;
        PositionUpOnBlankBeforeWall = null;
        PositionDownOnGoalBeforeBlank = null;
        PositionLeftOnGoalBeforeGoal = null;
        PositionRightOnBlankBeforeWall = null;

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
        assertEquals(PositionSimpleBlankUp.getX(), levelSimpleBlankUp.getRobot().getX());
        assertEquals(PositionSimpleBlankUp.getY(), levelSimpleBlankUp.getRobot().getY());
        assertEquals(directionSimpleBlankUp, levelSimpleBlankUp.getRobot().getDirection());

        moveForward.evaluate(levelSimpleGoalDown);
        assertEquals(PositionSimpleGoalDown.getX(), levelSimpleGoalDown.getRobot().getX());
        assertEquals(PositionSimpleGoalDown.getY(), levelSimpleGoalDown.getRobot().getY());
        assertEquals(directionSimpleGoalDown, levelSimpleGoalDown.getRobot().getDirection());

        moveForward.evaluate(levelSimpleGoalLeft);
        assertEquals(PositionSimpleGoalLeft.getX(), levelSimpleGoalLeft.getRobot().getX());
        assertEquals(PositionSimpleGoalLeft.getY(), levelSimpleGoalLeft.getRobot().getY());
        assertEquals(directionSimpleGoalLeft, levelSimpleGoalLeft.getRobot().getDirection());

        moveForward.evaluate(levelSimpleBlankRight);
        assertEquals(PositionSimpleBlankRight.getX(), levelSimpleBlankRight.getRobot().getX());
        assertEquals(PositionSimpleBlankRight.getY(), levelSimpleBlankRight.getRobot().getY());
        assertEquals(directionSimpleBlankRight, levelSimpleBlankRight.getRobot().getDirection());

        moveForward.evaluate(levelUpOnBlankBeforeWall);
        assertEquals(PositionUpOnBlankBeforeWall.getX(), levelUpOnBlankBeforeWall.getRobot().getX());
        assertEquals(PositionUpOnBlankBeforeWall.getY(), levelUpOnBlankBeforeWall.getRobot().getY());
        assertEquals(directionUpOnBlankBeforeWall, levelUpOnBlankBeforeWall.getRobot().getDirection());

        moveForward.evaluate(levelDownOnGoalBeforeBlank);
        assertEquals(PositionDownOnGoalBeforeBlank.getX(), levelDownOnGoalBeforeBlank.getRobot().getX());
        assertEquals(PositionDownOnGoalBeforeBlank.getY() + 1, levelDownOnGoalBeforeBlank.getRobot().getY());
        assertEquals(directionDownOnGoalBeforeBlank, levelDownOnGoalBeforeBlank.getRobot().getDirection());

        moveForward.evaluate(levelLeftOnGoalBeforeGoal);
        assertEquals(PositionLeftOnGoalBeforeGoal.getX() - 1, levelLeftOnGoalBeforeGoal.getRobot().getX());
        assertEquals(PositionLeftOnGoalBeforeGoal.getY(), levelLeftOnGoalBeforeGoal.getRobot().getY());
        assertEquals(directionLeftOnGoalBeforeGoal, levelLeftOnGoalBeforeGoal.getRobot().getDirection());

        moveForward.evaluate(levelRightOnBlankBeforeWall);
        assertEquals(PositionRightOnBlankBeforeWall.getX(), levelRightOnBlankBeforeWall.getRobot().getX());
        assertEquals(PositionRightOnBlankBeforeWall.getY(), levelRightOnBlankBeforeWall.getRobot().getY());
        assertEquals(directionRightOnBlankBeforeWall, levelRightOnBlankBeforeWall.getRobot().getDirection());
    }
}