package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Factory.IfBlockFactory;
import System.BlockStructure.Blocks.Factory.MoveForwardBlockFactory;
import System.BlockStructure.Blocks.Factory.NotBlockFactory;
import System.BlockStructure.Blocks.Factory.TurnLeftBlockFactory;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Blocks.OperationalBlock;
import System.GameState.GameState;
import System.GameWorld.Cell;
import System.GameWorld.CellType;
import System.GameWorld.Direction;
import System.GameWorld.Level.Level;
import Utility.Position;
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

    @BeforeEach
    void setUp() {
        GameState.setCurrentLevel(new Level(new Position(1,1), Direction.LEFT,
                new Cell[][] {
                        {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
                        {new Cell(CellType.WALL), new Cell(CellType.BLANK), new Cell(CellType.GOAL)},
                        {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
                }));

        handler = new ConnectionHandler();
        IfBlockFactory ifFactory = new IfBlockFactory();
        MoveForwardBlockFactory mfFactory = new MoveForwardBlockFactory();
        NotBlockFactory notFactory = new NotBlockFactory();
        TurnLeftBlockFactory tlFactory = new TurnLeftBlockFactory();
        moveForwardComplete = mfFactory.createBlock();
        moveForward1 = mfFactory.createBlock();
        incompleteBlock = ifFactory.createBlock();
        not = notFactory.createBlock();
        turnLeftComplete = tlFactory.createBlock();

        handler.connect(moveForwardComplete, turnLeftComplete.getSubConnectorAt(0));
        handler.connect(not, incompleteBlock.getConditionalSubConnector());
        handler.connect(moveForward1, incompleteBlock.getCavitySubConnector());

        validProgram = new Program(turnLeftComplete);
        invalidProgram = new Program(incompleteBlock);
    }

    @AfterEach
    void tearDown() {
        GameState.setCurrentLevel(null);

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
    void executeStep_InvalidProgram() {
        assertThrows(IllegalStateException.class, () -> { invalidProgram.executeStep(); });
    }

    @Test
    void executeStep_NoLevelLoaded() {
        GameState.setCurrentLevel(null);
        assertThrows(IllegalStateException.class, () -> { validProgram.executeStep(); });
    }

    @Test
    void executeStep_ProgramFinished() {
        validProgram.executeStep();
        validProgram.executeStep();
        validProgram.executeStep();
        assertTrue(validProgram.isFinished());
        assertTrue(validProgram.hasWon());
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
        assertEquals(turnLeftComplete, validProgram.getCurrentBlock());
        validProgram.executeStep();
        assertEquals(turnLeftComplete, validProgram.getCurrentBlock());
    }

    @Test
    void resetProgram() {
        validProgram.executeStep();
        assertTrue(turnLeftComplete.hasAlreadyRan());
        assertFalse(moveForwardComplete.hasAlreadyRan());
        assertFalse(validProgram.isFinished());
        validProgram.executeStep();
        assertTrue(validProgram.getStartBlock().hasAlreadyRan());
        assertTrue(moveForwardComplete.hasAlreadyRan());
        assertTrue(validProgram.isFinished());

        validProgram.resetProgram();
        assertFalse(validProgram.getStartBlock().hasAlreadyRan());
        assertFalse(moveForwardComplete.hasAlreadyRan());
        assertFalse(validProgram.isFinished());
    }

    @Test
    void hasWon() {
        assertFalse(validProgram.hasWon());
        assertFalse(invalidProgram.hasWon());
        validProgram.executeStep();
        validProgram.executeStep();
        assertTrue(validProgram.hasWon());
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
}