package System.Logic.ProgramArea;

import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Grid;
import GameWorld.Level;
import GameWorldAPI.GameWorld.Result;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Actions.TurnLeftAction;
import RobotCollection.Robot.Direction;
import RobotCollection.Robot.Robot;
import RobotCollection.Utility.GridPosition;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.NotFunctionality;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {

    ConnectionHandler handler;

    Program validProgram, invalidProgram;
    FunctionalBlock moveForwardComplete, moveForward1, turnLeftComplete;
    IfBlock incompleteBlock;
    OperationalBlock not;
    Level level;

    @BeforeEach
    void setUp() {
        level = new Level(new Robot(new GridPosition(1,1), Direction.LEFT),
                new Grid(new Cell[][] {
                        {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
                        {new Cell(CellType.WALL), new Cell(CellType.BLANK), new Cell(CellType.GOAL)},
                        {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
                }));

        handler = new ConnectionHandler();

        moveForwardComplete = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(), level));
        moveForward1 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(), level));
        incompleteBlock = new IfBlock(new CavityFunctionality(level));
        not = new NotBlock(new NotFunctionality(level));
        turnLeftComplete = new FunctionalBlock(new ActionFunctionality(new TurnLeftAction(), level));

        handler.connect(moveForwardComplete, turnLeftComplete.getSubConnectorAt(0));
        handler.connect(not, incompleteBlock.getConditionalSubConnector());
        handler.connect(moveForward1, incompleteBlock.getCavitySubConnector());

        validProgram = new Program(turnLeftComplete);
        invalidProgram = new Program(incompleteBlock);
    }

    @AfterEach
    void tearDown() {
        handler = null;
        moveForwardComplete = null;
        incompleteBlock = null;
        validProgram = null;
        invalidProgram = null;
        not = null;
        moveForward1 = null;
        turnLeftComplete = null;
    }

    @Test
    void program_invalidStart() {
        assertThrows(IllegalArgumentException.class, () -> new Program(null));
    }

    @Test
    void isValidStartBlock() {
        assertTrue(Program.isValidStartBlock(moveForwardComplete));
        assertTrue(Program.isValidStartBlock(new WhileBlock(new CavityFunctionality(level))));
        assertTrue(Program.isValidStartBlock(not));
        assertFalse(Program.isValidStartBlock(null));
    }

    @Test
    void getStartBlock() {
        assertEquals(turnLeftComplete, validProgram.getStartBlock());
        assertEquals(incompleteBlock, invalidProgram.getStartBlock());
    }

    @Test
    void getCurrentBlock() {
        assertEquals(turnLeftComplete, validProgram.getCurrentBlock());
        validProgram.executeStep();
        assertEquals(moveForwardComplete, validProgram.getCurrentBlock());
        validProgram.executeStep();
        assertNull(validProgram.getCurrentBlock());
        validProgram.executeStep();
        assertNull(validProgram.getCurrentBlock());
    }

    @Test
    void getLastResult() {
        assertEquals(Program.DEFAULT_RESULT, validProgram.getLastResult());
        Result result = validProgram.executeStep();
        assertEquals(result, validProgram.getLastResult());
        result = validProgram.executeStep();
        assertEquals(result, validProgram.getLastResult());
        result = validProgram.executeStep();
        assertEquals(result, validProgram.getLastResult());
    }

    @Test
    void executeStep_InvalidProgram() {
        assertThrows(IllegalStateException.class, () -> invalidProgram.executeStep());
    }

    @Test
    void executeStep_ProgramFinished() {
        validProgram.executeStep();
        Result result = validProgram.executeStep();
        assertTrue(validProgram.isFinished());
        assertEquals(result, validProgram.executeStep());
    }

    @Test
    void executeStep_ProgramNotFinished() {
        assertEquals(Result.SUCCESS, validProgram.executeStep());
        assertEquals(moveForwardComplete, validProgram.getCurrentBlock());
        assertEquals(Result.END, validProgram.executeStep());
        assertNull(validProgram.getCurrentBlock());
    }

    @Test
    void isFinished() {
        assertFalse(validProgram.isFinished());
        assertFalse(invalidProgram.isFinished());
        validProgram.executeStep();
        assertFalse(validProgram.isFinished());
        validProgram.executeStep();
        assertTrue(validProgram.isFinished());
    }

    @Test
    void isValidProgram() {
        assertTrue(validProgram.isValidProgram());
        assertFalse(invalidProgram.isValidProgram());
    }

    @Test
    void getSize() {
        assertEquals(2, validProgram.getSize());
        assertEquals(3, invalidProgram.getSize());
    }

    @Test
    void isExecuting() {
        assertFalse(validProgram.isExecuting());
        validProgram.executeStep();
        assertTrue(validProgram.isExecuting());
        validProgram.executeStep();
        assertTrue(validProgram.isExecuting());
    }

    @Test
    void undoProgram_actionDone() {
        validProgram.executeStep();
        Block block = validProgram.getCurrentBlock();
        Result result = validProgram.getLastResult();
        validProgram.undoProgram();
        assertEquals(block.getClass(), validProgram.getCurrentBlock().getClass());
        assertEquals(block.getFunctionality().getClass(), validProgram.getCurrentBlock().getFunctionality().getClass());
        assertEquals(block.getFunctionality().getGameWorld(), validProgram.getCurrentBlock().getFunctionality().getGameWorld());
        assertEquals(result, validProgram.getLastResult());
    }

    @Test
    void undoProgram_noActionDone() {
        validProgram.executeStep();
        Block block = validProgram.getCurrentBlock();
        Result result = validProgram.getLastResult();
        validProgram.executeStep();
        validProgram.undoProgram();
        assertEquals(block, validProgram.getCurrentBlock());
        assertEquals(result, validProgram.getLastResult());
    }

    @Test
    void redoProgram_noUndoDone() {
        validProgram.executeStep();
        Block block = validProgram.getCurrentBlock();
        Result result = validProgram.getLastResult();
        validProgram.redoProgram();
        assertEquals(block, validProgram.getCurrentBlock());
        assertEquals(result, validProgram.getLastResult());
    }

    @Test
    void redoProgram_undoDone() {
        validProgram.executeStep();
        Block block = validProgram.getCurrentBlock();
        Result result = validProgram.getLastResult();
        validProgram.undoProgram();
        validProgram.redoProgram();
        assertEquals(block.getClass(), validProgram.getCurrentBlock().getClass());
        assertEquals(block.getFunctionality().getClass(), validProgram.getCurrentBlock().getFunctionality().getClass());
        assertEquals(block.getFunctionality().getGameWorld(), validProgram.getCurrentBlock().getFunctionality().getGameWorld());
        assertEquals(result, validProgram.getLastResult());
    }

    @Test
    void resetProgram() {
        validProgram.executeStep();
        validProgram.executeStep();
        validProgram.resetProgram();
        assertEquals(validProgram.getStartBlock(), validProgram.getCurrentBlock());
        assertEquals(Program.DEFAULT_RESULT, validProgram.getLastResult());
    }
}