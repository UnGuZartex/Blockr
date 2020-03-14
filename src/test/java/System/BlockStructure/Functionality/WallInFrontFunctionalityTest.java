package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Factory.WallInFrontBlockFactory;
import System.BlockStructure.Blocks.StatementBlock;
import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import System.GameWorld.Level.Level;
import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WallInFrontFunctionalityTest {

    private WallInFrontFunctionality functionality;
    private StatementBlock block1, block2;

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
        WallInFrontBlockFactory factory = new WallInFrontBlockFactory();
        block1 = factory.createBlock();
        block2 = factory.createBlock();
        functionality = (WallInFrontFunctionality) block1.getFunctionality();

        /* Simple field has only one cell */
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
        block1 = null;
        block2 = null;

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
    void setBlock() {
        functionality = new WallInFrontFunctionality();
        assertNull(functionality.block);
        functionality.setBlock(block1);
        assertEquals(block1, functionality.block);
        functionality.setBlock(block2);
        assertEquals(block1, functionality.block);
    }

    @Test
    void getEvaluation() {
        assertFalse(functionality.getEvaluation());
        functionality.evaluate(levelUpOnBlankBeforeWall);
        assertTrue(functionality.getEvaluation());

        functionality = new WallInFrontFunctionality();
        assertFalse(functionality.getEvaluation());
        functionality.evaluate(levelDownOnGoalBeforeBlank);
        assertFalse(functionality.getEvaluation());

        functionality = new WallInFrontFunctionality();
        assertFalse(functionality.getEvaluation());
        functionality.evaluate(levelLeftOnGoalBeforeGoal);
        assertFalse(functionality.getEvaluation());

        functionality = new WallInFrontFunctionality();
        assertFalse(functionality.getEvaluation());
        functionality.evaluate(levelRightOnBlankBeforeWall);
        assertTrue(functionality.getEvaluation());

        functionality = new WallInFrontFunctionality();
        assertFalse(functionality.getEvaluation());
        functionality.evaluate(levelSimpleBlankUp);
        assertFalse(functionality.getEvaluation());

        functionality = new WallInFrontFunctionality();
        assertFalse(functionality.getEvaluation());
        functionality.evaluate(levelSimpleGoalDown);
        assertFalse(functionality.getEvaluation());

        functionality = new WallInFrontFunctionality();
        assertFalse(functionality.getEvaluation());
        functionality.evaluate(levelSimpleGoalLeft);
        assertFalse(functionality.getEvaluation());

        functionality = new WallInFrontFunctionality();
        assertFalse(functionality.getEvaluation());
        functionality.evaluate(levelSimpleBlankRight);
        assertFalse(functionality.getEvaluation());
    }

    @Test
    void evaluate() {
        functionality.evaluate(levelUpOnBlankBeforeWall);
        assertTrue(functionality.evaluation);

        functionality.evaluate(levelDownOnGoalBeforeBlank);
        assertFalse(functionality.evaluation);

        functionality.evaluate(levelLeftOnGoalBeforeGoal);
        assertFalse(functionality.evaluation);

        functionality.evaluate(levelRightOnBlankBeforeWall);
        assertTrue(functionality.evaluation);

        functionality.evaluate(levelSimpleBlankUp);
        assertFalse(functionality.evaluation);

        functionality.evaluate(levelSimpleGoalDown);
        assertFalse(functionality.evaluation);

        functionality.evaluate(levelSimpleGoalLeft);
        assertFalse(functionality.evaluation);

        functionality.evaluate(levelSimpleBlankRight);
        assertFalse(functionality.evaluation);
    }
}