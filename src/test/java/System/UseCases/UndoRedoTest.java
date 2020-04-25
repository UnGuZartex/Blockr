package System.UseCases;

import Controllers.ControllerClasses.HistoryController;
import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Grid;
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
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UndoRedoTest {

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

    void stateCheck(Block block, Result result, int x, int y, Direction direction) {
        assertEquals(block, paBlockHandler.getPA().getProgram().getCurrentBlock());
        assertEquals(result, paBlockHandler.getPA().getProgram().getLastResult());
        assertEquals(x, robot.getGridPosition().getX());
        assertEquals(y, robot.getGridPosition().getY());
        assertEquals(direction.name(), robot.getDirection());
    }

    @Test
    void noUndoPossible() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.undo();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
    }

    @Test
    void noRedoPossible_noProgramStep() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.redo();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
    }

    @Test
    void noRedoPossible_programStep() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.redo();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
    }

    @Test
    void mainTest() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.executeProgramRunCommand();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        controller.executeProgramRunCommand();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        controller.executeProgramRunCommand();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);

        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);

        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.DOWN);

        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);

        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
    }

    @Test
    void redoAfterExecute() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);

        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);

        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
    }


    @Test
    void redoNotAllPossible() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);

        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);

        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
    }


    @Test
    void returnFromNoneSuccess() {
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, ifBlock.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(null, Result.FAILURE, 1,0,Direction.UP);

        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.UP);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);

        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.UP);
        controller.redo();
    }

    @Test
    void emptyCavityTest() {
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, ifBlock.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, turnRight.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.executeProgramRunCommand();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.UP);
        controller.executeProgramRunCommand();
        stateCheck(null, Result.FAILURE, 1,0,Direction.UP);

        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.UP);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.undo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);

        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        controller.redo();
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.UP);
        controller.redo();
    }
}