package System.Logic.ProgramArea;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.Logic.CommandHistory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PABlockHandlerTest {

    PABlockHandler handler;
    ProgramArea programArea;
    private static final int MAX_BLOCKS = 3;
    GameWorldType type;
    GameWorld gameWorld;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        programArea = new ProgramArea(gameWorld, new CommandHistory());
        handler = new PABlockHandler(Collections.singletonList(new IfBlock()), programArea);
        handler.setMaxBlocks(MAX_BLOCKS);
    }

    @AfterEach
    void tearDown() {
        handler = null;
        programArea = null;
        type = null;
        gameWorld = null;
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
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertTrue(handler.hasProperNbBlocks());
    }

    @Test
    void getFromPalette() {
        assertTrue(handler.getFromPalette(0) instanceof IfBlock);
    }

    @Test
    void getFromPalette_maxNumberBlocksReached() {
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1))));
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertThrows(IllegalStateException.class, () -> handler.getFromPalette(0));
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
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
        assertEquals(3, handler.getPA().getAllBlocksCount());
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
    void setMaxBlocks() {
        for (int i = 0; i < MAX_BLOCKS; i++) {
            assertEquals(i, handler.getPA().getAllBlocksCount());
            handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))));
            assertEquals(i+1, handler.getPA().getAllBlocksCount());
        }
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertEquals(MAX_BLOCKS, handler.getPA().getAllBlocksCount());

        handler.setMaxBlocks(MAX_BLOCKS + 1);
        handler.addToPA(new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))));
        assertEquals(MAX_BLOCKS + 1, handler.getPA().getAllBlocksCount());
    }

}