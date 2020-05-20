package System.UseCases;

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
import System.Logic.ProgramArea.ProgramCommand;
import System.Logic.ProgramArea.RunProgramCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RunProgramTest {

    PABlockHandler paBlockHandler;
    ProgramCommand command;
    Robot robot;
    Grid grid;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock, procedure;

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
        procedure = new ProcedureBlock();

        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock, procedure)), new ProgramArea(gameWorld, new CommandHistory()));
        command = new RunProgramCommand(paBlockHandler.getPA());
    }

    @AfterEach
    void tearDown() {
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
        procedure = null;
        command = null;
    }

    void stateCheck(Block block, Result result, int x, int y, Direction direction) {
        assertEquals(block, paBlockHandler.getPA().getProgram().getCurrentBlock());
        assertEquals(result, paBlockHandler.getPA().getProgram().getLastResult());
        assertEquals(x, robot.getGridPosition().getX());
        assertEquals(y, robot.getGridPosition().getY());
        assertEquals(direction.name(), robot.getDirection());
    }

    // Start with while
    @Test
    void while_wallInFront_turnRight_moveForward_end()  {
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
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    // Start with if
    @Test
    void if_wallInFront_turnLeft_moveForward_end() {
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, ifBlock.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    // Start with functional
    @Test
    void turnLeft_moveForward_end() {
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(turnLeft);
        paBlockHandler.connectToExistingBlock(moveForward, turnLeft.getSubConnectorAt(0));

        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    // All blocks used
    @Test
    void turnLeft_if_not_wallInFront_while_wallInFront_turnRight_moveForward_end() {
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        PredicateBlock wallInFront1 = (PredicateBlock) paBlockHandler.getFromPalette(3);
        PredicateBlock wallInFront2 = (PredicateBlock) paBlockHandler.getFromPalette(3);
        NotBlock notBlock = (NotBlock) paBlockHandler.getFromPalette(4);
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);

        paBlockHandler.addToPA(turnLeft);
        paBlockHandler.connectToExistingBlock(ifBlock, turnLeft.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(notBlock, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(wallInFront1, notBlock.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(whileBlock, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(wallInFront2, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(8, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    // Failure
    @Test
    void if_not_wallInFront_turnRight_moveForward_failure() {
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        NotBlock notBlock = (NotBlock) paBlockHandler.getFromPalette(4);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(notBlock, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(wallInFront, notBlock.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(turnRight, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, ifBlock.getSubConnectorAt(0));

        assertEquals(5, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(null, Result.FAILURE, 0,1,Direction.LEFT);
        command.execute();
        stateCheck(null, Result.FAILURE, 0,1,Direction.LEFT);
    }

    // Failure mid execution
    @Test
    void moveForward_failure_turnLeft() {
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(turnLeft);
        paBlockHandler.connectToExistingBlock(turnLeft, moveForward.getSubConnectorAt(0));

        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.FAILURE, 0,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.FAILURE, 0,1,Direction.LEFT);
    }

    // End on blank
    @Test
    void if_wallInFront_turnLeft_success() {
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getCavitySubConnector());

        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(null, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.SUCCESS, 1,1,Direction.DOWN);
    }

    // Empty cavity
    @Test
    void while_not_wallInFront_turnLeft() {
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        NotBlock notBlock = (NotBlock) paBlockHandler.getFromPalette(4);
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(notBlock, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(wallInFront, notBlock.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(turnLeft, whileBlock.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(null, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.SUCCESS, 1,1,Direction.DOWN);
    }

    // Infinite loop
    @Test
    void while_wallInFront_turnRight_if_wallInFront_turnLeft_infiniteLoop() {
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        PredicateBlock wallInFront1 = (PredicateBlock) paBlockHandler.getFromPalette(3);
        PredicateBlock wallInFront2 = (PredicateBlock) paBlockHandler.getFromPalette(3);
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront1, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(ifBlock, turnRight.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(wallInFront2, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getCavitySubConnector());

        assertEquals(6, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        for (int i = 0; i < 10; i++) {
            stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
            command.execute();
            stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
            command.execute();
            stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.UP);
            command.execute();
            stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.UP);
            command.execute();
        }
    }

    // Large cavity
    @Test
    void if_wallInFront_turnRight_turnRight_turnRight_moveForward_success() {
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight1 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock turnRight2 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock turnRight3 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight1, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(turnRight2, turnRight1.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(turnRight3, turnRight2.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, turnRight3.getSubConnectorAt(0));

        assertEquals(6, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnRight1, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnRight2, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(turnRight3, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    // End before program end
    @Test
    void turnLeft_moveForward_end_moveForward() {
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock moveForward2 = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(turnLeft);
        paBlockHandler.connectToExistingBlock(moveForward, turnLeft.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward2, moveForward.getSubConnectorAt(0));

        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(moveForward2, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(moveForward2, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void multiplePrograms() {
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(turnRight);
        paBlockHandler.addToPA(moveForward);

        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        assertNull(paBlockHandler.getPA().getProgram());

        assertThrows(IllegalStateException.class, () -> command.execute());
    }

    @Test
    void multipleProgramsAndProcedure() {
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        Block block = paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(turnRight);
        paBlockHandler.addToPA(moveForward);
        paBlockHandler.addToPA(procedure);
        paBlockHandler.connectToExistingBlock(block, procedure.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        assertNull(paBlockHandler.getPA().getProgram());

        assertThrows(IllegalStateException.class, () -> command.execute());
    }

    @Test
    void noPrograms() {
        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        assertNull(paBlockHandler.getPA().getProgram());

        assertThrows(IllegalStateException.class, () -> command.execute());
    }

    @Test
    void invalidProgram() {
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        CavityBlock cavityBlock = (CavityBlock) paBlockHandler.getFromPalette(5);

        paBlockHandler.addToPA(turnRight);
        paBlockHandler.connectToExistingBlock(cavityBlock, turnRight.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, cavityBlock.getCavitySubConnector());

        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());

        assertThrows(IllegalStateException.class, () -> command.execute());
    }

    @Test
    void invalidCalledProcedure() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        CavityBlock cavity = (CavityBlock) paBlockHandler.getFromPalette(6);
        paBlockHandler.addToPA(procedure);
        paBlockHandler.connectToExistingBlock(cavity, procedure.getSubConnectorAt(0));

        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);
        paBlockHandler.addToPA(turnLeft);
        paBlockHandler.connectToExistingBlock(call, turnLeft.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, call.getSubConnectorAt(0));

        assertEquals(5, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        assertFalse(procedure.hasProperConnections());

        assertThrows(IllegalStateException.class, () -> command.execute());
    }

    @Test
    void invalidNoneCalledProcedure() {
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        CavityBlock cavity = (CavityBlock) paBlockHandler.getFromPalette(6);

        paBlockHandler.addToPA(turnLeft);
        paBlockHandler.connectToExistingBlock(moveForward, turnLeft.getSubConnectorAt(0));
        paBlockHandler.addToPA(procedure);
        paBlockHandler.connectToExistingBlock(cavity, procedure.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        assertFalse(procedure.hasProperConnections());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void onlyProcedures() {
        ProcedureBlock procedure1 = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        Block block1 = paBlockHandler.getFromPalette(0);
        ProcedureBlock procedure2 = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        Block block21 = paBlockHandler.getFromPalette(1);
        Block block22 = paBlockHandler.getFromPalette(2);

        paBlockHandler.addToPA(procedure1);
        paBlockHandler.connectToExistingBlock(block1, procedure1.getSubConnectorAt(0));

        paBlockHandler.addToPA(procedure2);
        paBlockHandler.connectToExistingBlock(block21, procedure2.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(block22, block21.getSubConnectorAt(0));

        assertEquals(5, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());

        assertThrows(IllegalStateException.class, () -> command.execute());
    }

    @Test
    void withProcedureNotCalled() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        Block block = paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        paBlockHandler.addToPA(procedure);
        paBlockHandler.connectToExistingBlock(block, procedure.getSubConnectorAt(0));

        assertEquals(6, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void turnLeft_call_moveForwardProcedure() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);

        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(turnLeft);
        paBlockHandler.connectToExistingBlock(call, turnLeft.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, procedure.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(call, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void call_turnLeftProcedure_moveForward() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);

        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(turnLeft);
        paBlockHandler.connectToExistingBlock(turnLeft, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, call.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(call, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void call_turnLeftProcedure_moveForwardProcedure() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);

        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(call);
        paBlockHandler.connectToExistingBlock(turnLeft, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, turnLeft.getSubConnectorAt(0));

        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(call, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void while_wallInFront_call_turnRightProcedure_moveForward() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);

        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(call, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(6, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(call, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(call, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(call, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void if_wallInFront_turnLeftProcedure_moveForward() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);

        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(call, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, ifBlock.getSubConnectorAt(0));


        assertEquals(6, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(call, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void call_ifProcedure_wallInFrontProcedure_turnLeftProcedure_moveForward() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);

        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(call);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(ifBlock, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, call.getSubConnectorAt(0));


        assertEquals(6, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(call, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void call_ifProcedure_wallInFrontProcedure_turnLeftProcedure_moveForwardProcedure() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);

        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(call);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(ifBlock, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, ifBlock.getSubConnectorAt(0));

        assertEquals(6, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(call, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void call_turnRightProcedure_call_turnRightProcedure_call_turnRightProcedure_moveForward() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call1 = (ProcedureCall) paBlockHandler.getFromPalette(8);
        ProcedureCall call2 = (ProcedureCall) paBlockHandler.getFromPalette(8);
        ProcedureCall call3 = (ProcedureCall) paBlockHandler.getFromPalette(8);
        paBlockHandler.connectToExistingBlock(call2, call1.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(call3, call2.getSubConnectorAt(0));

        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        paBlockHandler.connectToExistingBlock(turnRight, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, call3.getSubConnectorAt(0));

        assertEquals(6, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(call1, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(call2, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(call3, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void call1_turnLeftProcedure1_call2_moveForwardProcedure2() {
        ProcedureBlock procedure1 = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure1);
        ProcedureCall call1 = (ProcedureCall) paBlockHandler.getFromPalette(8);

        ProcedureBlock procedure2 = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure2);
        ProcedureCall call2 = (ProcedureCall) paBlockHandler.getFromPalette(9);

        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(call1);
        paBlockHandler.connectToExistingBlock(call2, call1.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, procedure2.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(turnLeft, procedure1.getSubConnectorAt(0));

        assertEquals(6, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        robot = ((Level)paBlockHandler.getPA().getGameWorld()).getRobot();

        stateCheck(call1, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure1, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(call2, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(procedure2, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void recursion() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);
        ProcedureCall callRecursion = (ProcedureCall) paBlockHandler.getFromPalette(8);

        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(call);
        paBlockHandler.connectToExistingBlock(turnRight, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(ifBlock, turnRight.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(callRecursion, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, call.getSubConnectorAt(0));

        stateCheck(call, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(callRecursion, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(callRecursion, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        command.execute();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
    }

    @Test
    void recursion_blocksAfter() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call = (ProcedureCall) paBlockHandler.getFromPalette(8);
        ProcedureCall callRecursion = (ProcedureCall) paBlockHandler.getFromPalette(8);

        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);

        paBlockHandler.addToPA(call);
        paBlockHandler.connectToExistingBlock(turnRight, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(ifBlock, turnRight.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(callRecursion, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getSubConnectorAt(0));

        stateCheck(call, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        command.execute();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(callRecursion, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        command.execute();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(callRecursion, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(procedure, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        command.execute();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1, Direction.DOWN);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1, Direction.RIGHT);
        command.execute();
        stateCheck(turnLeft, Result.SUCCESS, 1,1, Direction.UP);
        command.execute();
        stateCheck(null, Result.SUCCESS, 1,1, Direction.LEFT);
        command.execute();
        stateCheck(null, Result.SUCCESS, 1,1, Direction.LEFT);
    }
}