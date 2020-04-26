package Controllers.ControllerClasses;

import GameWorld.Grid;
import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Level;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Actions.TurnLeftAction;
import GameWorldUtility.Actions.TurnRightAction;
import GameWorldUtility.LevelInitializer;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import RobotCollection.Robot.Direction;
import RobotCollection.Robot.Robot;
import RobotCollection.Utility.GridPosition;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.CommandHistory;
import System.Logic.ProgramArea.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HistoryControllerTest {

    HistoryController controller;
    ProgramArea programArea;
    CommandHistory history;
    PABlockHandler paBlockHandler;
    Robot robot;
    Grid grid;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        robot = new Robot(new GridPosition(1,1), Direction.LEFT);
        grid = new Grid(new Cell[][] {
                {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
                {new Cell(CellType.WALL), new Cell(CellType.BLANK), new Cell(CellType.GOAL)},
                {new Cell(CellType.WALL), new Cell(CellType.WALL), new Cell(CellType.WALL)},
        });
        gameWorld = new Level(robot, grid);

        moveForward = new FunctionalBlock(new ActionFunctionality((MoveForwardAction) type.getAllActions().get(0)));
        turnLeft = new FunctionalBlock(new ActionFunctionality((TurnLeftAction) type.getAllActions().get(1)));
        turnRight = new FunctionalBlock(new ActionFunctionality((TurnRightAction) type.getAllActions().get(2)));
        wallInFront = new PredicateBlock(new PredicateFunctionality((WallInFrontPredicate) type.getAllPredicates().get(0)));
        notBlock = new NotBlock();
        whileBlock = new WhileBlock();
        ifBlock = new IfBlock();

        history = new CommandHistory();
        programArea = new ProgramArea(gameWorld, history);
        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)), programArea);
        controller = new HistoryController(history, programArea);
    }

    @AfterEach
    void tearDown() {
        history = null;
        type = null;
        robot = null;
        grid = null;
        gameWorld = null;
        moveForward = null;
        turnLeft = null;
        turnRight = null;
        wallInFront = null;
        notBlock = null;
        whileBlock = null;
        ifBlock = null;
    }

    @Test
    void historyController_invalidHistory() {
        assertThrows(IllegalArgumentException.class, () -> new HistoryController(null, programArea));
    }

    @Test
    void historyController_invalidProgramArea() {
        assertThrows(IllegalArgumentException.class, () -> new HistoryController(history, null));
    }

    @Test
    void execute_run() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), robot.getDirection());
        assertEquals(whileBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.execute(new RunProgramCommand(programArea));
        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), robot.getDirection());
        assertEquals(turnLeft, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.execute(new RunProgramCommand(programArea));
        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), robot.getDirection());
        assertEquals(whileBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.execute(new RunProgramCommand(programArea));
        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), robot.getDirection());
        assertEquals(moveForward, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.execute(new RunProgramCommand(programArea));
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(2, robot.getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), robot.getDirection());
        assertNull(programArea.getNextBlockInProgram());
        assertEquals(Result.END, programArea.getProgram().getLastResult());
    }

    @Test
    void execute_reset() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        controller.execute(new RunProgramCommand(programArea));
        controller.execute(new RunProgramCommand(programArea));
        controller.execute(new RunProgramCommand(programArea));
        controller.execute(new RunProgramCommand(programArea));
        controller.execute(new RunProgramCommand(programArea));
        controller.execute(new RunProgramCommand(programArea));
        controller.execute(new RunProgramCommand(programArea));
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(2, robot.getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), robot.getDirection());
        assertNull(programArea.getNextBlockInProgram());
        assertEquals(Result.END, programArea.getProgram().getLastResult());

        controller.execute(new ResetProgramCommand(programArea));
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)programArea.getGameWorld()).getRobot().getDirection());
        assertEquals(whileBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());
    }

    @Test
    void executeProgramRunCommand() {
        CavityBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, ifBlock.getSubConnectorAt(0));

        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), robot.getDirection());
        assertEquals(ifBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.executeProgramRunCommand();
        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), robot.getDirection());
        assertEquals(turnLeft, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.executeProgramRunCommand();
        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), robot.getDirection());
        assertEquals(moveForward, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.executeProgramRunCommand();
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(2, robot.getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), robot.getDirection());
        assertNull(programArea.getNextBlockInProgram());
        assertEquals(Result.END, programArea.getProgram().getLastResult());
    }

    @Test
    void executeProgramResetCommand() {
        CavityBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, ifBlock.getSubConnectorAt(0));

        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), robot.getDirection());
        assertEquals(ifBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(2, robot.getGridPosition().getY());
        assertEquals(Direction.DOWN.name(), robot.getDirection());
        assertNull(programArea.getNextBlockInProgram());
        assertEquals(Result.END, programArea.getProgram().getLastResult());

        controller.executeProgramResetCommand();
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)programArea.getGameWorld()).getRobot().getDirection());
        assertEquals(ifBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());
    }

    @Test
    void undo() {
        CavityBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), robot.getDirection());
        assertEquals(whileBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();

        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), robot.getDirection());
        assertEquals(turnRight, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.undo();
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)programArea.getGameWorld()).getRobot().getDirection());
        assertEquals(whileBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.undo();
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)programArea.getGameWorld()).getRobot().getDirection());
        assertEquals(turnRight, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.undo();
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)programArea.getGameWorld()).getRobot().getDirection());
        assertEquals(whileBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());
    }

    @Test
    void redo() {
        CavityBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), robot.getDirection());
        assertEquals(whileBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();
        controller.executeProgramRunCommand();

        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), robot.getDirection());
        assertEquals(turnRight, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.redo();
        assertEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), robot.getDirection());
        assertEquals(turnRight, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.undo();
        controller.undo();
        controller.undo();
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)programArea.getGameWorld()).getRobot().getDirection());
        assertEquals(whileBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.redo();
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getY());
        assertEquals(Direction.UP.name(), ((Level)programArea.getGameWorld()).getRobot().getDirection());
        assertEquals(turnRight, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.redo();
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)programArea.getGameWorld()).getRobot().getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), ((Level)programArea.getGameWorld()).getRobot().getDirection());
        assertEquals(whileBlock, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.redo();
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), robot.getDirection());
        assertEquals(turnRight, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());

        controller.redo();
        assertNotEquals(robot, ((Level)programArea.getGameWorld()).getRobot());
        assertEquals(1, robot.getGridPosition().getX());
        assertEquals(1, robot.getGridPosition().getY());
        assertEquals(Direction.RIGHT.name(), robot.getDirection());
        assertEquals(turnRight, programArea.getNextBlockInProgram());
        assertEquals(Result.SUCCESS, programArea.getProgram().getLastResult());
    }
}