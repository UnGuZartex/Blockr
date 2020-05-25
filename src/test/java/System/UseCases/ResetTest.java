package System.UseCases;

import GameWorld.Cell;
import GameWorld.CellType;
import GameWorld.Grid;
import GameWorld.Level;
import GameWorldAPI.GameWorld.GameWorld;
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
import System.Logic.ProgramArea.Handlers.PABlockHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ResetTest {

    ProgramCommand commandRun, commandReset;
    PABlockHandler paBlockHandler;
    Robot robot;
    Grid grid;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
    ProcedureBlock procedure;

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

        procedure.subscribe(paBlockHandler.getPalette());
        commandRun = new RunProgramCommand(paBlockHandler.getPA());
        commandReset = new ResetProgramCommand(paBlockHandler.getPA());
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
        commandRun = null;
        commandReset = null;
    }

    void checkIfReset() {
        assertNotEquals(robot, ((Level)gameWorld).getRobot());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getY());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    boolean isChanged() {
        return 1 != ((Level)gameWorld).getRobot().getGridPosition().getX() ||
               1 != ((Level)gameWorld).getRobot().getGridPosition().getX() ||
                !Direction.LEFT.name().equals(((Level) gameWorld).getRobot().getDirection());
    }

    @Test
    void gameWorldChanged() {
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

        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        assertTrue(this::isChanged);
        commandReset.execute();
        checkIfReset();
    }

    @Test
    void gameWorldNotChanged() {
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

        assertFalse(this::isChanged);
        commandReset.execute();
        checkIfReset();
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

        assertThrows(IllegalStateException.class, () -> commandReset.execute());
    }

    @Test
    void noPrograms() {
        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());
        assertNull(paBlockHandler.getPA().getProgram());

        assertThrows(IllegalStateException.class, () -> commandReset.execute());
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

        assertThrows(IllegalStateException.class, () -> commandReset.execute());
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

        assertThrows(IllegalStateException.class, () -> commandReset.execute());
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

        assertThrows(IllegalStateException.class, () -> commandReset.execute());
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

        commandRun.execute();
        assertTrue(this::isChanged);

        commandReset.execute();
        checkIfReset();
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

        assertThrows(IllegalStateException.class, () -> commandReset.execute());
    }
    
    @Test
    void gameWorldChanged_procedure() {
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

        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        commandRun.execute();
        assertTrue(this::isChanged);

        commandReset.execute();
        checkIfReset();
    }
}