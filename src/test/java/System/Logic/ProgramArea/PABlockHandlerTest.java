package System.Logic.ProgramArea;

import GameWorldUtility.MoveForwardAction;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.GameState.GameState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PABlockHandlerTest {

    PABlockHandler handler;
    private static final int MAX_BLOCKS = 3;

    @BeforeEach
    void setUp() {
        handler = new PABlockHandler((List<Block>) new IfBlock());
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
        assertTrue(handler.getFromPalette(0) instanceof IfBlock);
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        assertNull(handler.getFromPalette(0));
    }

    @Test
    void addToPA() {
        assertEquals(0, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        assertEquals(1, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        assertEquals(3, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        assertEquals(3, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
    }

    @Test
    void connectToExistingBlock() {
        assertEquals(0, handler.getPA().getAllBlocksCount());
        Block block1 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        Block block2 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        Block block3 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
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
        Block block1 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        Block block2 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        Block block3 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
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
        Block block = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction()));
        assertEquals(0, handler.getPA().getAllBlocksCount());
        handler.addToPA(block);
        assertEquals(1, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.deleteProgram(block);
    }

    @Test
    void hasReachedMaxBlocks() {
        assertFalse(handler.hasReachedMaxBlocks());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        assertFalse(handler.hasReachedMaxBlocks());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        assertFalse(handler.hasReachedMaxBlocks());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
        assertTrue(handler.hasReachedMaxBlocks());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction())));
    }
}