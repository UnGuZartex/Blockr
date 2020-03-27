package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Blocks.MoveForwardBlock;
import System.GameState.GameState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PABlockHandlerTest {

    PABlockHandler handler;
    private static final int MAX_BLOCKS = 3;

    @BeforeEach
    void setUp() {
        handler = new PABlockHandler();
        GameState.setMaxAmountOfBlocks(MAX_BLOCKS);
    }

    @AfterEach
    void tearDown() {
        handler = null;
    }

    @Test
    void hasProperNbBlocks() {
        assertTrue(handler.hasProperNbBlocks());
    }

    @Test
    void getFromPalette() {
        assertTrue(handler.getFromPalette("IF") instanceof IfBlock);
        handler.addToPA(new MoveForwardBlock());
        handler.addToPA(new MoveForwardBlock());
        handler.addToPA(new MoveForwardBlock());
        assertNull(handler.getFromPalette("IF"));
    }

    @Test
    void addToPA() {
        assertEquals(0, handler.getPA().getAllBlocksCount());
        handler.addToPA(new MoveForwardBlock());
        assertEquals(1, handler.getPA().getAllBlocksCount());
        handler.addToPA(new MoveForwardBlock());
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.addToPA(new MoveForwardBlock());
        assertEquals(3, handler.getPA().getAllBlocksCount());
        handler.addToPA(new MoveForwardBlock());
        assertEquals(3, handler.getPA().getAllBlocksCount());
        handler.addToPA(new MoveForwardBlock());
    }

    @Test
    void connectToExistingBlock() {
        assertEquals(0, handler.getPA().getAllBlocksCount());
        Block block1 = new MoveForwardBlock();
        Block block2 = new MoveForwardBlock();
        Block block3 = new MoveForwardBlock();
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
        Block block1 = new MoveForwardBlock();
        Block block2 = new MoveForwardBlock();
        Block block3 = new MoveForwardBlock();
        handler.addToPA(block1);
        handler.connectToExistingBlock(block2, block1.getSubConnectorAt(0));
        handler.connectToExistingBlock(block3, block2.getSubConnectorAt(0));
        assertEquals(3, handler.getPA().getAllBlocksCount());

        handler.disconnectInPA(block3);
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.disconnectInPA(block2);
        assertEquals(1, handler.getPA().getAllBlocksCount());

    }

    @Test
    void deleteProgram() {
        Block block = new MoveForwardBlock();
        assertEquals(0, handler.getPA().getAllBlocksCount());
        handler.addToPA(block);
        assertEquals(1, handler.getPA().getAllBlocksCount());
        handler.addToPA(new MoveForwardBlock());
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.deleteProgram(block);
    }

    @Test
    void hasReachedMaxBlocks() {
        assertFalse(handler.hasReachedMaxBlocks());
        handler.addToPA(new MoveForwardBlock());
        assertFalse(handler.hasReachedMaxBlocks());
        handler.addToPA(new MoveForwardBlock());
        assertFalse(handler.hasReachedMaxBlocks());
        handler.addToPA(new MoveForwardBlock());
        assertTrue(handler.hasReachedMaxBlocks());
        handler.addToPA(new MoveForwardBlock());
    }
}