package GUI.Blocks;

import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.ControllerClasses.ConnectionController;
import Controllers.IGUISystemBlockLink;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Panel.PalettePanel;
import GameWorldAPI.GameWorld.GameWorld;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GUIFunctionalBlockTest {

    GUIFunctionalBlock block, blockTop, blockBottom;
    Random random;
    int x, y, xTop, yTop, xBottom, yBottom;
    String name, nameTop, nameBottom;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;

    PalettePanel palette;
    int cornerX, cornerY, width, height;
    static final int MIN_WIDTH = 100, MAX_WIDTH = 800, MIN_HEIGHT = 100, MAX_HEIGHT = 800;
    ConnectionController connectionController;
    IGUISystemBlockLink converter;
    PABlockHandler paBlockHandler;
    ProgramArea programArea;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
    CommandHistory history;
    GUIBlock cavity, functional, conditional, operator;
    String cavityName, functionalName, conditionalName, operatorName;
    Block blockIf, blockPredicate, blockNot;
    IGUIBlock guiBlockCavity, guiBlockConditional, guiBlockOperator;
    BlockHandlerController blockHandlerController;

    @BeforeEach
    void setUp() {
        random = new Random();
        x = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        y = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        xTop = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        yTop = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        xBottom = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        yBottom = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;

        name = "functional block";
        nameTop = "functional block connected top";
        nameBottom = "functional block connected bottom";

        block = new GUIFunctionalBlock(name, x, y);
        blockTop = new GUIFunctionalBlock(nameTop, xTop, yTop);
        blockBottom = new GUIFunctionalBlock(nameBottom, xBottom, yBottom);

        blockBottom.setPosition(xTop, yTop + GUIFunctionalBlock.DEFAULT_HEIGHT);
        blockBottom.mainConnector.connect(blockTop.subConnectors.get(0));

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
        converter = new IGUISystemBlockLink();

        cavityName = "Cavity";
        functionalName = "functional";
        conditionalName = "conditional";
        operatorName = "operator";

        cavity = new GUICavityBlock(cavityName, 0,0);
        functional = new GUIFunctionalBlock(functionalName, 0,0);
        conditional = new GUIConditionalBlock(conditionalName, 0,0);
        operator = new GUIOperatorBlock(operatorName, 0,0);

        connectionController = new ConnectionController(converter, paBlockHandler);

        palette = new PalettePanel(cornerX, cornerY, width, height, new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator)));

        blockIf = new IfBlock();
        blockPredicate = new PredicateBlock(new PredicateFunctionality(type.getAllPredicates().get(0)));
        blockNot = new NotBlock();
        guiBlockCavity = palette.getNewBlock(0);
        guiBlockConditional = palette.getNewBlock(2);
        guiBlockOperator = palette.getNewBlock(3);
        converter.addBlockPair(block, new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        converter.addBlockPair(blockTop,  new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        converter.addBlockPair(blockBottom,  new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));

        blockHandlerController = new BlockHandlerController(converter, paBlockHandler);
    }

    @AfterEach
    void tearDown() {
        random = null;
        block = null;
        blockTop = null;
        blockBottom = null;
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
        blockIf = null;
        blockPredicate = null;
        blockNot = null;
        guiBlockCavity = null;
        guiBlockConditional = null;
        guiBlockOperator = null;
        blockHandlerController = null;
    }

    @Test
    void getX() {
        assertEquals(x, block.getX());
    }

    @Test
    void getY() {
        assertEquals(y, block.getY());
    }

    @Test
    void getPosition() {
        assertEquals(x, block.getPosition().getX());
        assertEquals(y, block.getPosition().getY());
    }

    @Test
    void getTotalHeight() {
        assertEquals(GUIFunctionalBlock.DEFAULT_HEIGHT, block.getTotalHeight());
        assertEquals(GUIFunctionalBlock.DEFAULT_HEIGHT, blockBottom.getTotalHeight());
        assertEquals(GUIFunctionalBlock.DEFAULT_HEIGHT*2, blockTop.getTotalHeight());
    }

    @Test
    void getWidth() {
        assertEquals(GUIFunctionalBlock.DEFAULT_WIDTH, block.getWidth());
    }

    @Test
    void setColor() {
        assertEquals(block.getColor(), GUIBlock.DEFAULT_BLOCK_COLOR);
        block.setColor(Color.pink);
        assertEquals(Color.pink, block.getColor());
    }

    @Test
    void setColor_terminated() {
        block.terminate();
        assertThrows(IllegalStateException.class, () -> block.setColor(Color.pink));
    }

    @Test
    void setPosition() {
        int x = 150, y = 200;
        blockTop.setPosition(x, y);
        assertEquals(x, blockTop.getX());
        assertEquals(y, blockTop.getY());
        assertEquals(x + GUIFunctionalBlock.DEFAULT_WIDTH/2, blockTop.subConnectors.get(0).getPosition().getX());
        assertEquals(y + GUIFunctionalBlock.DEFAULT_HEIGHT, blockTop.subConnectors.get(0).getPosition().getY());
        assertEquals(x, blockBottom.getX());
        assertEquals(y + GUIFunctionalBlock.DEFAULT_HEIGHT, blockBottom.getY());
        assertEquals(x + GUIFunctionalBlock.DEFAULT_WIDTH/2, blockBottom.subConnectors.get(0).getPosition().getX());
        assertEquals(y + GUIFunctionalBlock.DEFAULT_HEIGHT*2, blockBottom.subConnectors.get(0).getPosition().getY());
    }

    @Test
    void translate() {
        int x = 10, y = -15;
        blockTop.translate(x, y);
        assertEquals(xTop + x, blockTop.getX());
        assertEquals(yTop + y, blockTop.getY());
        assertEquals(xTop + x + GUIFunctionalBlock.DEFAULT_WIDTH/2, blockTop.subConnectors.get(0).getPosition().getX());
        assertEquals(yTop + y + GUIFunctionalBlock.DEFAULT_HEIGHT, blockTop.subConnectors.get(0).getPosition().getY());
        assertEquals(xTop + x, blockBottom.getX());
        assertEquals(yTop + y + GUIFunctionalBlock.DEFAULT_HEIGHT, blockBottom.getY());
        assertEquals(xTop + x + GUIFunctionalBlock.DEFAULT_WIDTH/2, blockBottom.subConnectors.get(0).getPosition().getX());
        assertEquals(yTop + y + GUIFunctionalBlock.DEFAULT_HEIGHT*2, blockBottom.subConnectors.get(0).getPosition().getY());
    }

    @Test
    void getConnectorIndex() {
        assertEquals(0, block.getConnectorIndex(block.subConnectors.get(0)));
    }

    @Test
    void removeInBetween_connectedBottom() {
        assertTrue(blockBottom.mainConnector.isConnected());
        assertTrue(blockTop.subConnectors.get(0).isConnected());
        assertEquals(blockBottom, blockTop.subConnectors.get(0).getConnectedGUIBlock());
        assertEquals(blockTop, blockBottom.mainConnector.getConnectedGUIBlock());
        blockTop.removeInBetween(connectionController, blockHandlerController);
        assertFalse(blockBottom.mainConnector.isConnected());
        assertFalse(blockTop.subConnectors.get(0).isConnected());
    }

    @Test
    void removeInBetween_connectedTop() {
        assertTrue(blockBottom.mainConnector.isConnected());
        assertTrue(blockTop.subConnectors.get(0).isConnected());
        assertEquals(blockBottom, blockTop.subConnectors.get(0).getConnectedGUIBlock());
        assertEquals(blockTop, blockBottom.mainConnector.getConnectedGUIBlock());
        blockBottom.removeInBetween(connectionController, blockHandlerController);
        assertFalse(blockBottom.mainConnector.isConnected());
        assertFalse(blockTop.subConnectors.get(0).isConnected());
    }

    @Test
    void removeInBetween_connectedTopBottom() {
        block.setPosition(xTop, yTop - GUIFunctionalBlock.DEFAULT_HEIGHT);
        blockTop.mainConnector.connect(block.subConnectors.get(0));

        assertTrue(blockTop.mainConnector.isConnected());
        assertTrue(block.subConnectors.get(0).isConnected());
        assertEquals(blockTop, block.subConnectors.get(0).getConnectedGUIBlock());
        assertEquals(block, blockTop.mainConnector.getConnectedGUIBlock());

        assertTrue(blockBottom.mainConnector.isConnected());
        assertTrue(blockTop.subConnectors.get(0).isConnected());
        assertEquals(blockBottom, blockTop.subConnectors.get(0).getConnectedGUIBlock());
        assertEquals(blockTop, blockBottom.mainConnector.getConnectedGUIBlock());

        blockTop.removeInBetween(connectionController, blockHandlerController);

        assertTrue(blockBottom.mainConnector.isConnected());
        assertFalse(blockTop.subConnectors.get(0).isConnected());
        assertFalse(blockTop.mainConnector.isConnected());
        assertTrue(block.subConnectors.get(0).isConnected());
        assertEquals(blockBottom, block.subConnectors.get(0).getConnectedGUIBlock());
        assertEquals(block, blockBottom.mainConnector.getConnectedGUIBlock());
    }

    @Test
    void contains() {
        int pointX = random.nextInt(GUIFunctionalBlock.DEFAULT_WIDTH + 1) + x;
        int pointY = random.nextInt(GUIFunctionalBlock.DEFAULT_HEIGHT + 1) + y;
        assertTrue(block.contains(pointX, pointY));
    }

    @Test
    void isInside() {
        int pointX = random.nextInt(GUIFunctionalBlock.DEFAULT_WIDTH + 1) + x;
        int pointY = random.nextInt(GUIFunctionalBlock.DEFAULT_HEIGHT + 1) + y;
        assertFalse(block.isInside(new CollisionRectangle(pointX, pointY, 500, 500, Color.WHITE)));

        pointX = random.nextInt(x);
        pointY = random.nextInt(y);
        assertTrue(block.isInside(new CollisionRectangle(pointX, pointY, x + GUIFunctionalBlock.DEFAULT_WIDTH + 100, y + GUIFunctionalBlock.DEFAULT_HEIGHT + 100, Color.WHITE)));
    }

    @Test
    void canPotentiallyConnectWith() {
        assertFalse(blockTop.canPotentiallyConnectWith(blockBottom));
        assertFalse(blockBottom.canPotentiallyConnectWith(blockTop));
        blockBottom.mainConnector.disconnect();
        assertTrue(blockTop.canPotentiallyConnectWith(blockBottom));
        assertTrue(blockBottom.canPotentiallyConnectWith(blockTop));
        block.setPosition(xTop + GUIFunctionalBlock.DEFAULT_WIDTH, yTop + GUIFunctionalBlock.DEFAULT_HEIGHT);
        assertFalse(blockTop.canPotentiallyConnectWith(block));
        assertFalse(block.canPotentiallyConnectWith(blockTop));
    }

    @Test
    void connectWithStaticBlock_canNotConnect() {
        assertThrows(IllegalArgumentException.class, () -> blockBottom.connectWithStaticBlock(blockTop, connectionController));
    }

    @Test
    void connectWithStaticBlock_mainIsStatic() {
        blockBottom.mainConnector.disconnect();
        blockTop.connectWithStaticBlock(blockBottom, connectionController);
        assertTrue(blockBottom.mainConnector.isConnected());
        assertTrue(blockTop.subConnectors.get(0).isConnected());
        assertEquals(blockBottom, blockTop.subConnectors.get(0).getConnectedGUIBlock());
        assertEquals(blockTop, blockBottom.mainConnector.getConnectedGUIBlock());
    }

    @Test
    void connectWithStaticBlock_subIsStatic() {
        blockBottom.mainConnector.disconnect();
        blockBottom.connectWithStaticBlock(blockTop, connectionController);
        assertTrue(blockBottom.mainConnector.isConnected());
        assertTrue(blockTop.subConnectors.get(0).isConnected());
        assertEquals(blockBottom, blockTop.subConnectors.get(0).getConnectedGUIBlock());
        assertEquals(blockTop, blockBottom.mainConnector.getConnectedGUIBlock());
    }

    @Test
    void disconnectMainConnector() {
        assertTrue(blockBottom.mainConnector.isConnected());
        assertTrue(blockTop.subConnectors.get(0).isConnected());
        blockBottom.disconnectMainConnector();
        assertFalse(blockBottom.mainConnector.isConnected());
        assertFalse(blockTop.subConnectors.get(0).isConnected());
    }

    @Test
    void getConnectedBlocks() {
        assertEquals(1, block.getConnectedBlocks().size());
        assertTrue(block.getConnectedBlocks().contains(block));

        assertEquals(1, blockBottom.getConnectedBlocks().size());
        assertTrue(blockBottom.getConnectedBlocks().contains(blockBottom));

        assertEquals(2, blockTop.getConnectedBlocks().size());
        assertTrue(blockTop.getConnectedBlocks().contains(blockBottom));
        assertTrue(blockTop.getConnectedBlocks().contains(blockTop));
    }

    @Test
    void getColor() {
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, block.getColor());
    }

    @Test
    void getName() {
        assertEquals(name, block.getName());
    }

    @Test
    void terminate() {
        assertFalse(block.isTerminated());
        block.terminate();
        assertTrue(block.isTerminated());
        block.terminate();
        assertTrue(block.isTerminated());
    }

    @Test
    void isTerminated() {
        assertFalse(block.isTerminated());
        block.terminate();
        assertTrue(block.isTerminated());
    }

    @Test
    void setShapes() {
        block.setShapes();
        assertEquals(GUIFunctionalBlock.DEFAULT_WIDTH, block.getWidth());
        assertEquals(GUIFunctionalBlock.DEFAULT_HEIGHT, block.getTotalHeight());
        assertEquals(GUIFunctionalBlock.DEFAULT_BLOCK_COLOR, block.getColor());
        assertEquals(GUIFunctionalBlock.DEFAULT_MAIN_CONNECTOR_COLOR, block.mainConnector.getCollisionCircle().getColor());
        assertEquals(GUIFunctionalBlock.DEFAULT_SUB_CONNECTOR_COLOR, block.subConnectors.get(0).getCollisionCircle().getColor());
    }

    @Test
    void testClone() {
        GUIBlock clone = block.clone();
        assertNotEquals(clone, block);
        assertEquals(clone.getClass(), block.getClass());
        assertEquals(clone.getX(), block.getX());
        assertEquals(clone.getY(), block.getY());
        assertEquals(clone.getName(), block.getName());
    }
}