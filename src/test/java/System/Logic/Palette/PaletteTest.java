package System.Logic.Palette;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Blocks.NotBlock;
import System.BlockStructure.Blocks.WhileBlock;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.NotFunctionality;
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
    GameWorld gameWorld;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        block0 = new WhileBlock(new CavityFunctionality(gameWorld));
        block1 = new NotBlock(new NotFunctionality(gameWorld));
        block2 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(), gameWorld));

        blocks = new ArrayList<>(Arrays.asList(block0, block1, block2));
        palette = new Palette(blocks);
    }

    @AfterEach
    void tearDown() {
        type = null;
        gameWorld = null;
        block0 = null;
        block1 = null;
        block2 = null;
        blocks = null;
        block = null;
        palette = null;
    }

    @Test
    void palette_invalidBlocks() {
        assertFalse(Palette.isValidBlockList(new ArrayList<>()));
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
        assertEquals(blocks.get(0).getFunctionality().getGameWorld(), block.getFunctionality().getGameWorld());

        block = palette.getNewBlock(1);
        assertNotEquals(blocks.get(1), block);
        assertEquals(blocks.get(1).getClass(), block.getClass());
        assertNotEquals(blocks.get(1).getFunctionality(), block.getFunctionality());
        assertEquals(blocks.get(1).getFunctionality().getClass(), block.getFunctionality().getClass());
        assertEquals(blocks.get(1).getFunctionality().getGameWorld(), block.getFunctionality().getGameWorld());

        block = palette.getNewBlock(2);
        assertNotEquals(blocks.get(2), block);
        assertEquals(blocks.get(2).getClass(), block.getClass());
        assertEquals(blocks.get(2).getFunctionality(), block.getFunctionality());
        assertEquals(blocks.get(2).getFunctionality().getClass(), block.getFunctionality().getClass());
        assertEquals(blocks.get(2).getFunctionality().getGameWorld(), block.getFunctionality().getGameWorld());
    }

    @Test
    void getNewBlock_invalidIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(blocks.size()));
    }
}