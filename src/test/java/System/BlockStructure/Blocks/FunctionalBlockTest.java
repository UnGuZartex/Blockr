package System.BlockStructure.Blocks;


import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalBlockTest {

    GameWorldType type;
    FunctionalBlock blockConnectedTop, blockConnectedBottom, blockNotConnected, blockConnectedTopBottom;
    Block block;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        blockConnectedBottom = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        blockConnectedTop = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        blockNotConnected = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2)));
        blockConnectedTopBottom = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2)));

        ConnectionHandler handler = new ConnectionHandler();
        handler.connect(blockConnectedTopBottom, blockConnectedBottom.getSubConnectorAt(0));
        handler.connect(blockConnectedTop, blockConnectedTopBottom.getSubConnectorAt(0));
    }

    @AfterEach
    void tearDown() {
        type = null;
        blockConnectedTop = null;
        blockConnectedBottom = null;
        blockNotConnected = null;
        blockConnectedTopBottom = null;
        block = null;
    }

    @Test
    void getNbSubConnectors() {
        assertEquals(1, blockConnectedBottom.getNbSubConnectors());
        assertEquals(1, blockConnectedTop.getNbSubConnectors());
        assertEquals(1, blockNotConnected.getNbSubConnectors());
        assertEquals(1, blockConnectedTopBottom.getNbSubConnectors());
    }

    @Test
    void hasProperConnections() {
        assertTrue(blockConnectedTop.hasProperConnections());
        assertTrue(blockConnectedBottom.hasProperConnections());
        assertTrue(blockNotConnected.hasProperConnections());
        assertTrue(blockConnectedTopBottom.hasProperConnections());
    }

    @Test
    void hasNext() {
        assertFalse(blockConnectedTop.hasNext());
        assertTrue(blockConnectedBottom.hasNext());
        assertFalse(blockNotConnected.hasNext());
        assertTrue(blockConnectedTopBottom.hasNext());
    }

    @Test
    void getNext() {
        assertNull(blockConnectedTop.getNext());
        assertNull(blockNotConnected.getNext());
        assertEquals(blockConnectedTopBottom, blockConnectedBottom.getNext());
        assertEquals(blockConnectedTop, blockConnectedTopBottom.getNext());
    }

    @Test
    void cloneTest() {
        block = blockConnectedTopBottom.clone();
        assertNotEquals(block, blockConnectedTopBottom);
        assertEquals(block.getFunctionality(), blockConnectedTopBottom.getFunctionality());
        assertEquals(block.getClass(), blockConnectedTopBottom.getClass());
        assertFalse(block.getSubConnectorAt(0).isConnected());
        assertFalse(block.getMainConnector().isConnected());
    }

    @Test
    void getBlockAtIndex_negativeIndex() {
        assertNull(blockConnectedTop.getBlockAtIndex(-1));
    }

    @Test
    void getBlockAtIndex_indexOutOfRange() {
        assertNull(blockConnectedTop.getBlockAtIndex(1));
        assertNull(blockNotConnected.getBlockAtIndex(1));
        assertNull(blockConnectedBottom.getBlockAtIndex(3));
        assertNull(blockConnectedTopBottom.getBlockAtIndex(2));
    }

    @Test
    void getBlockAtIndex() {
        assertEquals(blockConnectedTop, blockConnectedTop.getBlockAtIndex(0));

        assertEquals(blockNotConnected, blockNotConnected.getBlockAtIndex(0));

        assertEquals(blockConnectedBottom, blockConnectedBottom.getBlockAtIndex(0));
        assertEquals(blockConnectedTopBottom, blockConnectedBottom.getBlockAtIndex(1));
        assertEquals(blockConnectedTop, blockConnectedBottom.getBlockAtIndex(2));

        assertEquals(blockConnectedTopBottom, blockConnectedTopBottom.getBlockAtIndex(0));
        assertEquals(blockConnectedTop, blockConnectedTopBottom.getBlockAtIndex(1));
    }

    @Test
    void getIndexOfBlock_invalidBlock() {
        assertEquals(-1, blockNotConnected.getIndexOfBlock(blockConnectedTop));
    }

    @Test
    void getIndexOfBlock() {
        assertEquals(0, blockConnectedTop.getIndexOfBlock(blockConnectedTop));

        assertEquals(0, blockNotConnected.getIndexOfBlock(blockNotConnected));

        assertEquals(0, blockConnectedBottom.getIndexOfBlock(blockConnectedBottom));
        assertEquals(1, blockConnectedBottom.getIndexOfBlock(blockConnectedTopBottom));
        assertEquals(2, blockConnectedBottom.getIndexOfBlock(blockConnectedTop));

        assertEquals(0, blockConnectedTopBottom.getIndexOfBlock(blockConnectedTopBottom));
        assertEquals(1, blockConnectedTopBottom.getIndexOfBlock(blockConnectedTop));
    }
}