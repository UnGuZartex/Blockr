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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RunProgramTest {

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

        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)), new ProgramArea(gameWorld, new CommandHistory()));
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

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.UP);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.UP);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.RIGHT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.RIGHT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
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

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
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

        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
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

        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
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

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(null, Result.FAILURE, 0,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
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

        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnLeft, Result.FAILURE, 0,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
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

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(null, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
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

        stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(null, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
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

        for (int i = 0; i < 10; i++) {
            stateCheck(whileBlock, Result.SUCCESS, 1,1,Direction.LEFT);
            paBlockHandler.getPA().runProgramStep();
            stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
            paBlockHandler.getPA().runProgramStep();
            stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.UP);
            paBlockHandler.getPA().runProgramStep();
            stateCheck(turnLeft, Result.SUCCESS, 1,1,Direction.UP);
            paBlockHandler.getPA().runProgramStep();
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

        stateCheck(ifBlock, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnRight1, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnRight2, Result.SUCCESS, 1,1,Direction.UP);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnRight3, Result.SUCCESS, 1,1,Direction.RIGHT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(moveForward, Result.SUCCESS, 1,1,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(null, Result.END, 1,2,Direction.DOWN);
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

        paBlockHandler.getPA().runProgramStep();
    }

    @Test
    void noPrograms() {
        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        assertNull(paBlockHandler.getPA().getProgram());

        paBlockHandler.getPA().runProgramStep();
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

        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
        paBlockHandler.getPA().runProgramStep();
        stateCheck(turnRight, Result.SUCCESS, 1,1,Direction.LEFT);
    }
}