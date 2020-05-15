package System.Logic.ProgramArea;

import GameWorld.Level;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import RobotCollection.Robot.Robot;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Blocks.WhileBlock;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.Logic.CommandHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunProgramCommandTest {

    ProgramArea programArea;
    GameWorldType type;
    GameWorld gameWorld;
    CommandHistory history;
    ProgramCommand command;
    ConnectionHandler handler;
    Block block1, block2, block3;

    @BeforeEach
    void setUp() {
        history = new CommandHistory();
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        programArea = new ProgramArea(gameWorld, history);
        command = new RunProgramCommand(programArea);

        handler = new ConnectionHandler();
        block1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        block2 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        block3 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));

        handler.connect(block2, block1.getSubConnectorAt(0));
        handler.connect(block3, block2.getSubConnectorAt(0));

    }

    @AfterEach
    void tearDown() {
        programArea = null;
        type = null;
        gameWorld = null;
        history = null;
        command = null;
        handler = null;
        block1 = null;
        block2 = null;
        block3 = null;
    }

    @Test
    void execute_invalidProgramArea() {
        assertNull(command.programArea.getProgram());
        assertThrows(IllegalStateException.class, () -> command.execute());

        programArea.addProgram(new WhileBlock());
        assertNotNull(command.programArea.getProgram());
        assertFalse(command.programArea.getProgram().isValidProgram());
        assertThrows(IllegalStateException.class, () -> command.execute());
    }

    @Test
    void execute() {
        Robot robot = ((Level)gameWorld).getRobot().copy();
        programArea.addProgram(block1);
        command.execute();
        assertNotEquals(robot.getDirection(), ((Level)gameWorld).getRobot().getDirection());
        assertDoesNotThrow(command::undo);
    }

    @Test
    void undo_noSnapshots() {
        assertThrows(IllegalStateException.class, () -> command.undo());
    }

    @Test
    void undo() {
        programArea.addProgram(block1);
        int x = ((Level)gameWorld).getRobot().getGridPosition().getX();
        int y = ((Level)gameWorld).getRobot().getGridPosition().getY();
        String direction = ((Level)gameWorld).getRobot().getDirection();

        command.execute();
        // coordinates won't have changed because of the blocks
        assertNotEquals(direction, ((Level)gameWorld).getRobot().getDirection());

        command.undo();
        assertEquals(x, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(y, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(direction, ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void executeTask() {
        Robot robot = ((Level)gameWorld).getRobot().copy();
        programArea.addProgram(block1);
        command.executeTask();
        assertNotEquals(robot.getDirection(), ((Level)gameWorld).getRobot().getDirection());
    }
}