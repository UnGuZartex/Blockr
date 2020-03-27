package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.ConditionalBlock;
import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Blocks.WallInFrontBlock;
import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import System.GameWorld.Level.Level;
import System.Logic.ProgramArea.ConnectionHandler;
import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CavityFunctionalityTest {

    CavityFunctionality ifFunc;
    IfBlock if1, if2, block;
    ConditionalBlock cond1;

    private Level levelUpOnBlankBeforeWall, levelDownOnGoalBeforeBlank,
            levelLeftOnGoalBeforeGoal, levelRightOnBlankBeforeWall;
    private Position PositionUpOnBlankBeforeWall, PositionDownOnGoalBeforeBlank,
            PositionLeftOnGoalBeforeGoal, PositionRightOnBlankBeforeWall;
    private Direction directionUpOnBlankBeforeWall, directionDownOnGoalBeforeBlank,
            directionLeftOnGoalBeforeGoal, directionRightOnBlankBeforeWall;
    private Cell[][] cellsUpOnBlankBeforeWall, cellsDownOnGoalBeforeBlank,
            cellsLeftOnGoalBeforeGoal, cellsRightOnBlankBeforeWall;

    @BeforeEach
    void setUp() {
        if1 = new IfBlock();
        if2 = new IfBlock();
        ifFunc = new CavityFunctionality();
        block = new IfBlock();

        cond1 = new WallInFrontBlock();

        ConnectionHandler handler = new ConnectionHandler();
        handler.connect(cond1, if1.getConditionalSubConnector());

        PositionUpOnBlankBeforeWall = new Position(1,1);
        PositionDownOnGoalBeforeBlank = new Position(1,1);
        PositionLeftOnGoalBeforeGoal = new Position(1,1);
        PositionRightOnBlankBeforeWall = new Position(1,1);

        directionUpOnBlankBeforeWall = Direction.UP;
        directionDownOnGoalBeforeBlank = Direction.DOWN;
        directionLeftOnGoalBeforeGoal = Direction.LEFT;
        directionRightOnBlankBeforeWall = Direction.RIGHT;

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

        levelUpOnBlankBeforeWall = new Level(PositionUpOnBlankBeforeWall, directionUpOnBlankBeforeWall, cellsUpOnBlankBeforeWall);
        levelDownOnGoalBeforeBlank = new Level(PositionDownOnGoalBeforeBlank, directionDownOnGoalBeforeBlank, cellsDownOnGoalBeforeBlank);
        levelLeftOnGoalBeforeGoal = new Level(PositionLeftOnGoalBeforeGoal, directionLeftOnGoalBeforeGoal, cellsLeftOnGoalBeforeGoal);
        levelRightOnBlankBeforeWall = new Level(PositionRightOnBlankBeforeWall, directionRightOnBlankBeforeWall, cellsRightOnBlankBeforeWall);

    }

    @AfterEach
    void tearDown() {
        if1 = null;
        cond1 = null;
        ifFunc = null;

        PositionUpOnBlankBeforeWall = null;
        PositionDownOnGoalBeforeBlank = null;
        PositionLeftOnGoalBeforeGoal = null;
        PositionRightOnBlankBeforeWall = null;

        directionUpOnBlankBeforeWall = null;
        directionDownOnGoalBeforeBlank = null;
        directionLeftOnGoalBeforeGoal = null;
        directionRightOnBlankBeforeWall = null;

        cellsUpOnBlankBeforeWall = null;
        cellsDownOnGoalBeforeBlank = null;
        cellsLeftOnGoalBeforeGoal = null;
        cellsRightOnBlankBeforeWall = null;

        levelUpOnBlankBeforeWall = null;
        levelDownOnGoalBeforeBlank = null;
        levelLeftOnGoalBeforeGoal = null;
        levelRightOnBlankBeforeWall = null;
    }

    @Test
    void setBlock() {
        assertNull(ifFunc.block);
        ifFunc.setBlock(block);
        assertEquals(block, ifFunc.block);
        ifFunc.setBlock(if1);
        assertEquals(block, ifFunc.block);
    }

    @Test
    void getEvaluation() {
        assertFalse(if1.getFunctionality().getEvaluation());
        if1.getFunctionality().evaluation = true;
        assertTrue(if1.getFunctionality().getEvaluation());
    }

    @Test
    void evaluate() {
        ifFunc.evaluate(levelDownOnGoalBeforeBlank);
        assertFalse(ifFunc.getEvaluation());

        block.getFunctionality().evaluate(levelUpOnBlankBeforeWall);
        assertFalse(block.getFunctionality().getEvaluation());

        if1.getFunctionality().evaluate(levelRightOnBlankBeforeWall);
        assertTrue(if1.getFunctionality().getEvaluation());

        if1.getFunctionality().evaluate(levelLeftOnGoalBeforeGoal);
        assertFalse(if1.getFunctionality().getEvaluation());
    }
}