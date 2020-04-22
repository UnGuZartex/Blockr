package System.Logic.ProgramArea;

import GameWorld.Level;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.CavityFunctionality;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PABlockHandlerTest {

    PABlockHandler handler;
    private static final int MAX_BLOCKS = 3;
    Level level;

    @BeforeEach
    void setUp() {
        LevelInitializer init = new LevelInitializer();
        level = (Level) init.createNewGameWorld();
        handler = new PABlockHandler(Collections.singletonList(new IfBlock(new CavityFunctionality(level))));
        handler.setMaxBlocks(MAX_BLOCKS);
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
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        assertNull(handler.getFromPalette(0));
    }

    @Test
    void addToPA() {
        assertEquals(0, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        assertEquals(1, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        assertEquals(3, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        assertEquals(3, handler.getPA().getAllBlocksCount());
    }

    @Test
    void connectToExistingBlock() {
        assertEquals(0, handler.getPA().getAllBlocksCount());
        Block block1 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level));
        Block block2 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level));
        Block block3 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level));
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
        Block block1 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level));
        Block block2 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level));
        Block block3 = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level));
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
        Block block = new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level));
        assertEquals(0, handler.getPA().getAllBlocksCount());
        handler.addToPA(block);
        assertEquals(1, handler.getPA().getAllBlocksCount());
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        assertEquals(2, handler.getPA().getAllBlocksCount());
        handler.deleteProgram(block);
        assertEquals(1, handler.getPA().getAllBlocksCount());
    }

    @Test
    void setMaxBlocks() {
        for (int i = 0; i < MAX_BLOCKS; i++) {
            assertEquals(i, handler.getPA().getAllBlocksCount());
            handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
            assertEquals(i+1, handler.getPA().getAllBlocksCount());
        }
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        assertEquals(MAX_BLOCKS, handler.getPA().getAllBlocksCount());

        handler.setMaxBlocks(MAX_BLOCKS + 1);
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(new MoveForwardAction(),level)));
        assertEquals(MAX_BLOCKS + 1, handler.getPA().getAllBlocksCount());
    }
}