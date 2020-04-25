package System.Logic.ProgramArea;

import GameWorld.Level;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.LevelInitializer;
import RobotCollection.Robot.Direction;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Blocks.WhileBlock;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.Logic.CommandHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramAreaTest {

    ProgramArea pa0, pa1, pa2, paInvalidProgram;
    Block start1, start2a, start2b, block1, block2, start1Under;
    GameWorldType type;
    CommandHistory history;
    GameWorld gameWorld;
    ConnectionHandler handler;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        history = new CommandHistory();
        gameWorld = type.createNewGameWorld();

        handler = new ConnectionHandler();
        block1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        block2 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        handler.connect(block2, block1.getSubConnectorAt(0));

        pa0 = new ProgramArea(gameWorld, history);
        pa1 = new ProgramArea(gameWorld, history);
        pa2 = new ProgramArea(gameWorld, history);
        paInvalidProgram = new ProgramArea(gameWorld, history);

        start1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        start1Under = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        start2a = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        start2b = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2)));

        pa1.addProgram(start1);
        handler.connect(start1Under, start1.getSubConnectorAt(0));
        pa2.addProgram(start2a);
        pa2.addProgram(start2b);
        paInvalidProgram.addProgram(new WhileBlock());
    }

    @AfterEach
    void tearDown() {
        pa0 = null;
        pa1 = null;
        pa2 = null;
        start1 = null;
        start2a = null;
        start2b = null;
        block1 = null;
        block2 = null;
        type = null;
        gameWorld = null;
        handler = null;
        paInvalidProgram = null;
        history = null;
    }

    @Test
    void getGameWorld() {
        assertEquals(gameWorld, pa0.getGameWorld());
        assertEquals(gameWorld, pa1.getGameWorld());
        assertEquals(gameWorld, pa2.getGameWorld());
    }

    @Test
    void getProgram() {
        assertNull(pa0.getProgram());
        assertEquals(start1, pa1.getProgram().getStartBlock());
        assertNull(pa2.getProgram());
    }

    @Test
    void getAllBlocksCount() {
        assertEquals(0, pa0.getAllBlocksCount());
        assertEquals(2, pa1.getAllBlocksCount());
        assertEquals(2, pa2.getAllBlocksCount());
    }

    @Test
    void getNextBlockInProgram() {
        assertNull(pa0.getNextBlockInProgram());
        assertEquals(start1, pa1.getNextBlockInProgram());
        pa1.runProgramStep();
        assertEquals(start1Under, pa1.getNextBlockInProgram());
        assertNull(pa2.getNextBlockInProgram());
    }

    @Test
    void addProgram_InvalidProgram() {
        assertThrows(IllegalArgumentException.class, () -> pa1.addProgram(null));
    }

    @Test
    void addProgram_ValidProgram() {
        Block block = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        pa0.addProgram(block); // No error may be thrown
        assertEquals(block, pa0.getProgram().getStartBlock());
    }

    @Test
    void addProgram_alreadyIn() {
        pa2.addProgram(start2a);
        assertEquals(2, pa2.getAllBlocksCount());
    }

    @Test
    void addHighestAsProgram_invalid() {
        assertThrows(IllegalArgumentException.class, () -> pa1.addHighestAsProgram(null));
    }

    @Test
    void addHighestAsProgram_valid() {
        pa0.addHighestAsProgram(block2);
        assertEquals(2, pa0.getAllBlocksCount());
    }

    @Test
    void deleteProgram() {
        assertEquals(2, pa2.getAllBlocksCount());
        pa2.deleteProgram(start1);
        assertEquals(2, pa2.getAllBlocksCount());
        pa2.deleteProgram(start2a);
        assertEquals(1, pa2.getAllBlocksCount());
        pa2.deleteProgram(start2b);
        assertEquals(0, pa2.getAllBlocksCount());
    }


    @Test
    void runProgramStep_invalidNbPrograms() {
        assertThrows(IllegalStateException.class, () -> pa0.runProgramStep());
        assertThrows(IllegalStateException.class, () -> pa2.runProgramStep());
    }

    @Test
    void runProgramStep_invalidProgram() {
        assertThrows(IllegalStateException.class, () -> paInvalidProgram.runProgramStep());
    }

    @Test
    void runProgramStep() {
        pa1.runProgramStep();
        assertEquals(start1Under, pa1.getNextBlockInProgram());
        assertEquals(Direction.DOWN.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void resetProgramArea() {
        pa1.runProgramStep();
        pa1.resetProgramArea();
        assertEquals(start1, pa1.getNextBlockInProgram());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
    }
}