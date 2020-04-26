package Controllers.ControllerClasses;

import Controllers.BlockLinkDatabase;
import GUI.Blocks.*;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BlockHandlerControllerTest {

    PalettePanel palette;
    int cornerX, cornerY, width, height;
    Random random;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    static final int MIN_WIDTH = 100, MAX_WIDTH = 800, MIN_HEIGHT = 100, MAX_HEIGHT = 800;
    BlockHandlerController controller;
    BlockLinkDatabase converter;
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
        converter = new BlockLinkDatabase();

        cavityName = "Cavity";
        functionalName = "functional";
        conditionalName = "conditional";
        operatorName = "operator";

        cavity = new GUICavityBlock(cavityName, 0,0);
        functional = new GUIFunctionalBlock(functionalName, 0,0);
        conditional = new GUIConditionalBlock(conditionalName, 0,0);
        operator = new GUIOperatorBlock(operatorName, 0,0);

        controller = new BlockHandlerController(converter, paBlockHandler);

        palette = new PalettePanel(cornerX, cornerY, width, height, new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator)));

        blockIf = new IfBlock();
        blockPredicate = new PredicateBlock(new PredicateFunctionality(type.getAllPredicates().get(0)));
        blockNot = new NotBlock();
        guiBlockCavity = palette.getNewBlock(0);
        guiBlockConditional = palette.getNewBlock(2);
        guiBlockOperator = palette.getNewBlock(3);
        converter.addBlockPair(guiBlockCavity, blockIf);
        converter.addBlockPair(guiBlockConditional, blockPredicate);
        converter.addBlockPair(guiBlockOperator, blockNot);
    }

    @AfterEach
    void tearDown() {
        random = null;
        controller = null;
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
    }

    @Test
    void blockHandlerController_invalidConverter() {
        assertThrows(IllegalArgumentException.class, () -> new BlockHandlerController(null, paBlockHandler));
    }

    @Test
    void blockHandlerController_invalidBlockHandler() {
        assertThrows(IllegalArgumentException.class, () -> new BlockHandlerController(converter, null));
    }

    @Test
    void addBlockToPA() {
        controller.addBlockToPA(guiBlockOperator, 4);
        assertTrue(converter.getBlockFromGUIBlock(guiBlockOperator) instanceof NotBlock);
        assertEquals(1, paBlockHandler.getPA().getAllBlocksCount());


        controller.addBlockToPA(guiBlockCavity, 5);
        assertTrue(converter.getBlockFromGUIBlock(guiBlockCavity) instanceof WhileBlock);
        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());

        controller.addBlockToPA(guiBlockConditional, 3);
        assertTrue(converter.getBlockFromGUIBlock(guiBlockConditional) instanceof PredicateBlock);
        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void deleteFromPA() {
        controller.addBlockToPA(guiBlockOperator, 4);
        assertTrue(converter.getBlockFromGUIBlock(guiBlockOperator) instanceof NotBlock);
        assertEquals(1, paBlockHandler.getPA().getAllBlocksCount());

        controller.addBlockToPA(guiBlockCavity, 5);
        assertTrue(converter.getBlockFromGUIBlock(guiBlockCavity) instanceof WhileBlock);
        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());

        controller.addBlockToPA(guiBlockConditional, 3);
        assertTrue(converter.getBlockFromGUIBlock(guiBlockConditional) instanceof PredicateBlock);
        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());

        controller.deleteFromPA(guiBlockOperator);
        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());
        controller.deleteFromPA(guiBlockConditional);
        assertEquals(1, paBlockHandler.getPA().getAllBlocksCount());
        controller.deleteFromPA(guiBlockCavity);
        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void getHighlightedBlock() {
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        IGUIBlock guiIf = palette.getNewBlock(0);
        converter.addBlockPair(guiIf, ifBlock);

        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        IGUIBlock guiWallInFront = palette.getNewBlock(2);
        converter.addBlockPair(guiWallInFront, wallInFront);

        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        IGUIBlock guiTurnLeft = palette.getNewBlock(1);
        converter.addBlockPair(guiTurnLeft, turnLeft);

        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        IGUIBlock guiMoveForward = palette.getNewBlock(1);
        converter.addBlockPair(guiMoveForward, moveForward);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, ifBlock.getSubConnectorAt(0));

        assertEquals(guiIf, controller.getHighlightedBlock());
        paBlockHandler.getPA().addProgramRunCommand();
        assertEquals(guiTurnLeft, controller.getHighlightedBlock());
        paBlockHandler.getPA().addProgramRunCommand();
        assertEquals(guiMoveForward, controller.getHighlightedBlock());
        paBlockHandler.getPA().addProgramRunCommand();
        assertNull(controller.getHighlightedBlock());
    }
}