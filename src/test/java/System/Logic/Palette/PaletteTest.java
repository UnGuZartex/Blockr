package System.Logic.Palette;

import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PaletteTest {

    Palette palette;
    Block block0, block1, block2, block;
    ArrayList<Block> blocks;
    GameWorldType type;
    ProcedureBlock procedure;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        block0 = new WhileBlock();
        block1 = new NotBlock();
        block2 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        procedure = new ProcedureBlock();
        blocks = new ArrayList<>(Arrays.asList(block0, block1, block2));
        palette = new Palette(blocks);
        palette.createCaller(procedure);
    }

    @AfterEach
    void tearDown() {
        type = null;
        block0 = null;
        block1 = null;
        block2 = null;
        blocks = null;
        block = null;
        palette = null;
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
    void createCaller() {
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(4));
        ProcedureBlock block = new ProcedureBlock();
        palette.createCaller(block);
        Block caller = palette.getNewBlock(4);
        assertEquals(block, ((ProcedureCall)caller).getProcedure());
    }

    @Test
    void deleteCaller() {
        ProcedureBlock procedure1 = new ProcedureBlock();
        ProcedureBlock procedure2 = new ProcedureBlock();
        palette.createCaller(procedure1);
        palette.createCaller(procedure2);

        assertEquals(2, palette.deleteCaller(procedure2));
        assertEquals(0, palette.deleteCaller(procedure));
        assertEquals(0, palette.deleteCaller(procedure1));
    }

    @Test
    void deleteCaller_notInPalette() {
        ProcedureBlock procedure1 = new ProcedureBlock();
        assertThrows(IllegalArgumentException.class, () -> palette.deleteCaller(procedure1));
    }
}