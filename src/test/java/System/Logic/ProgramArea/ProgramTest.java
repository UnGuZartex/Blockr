package System.Logic.ProgramArea;

import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Grid;
import GameWorld.Level;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldAPI.History.Snapshot;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Actions.TurnLeftAction;
import GameWorldUtility.LevelInitializer;
import RobotCollection.Robot.Direction;
import RobotCollection.Robot.Robot;
import RobotCollection.Utility.GridPosition;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramTest {

    ConnectionHandler handler;
    GameWorldType type;
    Program validProgram, invalidProgram;
    FunctionalBlock moveForwardComplete, moveForward1, turnLeftComplete;
    IfBlock incompleteBlock;
    OperationalBlock not;
    Level level;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        level = new Level(new Robot(new GridPosition(1,1), Direction.LEFT),
                new Grid(new Cell[][] {
                        {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
                        {new Cell(CellType.WALL), new Cell(CellType.BLANK), new Cell(CellType.GOAL)},
                        {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
                }));

        handler = new ConnectionHandler();

        moveForwardComplete = new FunctionalBlock(new ActionFunctionality((MoveForwardAction)type.getAllActions().get(0)));
        moveForward1 = new FunctionalBlock(new ActionFunctionality((MoveForwardAction)type.getAllActions().get(0)));
        incompleteBlock = new IfBlock();
        not = new NotBlock();
        turnLeftComplete = new FunctionalBlock(new ActionFunctionality((TurnLeftAction)type.getAllActions().get(1)));

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
        assertTrue(Program.isValidStartBlock(new WhileBlock()));
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
        validProgram.executeStep(level);
        assertEquals(moveForwardComplete, validProgram.getCurrentBlock());
        validProgram.executeStep(level);
        assertNull(validProgram.getCurrentBlock());
        validProgram.executeStep(level);
        assertNull(validProgram.getCurrentBlock());
    }

    @Test
    void getLastResult() {
        assertEquals(Program.DEFAULT_RESULT, validProgram.getLastResult());
        validProgram.executeStep(level);
        assertEquals(Result.SUCCESS, validProgram.getLastResult());
        validProgram.executeStep(level);
        assertEquals(Result.END, validProgram.getLastResult());
        validProgram.executeStep(level);
        assertEquals(Result.END, validProgram.getLastResult());
    }

    @Test
    void executeStep_InvalidProgram() {
        assertThrows(IllegalStateException.class, () -> invalidProgram.executeStep(level));
    }

    @Test
    void executeStep_ProgramFinished() {
        validProgram.executeStep(level);
        validProgram.executeStep(level);
        assertTrue(validProgram.isFinished());
        Block block = validProgram.getCurrentBlock();
        Result result = validProgram.getLastResult();
        validProgram.executeStep(level);
        assertEquals(block, validProgram.getCurrentBlock());
        assertEquals(result, validProgram.getLastResult());
    }

    @Test
    void executeStep_ProgramNotFinished() {
        assertEquals(Result.SUCCESS, validProgram.getLastResult());
        assertEquals(turnLeftComplete, validProgram.getCurrentBlock());
        validProgram.executeStep(level);
        assertEquals(Result.SUCCESS, validProgram.getLastResult());
        assertEquals(moveForwardComplete, validProgram.getCurrentBlock());
        validProgram.executeStep(level);
        assertEquals(Result.END, validProgram.getLastResult());
        assertNull(validProgram.getCurrentBlock());
    }

    @Test
    void isFinished() {
        assertFalse(validProgram.isFinished());
        assertFalse(invalidProgram.isFinished());
        validProgram.executeStep(level);
        assertFalse(validProgram.isFinished());
        validProgram.executeStep(level);
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
    void getSizeOfBlock() {
        assertEquals(1, Program.getSizeOfBlock(moveForwardComplete));
        assertEquals(2, Program.getSizeOfBlock(turnLeftComplete));
        assertEquals(3, Program.getSizeOfBlock(incompleteBlock));
        assertEquals(1, Program.getSizeOfBlock(moveForward1));
        assertEquals(1, Program.getSizeOfBlock(not));
    }

    @Test
    void resetProgram() {
        validProgram.executeStep(level);
        validProgram.executeStep(level);
        validProgram.resetProgram();
        assertEquals(validProgram.getStartBlock(), validProgram.getCurrentBlock());
        assertEquals(Program.DEFAULT_RESULT, validProgram.getLastResult());
    }

    @Test
    void snapshot_start() {
        Snapshot snapshot = validProgram.createSnapshot();
        validProgram.executeStep(level);
        validProgram.executeStep(level);
        validProgram.loadSnapshot(snapshot);
        assertEquals(Result.SUCCESS, validProgram.getLastResult());
        assertEquals(turnLeftComplete, validProgram.getCurrentBlock());
    }

    @Test
    void snapshot_midExecution() {
        validProgram.executeStep(level);
        Snapshot snapshot = validProgram.createSnapshot();
        validProgram.executeStep(level);
        validProgram.loadSnapshot(snapshot);
        assertEquals(Result.SUCCESS, validProgram.getLastResult());
        assertEquals(moveForwardComplete, validProgram.getCurrentBlock());
    }

    @Test
    void snapshot_finished() {
        validProgram.executeStep(level);
        validProgram.executeStep(level);
        assertTrue(validProgram.isFinished());
        Snapshot snapshot = validProgram.createSnapshot();
        validProgram.loadSnapshot(snapshot);
        assertEquals(Result.END, validProgram.getLastResult());
        assertNull(validProgram.getCurrentBlock());
    }
}