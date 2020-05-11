package GUI.Panel;

import Controllers.IGUI_System_BlockLink;
import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.ControllerClasses.ConnectionController;
import GUI.Blocks.*;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Actions.TurnLeftAction;
import GameWorldUtility.Actions.TurnRightAction;
import GameWorldUtility.LevelInitializer;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.CommandHistory;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProgramAreaPanelTest {

    ProgramAreaPanel panel;
    PalettePanel palette;
    int cornerX, cornerY, width, height;
    Random random;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    static final int MIN_WIDTH = 100, MAX_WIDTH = 800, MIN_HEIGHT = 100, MAX_HEIGHT = 800;
    BlockHandlerController blockHandlerController;
    ConnectionController connectionController;
    IGUI_System_BlockLink converter;
    PABlockHandler paBlockHandler;
    ProgramArea programArea;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
    CommandHistory history;
    GUIBlock cavity, functional, conditional, operator;
    String cavityName, functionalName, conditionalName, operatorName;

    @BeforeEach
    void setUp() {
        random = new Random();
        cornerX = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        cornerY = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        width = random.nextInt(MAX_WIDTH + 1 - MIN_WIDTH) + MIN_WIDTH;
        height = random.nextInt(MAX_HEIGHT + 1 - MIN_HEIGHT) + MIN_HEIGHT;

        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        history = new CommandHistory();
        programArea = new ProgramArea(gameWorld, history);

        moveForward = new FunctionalBlock(new ActionFunctionality((MoveForwardAction) type.getAllActions().get(0)));
        turnLeft = new FunctionalBlock(new ActionFunctionality((TurnLeftAction) type.getAllActions().get(1)));
        turnRight = new FunctionalBlock(new ActionFunctionality((TurnRightAction) type.getAllActions().get(2)));
        wallInFront = new PredicateBlock(new PredicateFunctionality((WallInFrontPredicate) type.getAllPredicates().get(0)));
        notBlock = new NotBlock();
        whileBlock = new WhileBlock();
        ifBlock = new IfBlock();

        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)), programArea);
        converter = new IGUI_System_BlockLink();

        cavityName = "Cavity";
        functionalName = "functional";
        conditionalName = "conditional";
        operatorName = "operator";

        cavity = new GUICavityBlock(cavityName, 0,0);
        functional = new GUIFunctionalBlock(functionalName, 0,0);
        conditional = new GUIConditionalBlock(conditionalName, 0,0);
        operator = new GUIOperatorBlock(operatorName, 0,0);

        blockHandlerController = new BlockHandlerController(converter, paBlockHandler);
        connectionController = new ConnectionController(converter, paBlockHandler);

        panel = new ProgramAreaPanel(cornerX, cornerY, width, height, blockHandlerController, connectionController);
        palette = new PalettePanel(cornerX, cornerY, width, height, new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator)), blockHandlerController);
    }

    @AfterEach
    void tearDown() {
        random = null;
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
        history = null;
        gameWorld = null;
        type = null;
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
    void programAreaPanel_invalidBlockHandlerController() {
        assertThrows(IllegalArgumentException.class, () -> new ProgramAreaPanel(cornerX, cornerY, width, height, null, connectionController));
    }

    @Test
    void programAreaPanel_invalidConnectionController() {
        assertThrows(IllegalArgumentException.class, () -> new ProgramAreaPanel(cornerX, cornerY, width, height, blockHandlerController, null));
    }

    @Test
    void getConnectionController() {
        assertEquals(connectionController, panel.getConnectionController());
    }

    @Test
    void addTemporaryBlockToProgramArea_noTemporaryBlock() {
        assertThrows(IllegalStateException.class, () -> panel.addTemporaryBlockToProgramArea());
    }

    @Test
    void addTemporaryBlockToProgramArea_alreadyInProgramArea() {
        Map.Entry<GUIBlock, Integer> blockPair = new AbstractMap.SimpleEntry<>(palette.getNewBlock(0), 1);
        panel.setTemporaryBlockPair(blockPair);
        panel.addTemporaryBlockToProgramArea();
        assertThrows(IllegalStateException.class, () -> panel.addTemporaryBlockToProgramArea());
    }

    @Test
    void addTemporaryBlockToProgramArea() {
        Map.Entry<GUIBlock, Integer> blockPair = new AbstractMap.SimpleEntry<>(palette.getNewBlock(0), 1);
        panel.setTemporaryBlockPair(blockPair);
        assertNull(programArea.getProgram());
        assertFalse(panel.getBlockPairs().contains(blockPair));
        assertEquals(0, panel.getBlockPairs().size());
        panel.addTemporaryBlockToProgramArea();
        assertNotNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair));
        assertEquals(1, panel.getBlockPairs().size());
    }

    @Test
    void setTemporaryBlockPair() {
        Map.Entry<GUIBlock, Integer> blockPair = new AbstractMap.SimpleEntry<>(palette.getNewBlock(0), 1);
        panel.setTemporaryBlockPair(blockPair);
        panel.addTemporaryBlockToProgramArea();
        assertNotNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair));
        assertEquals(1, panel.getBlockPairs().size());
    }

    @Test
    void disconnectInProgramArea_notConnected() {
        assertNull(programArea.getProgram());
        assertEquals(0, panel.getBlockPairs().size());
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        assertNotNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertEquals(1, panel.getBlockPairs().size());

        panel.disconnectInProgramArea(block0);
        assertNotNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertEquals(1, panel.getBlockPairs().size());
    }

    @Test
    void disconnectInProgramArea_connected() {
        assertNull(programArea.getProgram());
        assertEquals(0, panel.getBlockPairs().size());
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        assertNotNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertEquals(1, panel.getBlockPairs().size());
        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        block0.setPosition(100, 250);
        block1.setPosition(100, 250 + block0.getHeight());
        block0.connectWithStaticBlock(block1, panel.getConnectionController());

        assertNotNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertEquals(2, panel.getBlockPairs().size());

        panel.disconnectInProgramArea(block1);
        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertEquals(2, panel.getBlockPairs().size());
    }

    @Test
    void deleteBlockFromProgramArea_noBlock() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block2 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair2 = new AbstractMap.SimpleEntry<>(block2, 2);
        panel.setTemporaryBlockPair(blockPair2);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block3 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair3 = new AbstractMap.SimpleEntry<>(block3, 3);
        panel.setTemporaryBlockPair(blockPair3);
        panel.addTemporaryBlockToProgramArea();

        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertTrue(panel.getBlockPairs().contains(blockPair2));
        assertTrue(panel.getBlockPairs().contains(blockPair3));
        assertEquals(4, panel.getBlockPairs().size());

        panel.deleteBlockFromProgramArea( new ArrayList<>());
        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertTrue(panel.getBlockPairs().contains(blockPair2));
        assertTrue(panel.getBlockPairs().contains(blockPair3));
        assertEquals(4, panel.getBlockPairs().size());
    }

    @Test
    void deleteBlockFromProgramArea_oneBlock() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block2 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair2 = new AbstractMap.SimpleEntry<>(block2, 2);
        panel.setTemporaryBlockPair(blockPair2);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block3 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair3 = new AbstractMap.SimpleEntry<>(block3, 3);
        panel.setTemporaryBlockPair(blockPair3);
        panel.addTemporaryBlockToProgramArea();

        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertTrue(panel.getBlockPairs().contains(blockPair2));
        assertTrue(panel.getBlockPairs().contains(blockPair3));
        assertEquals(4, panel.getBlockPairs().size());

        panel.deleteBlockFromProgramArea( new ArrayList<>(Collections.singletonList(block1)));
        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair2));
        assertTrue(panel.getBlockPairs().contains(blockPair3));
        assertEquals(3, panel.getBlockPairs().size());
    }

    @Test
    void deleteBlockFromProgramArea_multipleBlocks() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block2 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair2 = new AbstractMap.SimpleEntry<>(block2, 2);
        panel.setTemporaryBlockPair(blockPair2);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block3 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair3 = new AbstractMap.SimpleEntry<>(block3, 3);
        panel.setTemporaryBlockPair(blockPair3);
        panel.addTemporaryBlockToProgramArea();

        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertTrue(panel.getBlockPairs().contains(blockPair2));
        assertTrue(panel.getBlockPairs().contains(blockPair3));
        assertEquals(4, panel.getBlockPairs().size());

        panel.deleteBlockFromProgramArea( new ArrayList<>(Arrays.asList(block1, block3)));
        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair2));
        assertEquals(2, panel.getBlockPairs().size());
    }

    @Test
    void deleteBlockFromProgramArea_connectedBlocks() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        block0.setPosition(100, 250);
        block1.setPosition(100, 250 + block0.getHeight());
        block0.connectWithStaticBlock(block1, panel.getConnectionController());

        GUIBlock block2 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair2 = new AbstractMap.SimpleEntry<>(block2, 2);
        panel.setTemporaryBlockPair(blockPair2);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block3 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair3 = new AbstractMap.SimpleEntry<>(block3, 3);
        panel.setTemporaryBlockPair(blockPair3);
        panel.addTemporaryBlockToProgramArea();

        block2.setPosition(300, 250);
        block3.setPosition(300, 250 + block2.getHeight());
        block2.connectWithStaticBlock(block3, panel.getConnectionController());

        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertTrue(panel.getBlockPairs().contains(blockPair2));
        assertTrue(panel.getBlockPairs().contains(blockPair3));
        assertEquals(4, panel.getBlockPairs().size());

        panel.deleteBlockFromProgramArea( new ArrayList<>(Collections.singletonList(block1)));

        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair2));
        assertTrue(panel.getBlockPairs().contains(blockPair3));
        assertEquals(3, panel.getBlockPairs().size());

        panel.deleteBlockFromProgramArea( new ArrayList<>(Collections.singletonList(block2)));

        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair3));
        assertEquals(2, panel.getBlockPairs().size());
    }

    @Test
    void deleteBlockFromProgramArea_blockNotInProgramArea() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        assertNull(programArea.getProgram());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertEquals(2, panel.getBlockPairs().size());

        assertThrows(IllegalArgumentException.class, () -> panel.deleteBlockFromProgramArea( new ArrayList<>(Collections.singletonList(palette.getNewBlock(0)))));
    }

    @Test
    void getBlocks() {
        assertEquals(0, panel.getBlocks().size());

        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        assertEquals(1, panel.getBlocks().size());
        assertTrue(panel.getBlocks().contains(block0));

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();
        assertEquals(2, panel.getBlocks().size());
        assertTrue(panel.getBlocks().contains(block0));
        assertTrue(panel.getBlocks().contains(block1));

        GUIBlock block2 = palette.getNewBlock(2);
        Map.Entry<GUIBlock, Integer> blockPair2 = new AbstractMap.SimpleEntry<>(block2, 2);
        panel.setTemporaryBlockPair(blockPair2);
        panel.addTemporaryBlockToProgramArea();
        assertEquals(3, panel.getBlocks().size());
        assertTrue(panel.getBlocks().contains(block0));
        assertTrue(panel.getBlocks().contains(block1));
        assertTrue(panel.getBlocks().contains(block2));

        GUIBlock block3 = palette.getNewBlock(3);
        Map.Entry<GUIBlock, Integer> blockPair3 = new AbstractMap.SimpleEntry<>(block3, 3);
        panel.setTemporaryBlockPair(blockPair3);
        panel.addTemporaryBlockToProgramArea();
        assertEquals(4, panel.getBlocks().size());
        assertTrue(panel.getBlocks().contains(block0));
        assertTrue(panel.getBlocks().contains(block1));
        assertTrue(panel.getBlocks().contains(block2));
        assertTrue(panel.getBlocks().contains(block3));
    }

    @Test
    void getBlockPairs() {
        assertEquals(0, panel.getBlockPairs().size());

        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        assertEquals(1, panel.getBlockPairs().size());
        assertTrue(panel.getBlockPairs().contains(blockPair0));

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();
        assertEquals(2, panel.getBlockPairs().size());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));

        GUIBlock block2 = palette.getNewBlock(2);
        Map.Entry<GUIBlock, Integer> blockPair2 = new AbstractMap.SimpleEntry<>(block2, 2);
        panel.setTemporaryBlockPair(blockPair2);
        panel.addTemporaryBlockToProgramArea();
        assertEquals(3, panel.getBlockPairs().size());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertTrue(panel.getBlockPairs().contains(blockPair2));

        GUIBlock block3 = palette.getNewBlock(3);
        Map.Entry<GUIBlock, Integer> blockPair3 = new AbstractMap.SimpleEntry<>(block3, 3);
        panel.setTemporaryBlockPair(blockPair3);
        panel.addTemporaryBlockToProgramArea();
        assertEquals(4, panel.getBlockPairs().size());
        assertTrue(panel.getBlockPairs().contains(blockPair0));
        assertTrue(panel.getBlockPairs().contains(blockPair1));
        assertTrue(panel.getBlockPairs().contains(blockPair2));
        assertTrue(panel.getBlockPairs().contains(blockPair3));
    }

    @Test
    void setBlockDrawLayerFirst_noElements() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block2 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair2 = new AbstractMap.SimpleEntry<>(block2, 2);
        panel.setTemporaryBlockPair(blockPair2);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block3 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair3 = new AbstractMap.SimpleEntry<>(block3, 3);
        panel.setTemporaryBlockPair(blockPair3);
        panel.addTemporaryBlockToProgramArea();

        assertEquals(block0, panel.getBlocks().get(0));
        assertEquals(block1, panel.getBlocks().get(1));
        assertEquals(block2, panel.getBlocks().get(2));
        assertEquals(block3, panel.getBlocks().get(3));

        panel.setBlockDrawLayerFirst(new ArrayList<>());

        assertEquals(block0, panel.getBlocks().get(0));
        assertEquals(block1, panel.getBlocks().get(1));
        assertEquals(block2, panel.getBlocks().get(2));
        assertEquals(block3, panel.getBlocks().get(3));
    }

    @Test
    void setBlockDrawLayerFirst_oneElements() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block2 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair2 = new AbstractMap.SimpleEntry<>(block2, 2);
        panel.setTemporaryBlockPair(blockPair2);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block3 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair3 = new AbstractMap.SimpleEntry<>(block3, 3);
        panel.setTemporaryBlockPair(blockPair3);
        panel.addTemporaryBlockToProgramArea();

        assertEquals(block0, panel.getBlocks().get(0));
        assertEquals(block1, panel.getBlocks().get(1));
        assertEquals(block2, panel.getBlocks().get(2));
        assertEquals(block3, panel.getBlocks().get(3));

        panel.setBlockDrawLayerFirst(new ArrayList<>(Collections.singletonList(block1)));

        assertEquals(block0, panel.getBlocks().get(0));
        assertEquals(block1, panel.getBlocks().get(3));
        assertEquals(block2, panel.getBlocks().get(1));
        assertEquals(block3, panel.getBlocks().get(2));
    }

    @Test
    void setBlockDrawLayerFirst_multipleElements() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block2 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair2 = new AbstractMap.SimpleEntry<>(block2, 2);
        panel.setTemporaryBlockPair(blockPair2);
        panel.addTemporaryBlockToProgramArea();

        GUIBlock block3 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair3 = new AbstractMap.SimpleEntry<>(block3, 3);
        panel.setTemporaryBlockPair(blockPair3);
        panel.addTemporaryBlockToProgramArea();

        assertEquals(block0, panel.getBlocks().get(0));
        assertEquals(block1, panel.getBlocks().get(1));
        assertEquals(block2, panel.getBlocks().get(2));
        assertEquals(block3, panel.getBlocks().get(3));

        panel.setBlockDrawLayerFirst(new ArrayList<>(Arrays.asList(block1, block0)));

        assertEquals(block0, panel.getBlocks().get(3));
        assertEquals(block1, panel.getBlocks().get(2));
        assertEquals(block2, panel.getBlocks().get(0));
        assertEquals(block3, panel.getBlocks().get(1));

    }

    @Test
    void onGameFinished_success() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        assertEquals(Color.white, block0.getColor());
        assertEquals(Color.white, block1.getColor());
        assertEquals("", panel.gameState);

        panel.onGameFinished(Result.SUCCESS);

        assertEquals(Color.orange, block0.getColor());
        assertEquals(Color.orange, block1.getColor());
        assertEquals("YOU LOSE!  :(", panel.gameState);
    }

    @Test
    void onGameFinished_end() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        assertEquals(Color.white, block0.getColor());
        assertEquals(Color.white, block1.getColor());
        assertEquals("", panel.gameState);

        panel.onGameFinished(Result.END);

        assertEquals(Color.green, block0.getColor());
        assertEquals(Color.green, block1.getColor());
        assertEquals("YOU WIN!  :)", panel.gameState);
    }

    @Test
    void onGameFinished_failure() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        assertEquals(Color.white, block0.getColor());
        assertEquals(Color.white, block1.getColor());
        assertEquals("", panel.gameState);

        panel.onGameFinished(Result.FAILURE);

        assertEquals(Color.orange, block0.getColor());
        assertEquals(Color.orange, block1.getColor());
        assertEquals("YOU LOSE!  :(", panel.gameState);
    }

    @Test
    void onProgramDefaultState_alreadyOnDefault() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        panel.onGameFinished(Result.FAILURE);
        assertEquals(Color.orange, block0.getColor());
        assertEquals(Color.orange, block1.getColor());
        assertEquals("YOU LOSE!  :(", panel.gameState);

        panel.onProgramDefaultState();

        assertEquals(Color.white, block0.getColor());
        assertEquals(Color.white, block1.getColor());
        assertEquals("", panel.gameState);
    }

    @Test
    void onProgramDefaultState_notOnDefault() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        assertEquals(Color.white, block0.getColor());
        assertEquals(Color.white, block1.getColor());
        assertEquals("", panel.gameState);

        panel.onProgramDefaultState();

        assertEquals(Color.white, block0.getColor());
        assertEquals(Color.white, block1.getColor());
        assertEquals("", panel.gameState);
    }

    @Test
    void onTooManyPrograms() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        assertEquals(Color.white, block0.getColor());
        assertEquals(Color.white, block1.getColor());
        assertEquals("", panel.gameState);

        panel.onTooManyPrograms();

        assertEquals(Color.red, block0.getColor());
        assertEquals(Color.red, block1.getColor());
        assertEquals("TOO MANY PROGRAMS!", panel.gameState);
    }

    @Test
    void onProgramInvalid() {
        GUIBlock block0 = palette.getNewBlock(0);
        Map.Entry<GUIBlock, Integer> blockPair0 = new AbstractMap.SimpleEntry<>(block0, 0);
        panel.setTemporaryBlockPair(blockPair0);
        panel.addTemporaryBlockToProgramArea();
        GUIBlock block1 = palette.getNewBlock(1);
        Map.Entry<GUIBlock, Integer> blockPair1 = new AbstractMap.SimpleEntry<>(block1, 1);
        panel.setTemporaryBlockPair(blockPair1);
        panel.addTemporaryBlockToProgramArea();

        assertEquals(Color.white, block0.getColor());
        assertEquals(Color.white, block1.getColor());
        assertEquals("", panel.gameState);

        panel.onProgramInvalid();

        assertEquals(Color.red, block0.getColor());
        assertEquals(Color.red, block1.getColor());
        assertEquals("INVALID PROGRAM!", panel.gameState);
    }
}