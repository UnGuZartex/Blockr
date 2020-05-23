package System.Logic.ProgramArea;

import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.ControllerClasses.ConnectionController;
import Controllers.IGUISystemBlockLink;
import GUI.Blocks.*;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import GameWorld.Level;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Actions.TurnLeftAction;
import GameWorldUtility.Actions.TurnRightAction;
import GameWorldUtility.LevelInitializer;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import RobotCollection.Robot.Direction;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.CommandHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProgramAreaTest {

    ProgramArea pa0, pa1, pa2, paInvalidProgram, paProcedures;
    Block start1, start2a, start2b, block1, block2, start1Under;
    GameWorldType type;
    CommandHistory history;
    GameWorld gameWorld;
    ConnectionHandler handler;

    Block procedurePaBlock1, procedurePaBlock2, procedurePaBlock3, procedurePaBlockInProcedure1;
    ProcedureBlock procedure1, procedure2;

    ProgramAreaPanel panel;
    PalettePanel palette;
    BlockHandlerController blockHandlerController;
    ConnectionController connectionController;
    IGUISystemBlockLink converter;
    PABlockHandler paBlockHandler;
    ProgramArea programArea;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
    GUIBlock cavity, functional, conditional, operator;
    String cavityName, functionalName, conditionalName, operatorName;

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
        programArea = new ProgramArea(gameWorld, history);
        paInvalidProgram = new ProgramArea(gameWorld, history);

        procedurePaBlock1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        procedurePaBlock2 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        procedurePaBlock3 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2)));
        procedurePaBlockInProcedure1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2)));
        procedure1 = new ProcedureBlock();
        procedure2 = new ProcedureBlock();
        handler.connect(procedurePaBlock2, procedurePaBlock1.getSubConnectorAt(0));
        handler.connect(procedurePaBlock3, procedurePaBlock2.getSubConnectorAt(0));
        handler.connect(procedurePaBlockInProcedure1, procedure1.getSubConnectorAt(0));
        paProcedures = new ProgramArea(gameWorld, history);
        paProcedures.addProgram(procedurePaBlock1);
        paProcedures.addProgram(procedure1);
        paProcedures.addProgram(procedure2);

        start1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        start1Under = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        start2a = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        start2b = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2)));

        pa1.addProgram(start1);
        handler.connect(start1Under, start1.getSubConnectorAt(0));
        pa2.addProgram(start2a);
        pa2.addProgram(start2b);
        paInvalidProgram.addProgram(new WhileBlock());

        moveForward = new FunctionalBlock(new ActionFunctionality((MoveForwardAction) type.getAllActions().get(0)));
        turnLeft = new FunctionalBlock(new ActionFunctionality((TurnLeftAction) type.getAllActions().get(1)));
        turnRight = new FunctionalBlock(new ActionFunctionality((TurnRightAction) type.getAllActions().get(2)));
        wallInFront = new PredicateBlock(new PredicateFunctionality((WallInFrontPredicate) type.getAllPredicates().get(0)));
        notBlock = new NotBlock();
        whileBlock = new WhileBlock();
        ifBlock = new IfBlock();

        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)), programArea);
        converter = new IGUISystemBlockLink();

        blockHandlerController = new BlockHandlerController(converter, paBlockHandler);
        connectionController = new ConnectionController(converter, paBlockHandler);

        panel = new ProgramAreaPanel(100,100,500,800, blockHandlerController, connectionController);

        cavityName = "Cavity";
        functionalName = "functional";
        conditionalName = "conditional";
        operatorName = "operator";

        cavity = new GUICavityBlock(cavityName, 0,0);
        functional = new GUIFunctionalBlock(functionalName, 0,0);
        conditional = new GUIConditionalBlock(conditionalName, 0,0);
        operator = new GUIOperatorBlock(operatorName, 0,0);
        palette = new PalettePanel(1000, 1000, 500, 800, new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator)));
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
        procedurePaBlock1 = null;
        procedurePaBlock2 = null;
        procedurePaBlock3 = null;
        procedurePaBlockInProcedure1 = null;
        procedure1 = null;
        procedure2 = null;
        paProcedures = null;
        panel = null;
        blockHandlerController = null;
        connectionController = null;
        converter = null;
        paBlockHandler = null;
        moveForward = null;
        turnLeft = null;
        turnRight = null;
        wallInFront = null;
        notBlock = null;
        whileBlock = null;
        ifBlock = null;
        programArea = null;
        cavityName = null;
        functionalName = null;
        conditionalName = null;
        operatorName = null;
        cavity = null;
        functional = null;
        conditional = null;
        operator = null;
        palette = null;
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
        assertEquals(procedurePaBlock1, paProcedures.getProgram().getStartBlock());
    }

    @Test
    void getAllBlocksCount() {
        assertEquals(0, pa0.getAllBlocksCount());
        assertEquals(2, pa1.getAllBlocksCount());
        assertEquals(2, pa2.getAllBlocksCount());
        assertEquals(6, paProcedures.getAllBlocksCount());
    }

    @Test
    void hasExecutablePrograms() {
        assertFalse(pa0.hasExecutablePrograms());
        assertTrue(pa1.hasExecutablePrograms());
        assertFalse(pa2.hasExecutablePrograms());
        assertTrue(paProcedures.hasExecutablePrograms());
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
    void addProgram() {
        pa0.addProgram(procedurePaBlock3);
        assertEquals(3, pa0.getAllBlocksCount());
        assertEquals(procedurePaBlock1, pa0.getProgram().getStartBlock());
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

    @Test
    void notifyProgramState_noExecutableProgram() {
        assertFalse(pa0.hasExecutablePrograms());
        assertThrows(IllegalStateException.class, () -> pa0.notifyProgramState());
    }

    @Test
    void addProgramRunCommand_tooMuchPrograms() {
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
        pa2.addProgramRunCommand();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void addProgramRunCommand_noPrograms() {
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
        pa0.addProgramRunCommand();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void addProgramRunCommand_validProgram() {
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
        pa1.addProgramRunCommand();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(Direction.DOWN.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void addProgramRunCommand_invalidProgram() {
        pa0.addProgram(new WhileBlock());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
        pa0.addProgramRunCommand();
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(1, ((Level)gameWorld).getRobot().getGridPosition().getX());
        assertEquals(Direction.LEFT.name(), ((Level)gameWorld).getRobot().getDirection());
    }

    @Test
    void observer() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        pa0.subscribe(panel);
        pa0.addProgramRunCommand();
        assertEquals(Color.RED, panel.getBlocks().get(0).getColor());

        Block block = moveForward.clone();
        pa0.addProgram(block);
        pa0.notifyProgramState();
        assertEquals(Color.WHITE, panel.getBlocks().get(0).getColor());

        pa0.unsubscribe(panel);
        pa0.deleteProgram(block);
        pa0.addProgramRunCommand();
        assertNotEquals(Color.RED, panel.getBlocks().get(0).getColor());
    }

}