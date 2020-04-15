package System.Logic.ProgramArea;

import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Grid;
import GameWorld.Level;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Actions.TurnLeftAction;
import RobotCollection.Robot.Robot;
import RobotCollection.Utility.Direction;
import RobotCollection.Utility.GridPosition;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Blocks.NotBlock;
import System.BlockStructure.Blocks.OperationalBlock;
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
    void executeStep_InvalidProgram() {
        assertThrows(IllegalStateException.class, () -> { invalidProgram.executeStep(); });
    }

    @Test
    void executeStep_ProgramFinished() {
        validProgram.executeStep();
        validProgram.executeStep();
        assertTrue(validProgram.isFinished());
        validProgram.executeStep();
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
        assertNull(validProgram.getCurrentBlock());
        validProgram.executeStep();
        assertNull(validProgram.getCurrentBlock());
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