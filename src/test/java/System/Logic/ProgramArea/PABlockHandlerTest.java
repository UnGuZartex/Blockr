package System.Logic.ProgramArea;

import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.IGUI_System_BlockLink;
import GUI.Blocks.*;
import GUI.Panel.PalettePanel;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.DummyFunctionality;
import System.Logic.CommandHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PABlockHandlerTest {

    PABlockHandler handler;
    ProgramArea programArea;
    private static final int MAX_BLOCKS = 3;
    GameWorldType type;
    GameWorld gameWorld;
    ProcedureBlock block;

    PalettePanel panel;
    int cornerX, cornerY, width, height;
    Random random;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    static final int MIN_WIDTH = 100, MAX_WIDTH = 800, MIN_HEIGHT = 400, MAX_HEIGHT = 800;
    GUIBlock procedure, functional, conditional, operator;
    String procedureName, functionalName, conditionalName, operatorName;
    BlockHandlerController blockHandlerController;
    IGUI_System_BlockLink converter;
    PABlockHandler paBlockHandler;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        programArea = new ProgramArea(gameWorld, new CommandHistory());
        block = new ProcedureBlock();
        handler = new PABlockHandler(Collections.singletonList(block), programArea);
        handler.setMaxBlocks(MAX_BLOCKS);

        random = new Random();
        cornerX = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        cornerY = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        width = random.nextInt(MAX_WIDTH + 1 - MIN_WIDTH) + MIN_WIDTH;
        height = random.nextInt(MAX_HEIGHT + 1 - MIN_HEIGHT) + MIN_HEIGHT;

        procedureName = "Def";
        functionalName = "functional";
        conditionalName = "conditional";
        operatorName = "operator";

        procedure = new GUIProcedureBlock("New Def", 0,0);
        functional = new GUIFunctionalBlock(functionalName, 0,0);
        conditional = new GUIConditionalBlock(conditionalName, 0,0);
        operator = new GUIOperatorBlock(operatorName, 0,0);

        converter = new IGUI_System_BlockLink();

        paBlockHandler = new PABlockHandler(Collections.singletonList(new FunctionalBlock(new DummyFunctionality())), programArea);

        blockHandlerController = new BlockHandlerController(converter, paBlockHandler);

        panel = new PalettePanel(cornerX, cornerY, width, height, Arrays.asList(procedure, functional, conditional, operator));

        handler.subscribe(panel);

        block.subscribe(handler.getPalette());

    }

    @AfterEach
    void tearDown() {
        type = null;
        gameWorld = null;
        handler = null;
        programArea = null;

        random = null;
        procedureName = null;
        functionalName = null;
        conditionalName = null;
        operatorName = null;
        procedure = null;
        functional = null;
        conditional = null;
        operator = null;
        panel = null;
    }

    @Test
    void paBlockHandler_invalidProgramArea() {
        assertThrows(IllegalArgumentException.class, () -> new PABlockHandler(Collections.singletonList(block), null));
    }

    @Test
    void isValidProgramArea() {
        assertTrue(PABlockHandler.isValidProgramArea(programArea));
        assertFalse(PABlockHandler.isValidProgramArea(null));
    }

    @Test
    void getPalette() {
        assertEquals(block.getClass(), handler.getPalette().getNewBlock(0).getClass());
    }

    @Test
    void getPA() {
        assertEquals(programArea, handler.getPA());
    }

    @Test
    void hasProperNbBlocks() {
        assertTrue(handler.hasProperNbBlocks());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        assertTrue(handler.hasProperNbBlocks());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1))));
        assertTrue(handler.hasProperNbBlocks());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertTrue(handler.hasProperNbBlocks());
    }

    @Test
    void getFromPalette() {
        assertEquals(block.getClass(), handler.getFromPalette(0).getClass());
    }

    @Test
    void getFromPalette_maxNumberBlocksReached() {
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertThrows(IllegalStateException.class, () -> handler.getFromPalette(0));
    }

    @Test
    void addToPA_maxBlocksReached() {
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertThrows(IllegalStateException.class, () -> handler.addToPA(new WhileBlock()));
    }

    @Test
    void addToPA() {
        assertEquals(0, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        assertEquals(1, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1))));
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertEquals(3, handler.getPA().getAllBlocksCount());
    }

    @Test
    void addToPA_procedure() {
        panel.getNewBlock(0);
        assertThrows(IndexOutOfBoundsException.class, () -> handler.getFromPalette(1));
        Block block = handler.getFromPalette(0);
        handler.addToPA(block);
        ProcedureCall call = (ProcedureCall) handler.getFromPalette(1);
        assertEquals(block, call.getProcedure());
    }

    @Test
    void connectToExistingBlock() {
        assertEquals(0, handler.getPA().getAllBlocksCount());
        Block block1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        Block block2 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        Block block3 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2)));
        handler.addToPA(block1);
        assertEquals(1, handler.getPA().getAllBlocksCount());
        handler.connectToExistingBlock(block2, block1.getSubConnectorAt(0));
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.connectToExistingBlock(block3, block2.getSubConnectorAt(0));
        assertEquals(3, handler.getPA().getAllBlocksCount());
    }

    @Test
    void disconnectInPA() {
        assertEquals(0, handler.getPA().getAllBlocksCount());
        Block block1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        Block block2 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        Block block3 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2)));
        handler.addToPA(block1);
        handler.connectToExistingBlock(block2, block1.getSubConnectorAt(0));
        handler.connectToExistingBlock(block3, block2.getSubConnectorAt(0));
        assertEquals(3, handler.getPA().getAllBlocksCount());

        handler.disconnectInPA(block3);
        assertEquals(3, handler.getPA().getAllBlocksCount());
        handler.disconnectInPA(block2);
        assertEquals(3, handler.getPA().getAllBlocksCount());

    }

    @Test
    void deleteProgram() {
        Block block = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        assertEquals(0, handler.getPA().getAllBlocksCount());
        handler.addToPA(block);
        assertEquals(1, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1))));
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.deleteProgram(block);
        assertEquals(1, handler.getPA().getAllBlocksCount());
    }

    @Test
    void deleteProgram_procedure() {
        panel.getNewBlock(0);
        Block block = handler.getFromPalette(0);
        handler.addToPA(block);
        ProcedureCall call = (ProcedureCall) handler.getFromPalette(1);
        assertEquals(block, call.getProcedure());
        block.terminate();
        handler.deleteProgram(block);
        assertThrows(IndexOutOfBoundsException.class, () -> handler.getFromPalette(1));
    }

    @Test
    void setMaxBlocks() {
        for (int i = 0; i < MAX_BLOCKS; i++) {
            assertEquals(i, handler.getPA().getAllBlocksCount());
            handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
            assertEquals(i+1, handler.getPA().getAllBlocksCount());
        }
        assertThrows(IllegalStateException.class, () -> handler.addToPA(new WhileBlock()));
        assertEquals(MAX_BLOCKS, handler.getPA().getAllBlocksCount());

        handler.setMaxBlocks(MAX_BLOCKS + 1);
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertEquals(MAX_BLOCKS + 1, handler.getPA().getAllBlocksCount());
        assertThrows(IllegalStateException.class, () -> handler.addToPA(new WhileBlock()));
    }

    @Test
    void subscribedListener() {
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertThrows(IllegalStateException.class, () -> panel.getNewBlock(0));
    }

    @Test
    void unsubscribedListener() {
        handler.unSubscribe(panel);
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertEquals(procedure.getClass(), panel.getNewBlock(0).getClass());
    }
}