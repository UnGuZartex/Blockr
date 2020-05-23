package Controllers;

import Controllers.ControllerClasses.BlockHandlerController;
import GUI.Blocks.*;
import GUI.Panel.PalettePanel;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.DummyFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.CommandHistory;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IGUISystemBlockLinkTest {

    GameWorldType type;
    IGUISystemBlockLink converter;
    GUIBlock cavity, functional, conditional, operator;
    String cavityName, functionalName, conditionalName, operatorName;
    int cornerX, cornerY, width, height;
    Random random;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    static final int MIN_WIDTH = 100, MAX_WIDTH = 800, MIN_HEIGHT = 100, MAX_HEIGHT = 800;
    PalettePanel palette;
    Block block0, block1, block2;
    IGUIBlock guiBlock0, guiBlock1, guiBlock2;
    PABlockHandler paBlockHandler;
    BlockHandlerController blockHandlerController;
    ProgramArea programArea;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        converter = new IGUISystemBlockLink();
        programArea = new ProgramArea(type.createNewGameWorld(), new CommandHistory());

        random = new Random();
        cornerX = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        cornerY = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        width = random.nextInt(MAX_WIDTH + 1 - MIN_WIDTH) + MIN_WIDTH;
        height = random.nextInt(MAX_HEIGHT + 1 - MIN_HEIGHT) + MIN_HEIGHT;

        cavityName = "Cavity";
        functionalName = "functional";
        conditionalName = "conditional";
        operatorName = "operator";

        cavity = new GUICavityBlock(cavityName, 0,0);
        functional = new GUIFunctionalBlock(functionalName, 0,0);
        conditional = new GUIConditionalBlock(conditionalName, 0,0);
        operator = new GUIOperatorBlock(operatorName, 0,0);

        paBlockHandler = new PABlockHandler(Collections.singletonList(new FunctionalBlock(new DummyFunctionality())), programArea);

        blockHandlerController = new BlockHandlerController(converter, paBlockHandler);

        palette = new PalettePanel(cornerX, cornerY, width, height, new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator)));

        block0 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        block1 = new PredicateBlock(new PredicateFunctionality(type.getAllPredicates().get(0)));
        block2 = new NotBlock();
        guiBlock0 = palette.getNewBlock(1);
        guiBlock1 = palette.getNewBlock(2);
        guiBlock2 = palette.getNewBlock(3);
        converter.addBlockPair(guiBlock0, block0);
        converter.addBlockPair(guiBlock1, block1);
        converter.addBlockPair(guiBlock2, block2);
    }

    @AfterEach
    void tearDown() {
        converter = null;
        random = null;
        cavityName = null;
        functionalName = null;
        conditionalName = null;
        operatorName = null;
        cavity = null;
        functional = null;
        conditional = null;
        operator = null;
        palette = null;
        block0 = null;
        block1 = null;
        block2 = null;
        guiBlock0 = null;
        guiBlock1 = null;
        guiBlock2 = null;
        programArea = null;
    }

    @Test
    void getBlockFromGUIBlock_notInDatabase() {
        IGUIBlock block = palette.getNewBlock(0);
        assertThrows(IllegalArgumentException.class, () -> converter.getBlockFromGUIBlock(block));
    }

    @Test
    void getBlockFromGUIBlock() {
        assertEquals(block0, converter.getBlockFromGUIBlock(guiBlock0));
        assertEquals(block1, converter.getBlockFromGUIBlock(guiBlock1));
        assertEquals(block2, converter.getBlockFromGUIBlock(guiBlock2));
    }

    @Test
    void getGUIBlockFromBlock_notInDatabase() {
        Block block = new NotBlock();
        assertThrows(IllegalArgumentException.class, () -> converter.getGUIBlockFromBlock(block));
    }

    @Test
    void getGUIBlockFromBlock() {
        assertEquals(guiBlock0, converter.getGUIBlockFromBlock(block0));
        assertEquals(guiBlock1, converter.getGUIBlockFromBlock(block1));
        assertEquals(guiBlock2, converter.getGUIBlockFromBlock(block2));
    }

    @Test
    void addBlockPair() {
        IGUIBlock guiBlock = palette.getNewBlock(0);
        Block block = new WhileBlock();
        converter.addBlockPair(guiBlock, block);

        assertEquals(block, converter.getBlockFromGUIBlock(guiBlock));
        assertEquals(guiBlock, converter.getGUIBlockFromBlock(block));
    }

    @Test
    void removeBlock() {
        IGUIBlock guiBlock = palette.getNewBlock(0);
        Block block = new WhileBlock();
        converter.addBlockPair(guiBlock, block);
        assertEquals(block, converter.getBlockFromGUIBlock(guiBlock));
        assertEquals(guiBlock, converter.getGUIBlockFromBlock(block));
        converter.removeBlock(guiBlock);

        assertThrows(IllegalArgumentException.class, () -> converter.getBlockFromGUIBlock(guiBlock));
        assertThrows(IllegalArgumentException.class, () -> converter.getGUIBlockFromBlock(block));
    }
}