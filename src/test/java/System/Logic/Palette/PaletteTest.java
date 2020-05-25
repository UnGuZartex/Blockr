package System.Logic.Palette;

import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.Utility.IGUISystemBlockLink;
import GUI.Blocks.*;
import GUI.Panels.PalettePanel;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.DummyFunctionality;
import System.Logic.CommandHistory;
import System.Logic.ProgramArea.Handlers.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PaletteTest {

    Palette palette;
    Block block0, block1, block2, block;
    ArrayList<Block> blocks;
    GameWorldType type;
    ProcedureBlock procedure;
    ProgramArea programArea;

    PalettePanel panel;
    int cornerX, cornerY, width, height;
    Random random;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    static final int MIN_WIDTH = 100, MAX_WIDTH = 800, MIN_HEIGHT = 400, MAX_HEIGHT = 800;
    GUIBlock procedureGUI, functional, conditional, operator;
    String procedureName, functionalName, conditionalName, operatorName;
    BlockHandlerController blockHandlerController;
    IGUISystemBlockLink converter;
    PABlockHandler paBlockHandler;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        block0 = new WhileBlock();
        block1 = new NotBlock();
        block2 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        procedure = new ProcedureBlock();
        blocks = new ArrayList<>(Arrays.asList(block0, block1, block2));
        palette = new Palette(blocks);


        type = new LevelInitializer();
        programArea = new ProgramArea(type.createNewGameWorld(), new CommandHistory());

        random = new Random();
        cornerX = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        cornerY = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        width = random.nextInt(MAX_WIDTH + 1 - MIN_WIDTH) + MIN_WIDTH;
        height = random.nextInt(MAX_HEIGHT + 1 - MIN_HEIGHT) + MIN_HEIGHT;

        procedureName = "def";
        functionalName = "functional";
        conditionalName = "conditional";
        operatorName = "operator";

        procedureGUI = new GUIProcedureBlock(procedureName, 0,0);
        functional = new GUIFunctionalBlock(functionalName, 0,0);
        conditional = new GUIConditionalBlock(conditionalName, 0,0);
        operator = new GUIOperatorBlock(operatorName, 0,0);

        converter = new IGUISystemBlockLink();

        paBlockHandler = new PABlockHandler(Collections.singletonList(new FunctionalBlock(new DummyFunctionality())), programArea);

        blockHandlerController = new BlockHandlerController(converter, paBlockHandler);

        panel = new PalettePanel(cornerX, cornerY, width, height, Arrays.asList(procedureGUI, functional, conditional, operator));

        palette.subscribe(panel);

        panel.getNewBlock(0);
        palette.onProcedureCreated(procedure);
    }

    @AfterEach
    void tearDown() {
        palette.unsubscribe(panel);
        type = null;
        block0 = null;
        block1 = null;
        block2 = null;
        blocks = null;
        block = null;
        palette = null;
        programArea = null;
        random = null;
        procedureName = null;
        functionalName = null;
        conditionalName = null;
        operatorName = null;
        procedureGUI = null;
        functional = null;
        conditional = null;
        operator = null;
        panel = null;
    }

    @Test
    void palette_invalidBlocks() {
        assertThrows(IllegalArgumentException.class, () -> new Palette(new ArrayList<>()));
    }

    @Test
    void isValidBlockList_nullList() {
        assertFalse(Palette.isValidBlockList(null));
    }

    @Test
    void isValidBlockList_emptyList() {
        assertFalse(Palette.isValidBlockList(new ArrayList<>()));
    }

    @Test
    void isValidBlockList_containingNull() {
        assertFalse(Palette.isValidBlockList(new ArrayList<>(Arrays.asList(block0, null, block1, block2))));
        assertFalse(Palette.isValidBlockList(new ArrayList<>(Arrays.asList(block0, null, block1, null, block2))));
    }

    @Test
    void isValidBlockList_valid() {
        assertTrue(Palette.isValidBlockList(blocks));
    }

    @Test
    void getNewBlock() {
        block = palette.getNewBlock(0);
        assertNotEquals(blocks.get(0), block);
        assertEquals(blocks.get(0).getClass(), block.getClass());
        assertNotEquals(blocks.get(0).getFunctionality(), block.getFunctionality());
        assertEquals(blocks.get(0).getFunctionality().getClass(), block.getFunctionality().getClass());

        block = palette.getNewBlock(1);
        assertNotEquals(blocks.get(1), block);
        assertEquals(blocks.get(1).getClass(), block.getClass());
        assertNotEquals(blocks.get(1).getFunctionality(), block.getFunctionality());
        assertEquals(blocks.get(1).getFunctionality().getClass(), block.getFunctionality().getClass());

        block = palette.getNewBlock(2);
        assertNotEquals(blocks.get(2), block);
        assertEquals(blocks.get(2).getClass(), block.getClass());
        assertEquals(blocks.get(2).getFunctionality(), block.getFunctionality());
        assertEquals(blocks.get(2).getFunctionality().getClass(), block.getFunctionality().getClass());

        block = palette.getNewBlock(3);
        assertEquals(procedure, ((ProcedureCall)block).getProcedure());
    }

    @Test
    void getNewBlock_invalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(blocks.size() + 1)); // +1 for the caller
    }

    @Test
    void onProcedureCreated() {
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(4));
        ProcedureBlock block = new ProcedureBlock();
        palette.onProcedureCreated(block);
        Block caller = palette.getNewBlock(4);
        assertEquals(block, ((ProcedureCall)caller).getProcedure());
    }

    @Test
    void onProcedureDeleted() {
        ProcedureBlock procedure1 = new ProcedureBlock();
        ProcedureBlock procedure2 = new ProcedureBlock();
        palette.onProcedureCreated(procedure1);
        palette.onProcedureCreated(procedure2);
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(blocks.size() + 3));

        palette.getNewBlock(blocks.size() + 2);
        palette.onProcedureDeleted(procedure2);
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(blocks.size() + 2));

        palette.getNewBlock(blocks.size() + 1);
        palette.onProcedureDeleted(procedure);
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(blocks.size() + 1));

        palette.getNewBlock(blocks.size());
        palette.onProcedureDeleted(procedure1);
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(blocks.size()));
    }

    @Test
    void onProcedureDeleted_notInPalette() {
        ProcedureBlock procedure1 = new ProcedureBlock();
        assertThrows(IllegalArgumentException.class, () -> palette.onProcedureDeleted(procedure1));
    }
}