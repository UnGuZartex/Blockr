package System.Logic;

import GameWorld.Level;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Actions.TurnRightAction;
import GameWorldUtility.LevelInitializer;
import RobotCollection.Robot.Direction;
import RobotCollection.Robot.Robot;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.Logic.ProgramArea.*;
import System.Logic.ProgramArea.Handlers.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandHistoryTest {

    ProgramArea programArea;
    GameWorldType type;
    GameWorld gameWorld;
    CommandHistory history;
    ConnectionHandler handler;
    Block block1, block2, block3, block4;

    @BeforeEach
    void setUp() {
        history = new CommandHistory();
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        programArea = new ProgramArea(gameWorld, history);

        handler = new ConnectionHandler();
        block1 = new FunctionalBlock(new ActionFunctionality((TurnRightAction)type.getAllActions().get(2)));
        block2 = new FunctionalBlock(new ActionFunctionality((TurnRightAction)type.getAllActions().get(2)));
        block3 = new FunctionalBlock(new ActionFunctionality((TurnRightAction)type.getAllActions().get(2)));
        block4 = new FunctionalBlock(new ActionFunctionality((MoveForwardAction)type.getAllActions().get(0)));

        handler.connect(block2, block1.getSubConnectorAt(0));
        handler.connect(block3, block2.getSubConnectorAt(0));
        handler.connect(block4, block3.getSubConnectorAt(0));

        programArea.addProgram(block1);
    }

    @AfterEach
    void tearDown() {
        programArea = null;
        type = null;
        gameWorld = null;
        history = null;
        handler = null;
        block1 = null;
        block2 = null;
        block3 = null;
        block4 = null;
    }

    @Test
    void execute_invalidCommand() {
        assertThrows(IllegalArgumentException.class, () -> history.execute(null));
    }

    @Test
    void execute() {
        Robot robot = ((Level)gameWorld).getRobot();
        
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), robot.getDirection());

        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.UP.name(), robot.getDirection());

        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), robot.getDirection());

        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), robot.getDirection());

        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(2, robot.getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), robot.getDirection());

        history.execute(new ResetProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void execute_noRedo() {
        history.execute(new RunProgramCommand(programArea));
        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());

        history.redo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void execute_undo() {
        history.execute(new RunProgramCommand(programArea));
        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), ((Level)gameWorld).getRobot().getDirection());

        history.undo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void undo_noUndo() {
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.undo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void undo() {
        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)gameWorld).getRobot().getDirection());
        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), ((Level)gameWorld).getRobot().getDirection());

        history.undo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.undo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void redo_noRedo() {
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.redo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void redo_afterExecute() {
        history.execute(new RunProgramCommand(programArea));
        history.execute(new RunProgramCommand(programArea));
        history.execute(new ResetProgramCommand(programArea));
        history.undo();
        history.undo();
        history.undo();
        history.execute(new ResetProgramCommand(programArea));

        history.redo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void redo() {
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)gameWorld).getRobot().getDirection());
        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.execute(new RunProgramCommand(programArea));
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), ((Level)gameWorld).getRobot().getDirection());
        history.undo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.undo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)gameWorld).getRobot().getDirection());
        assertEquals(1, history.undoStack.size());
        history.undo();

        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());

        history.redo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)gameWorld).getRobot().getDirection());
        history.redo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.redo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), ((Level)gameWorld).getRobot().getDirection());
        history.redo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), ((Level)gameWorld).getRobot().getDirection());
        history.undo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)gameWorld).getRobot().getDirection());
        history.undo();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)gameWorld).getRobot().getDirection());
    }
}