package System.BlockStructure.Blocks;


import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

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
        assertNull(blockConnectedTop.getBlockAtIndex(-1, new Stack<>()));
    }

    @Test
    void getBlockAtIndex_indexOutOfRange() {
        assertNull(blockConnectedTop.getBlockAtIndex(1, new Stack<>()));
        assertNull(blockNotConnected.getBlockAtIndex(1, new Stack<>()));
        assertNull(blockConnectedBottom.getBlockAtIndex(3, new Stack<>()));
        assertNull(blockConnectedTopBottom.getBlockAtIndex(2, new Stack<>()));
        assertNull(blockConnectedTop.getBlockAtIndex(1, new Stack<>()));
    }

    @Test
    void getBlockAtIndex() {
        assertEquals(blockConnectedTop, blockConnectedTop.getBlockAtIndex(0, new Stack<>()));

        assertEquals(blockNotConnected, blockNotConnected.getBlockAtIndex(0, new Stack<>()));

        assertEquals(blockConnectedBottom, blockConnectedBottom.getBlockAtIndex(0, new Stack<>()));
        assertEquals(blockConnectedTopBottom, blockConnectedBottom.getBlockAtIndex(1, new Stack<>()));
        assertEquals(blockConnectedTop, blockConnectedBottom.getBlockAtIndex(2, new Stack<>()));

        assertEquals(blockConnectedTopBottom, blockConnectedTopBottom.getBlockAtIndex(0, new Stack<>()));
        assertEquals(blockConnectedTop, blockConnectedTopBottom.getBlockAtIndex(1, new Stack<>()));
    }

    @Test
    void getIndexOfBlock_invalidBlock() {
        assertEquals(-1, blockNotConnected.getIndexOfBlock(blockConnectedTop, new Stack<>()));
        assertEquals(1, blockConnectedBottom.getIndexOfBlock(blockNotConnected, new Stack<>()));
        assertEquals(0, blockConnectedTopBottom.getIndexOfBlock(blockNotConnected, new Stack<>()));
        assertEquals(-1, blockConnectedTop.getIndexOfBlock(blockNotConnected, new Stack<>()));
        assertEquals(-1, blockConnectedTop.getIndexOfBlock(blockConnectedTopBottom, new Stack<>()));
    }

    @Test
    void getIndexOfBlock() {
        assertEquals(0, blockConnectedTop.getIndexOfBlock(blockConnectedTop, new Stack<>()));

        assertEquals(0, blockNotConnected.getIndexOfBlock(blockNotConnected, new Stack<>()));

        assertEquals(0, blockConnectedBottom.getIndexOfBlock(blockConnectedBottom, new Stack<>()));
        assertEquals(1, blockConnectedBottom.getIndexOfBlock(blockConnectedTopBottom, new Stack<>()));
        assertEquals(2, blockConnectedBottom.getIndexOfBlock(blockConnectedTop, new Stack<>()));

        assertEquals(0, blockConnectedTopBottom.getIndexOfBlock(blockConnectedTopBottom, new Stack<>()));
        assertEquals(1, blockConnectedTopBottom.getIndexOfBlock(blockConnectedTop, new Stack<>()));
    }

    @Test
    void terminate() {
        assertFalse(blockConnectedTop.isTerminated());
        assertFalse(blockConnectedBottom.isTerminated());
        assertFalse(blockNotConnected.isTerminated());
        assertFalse(blockConnectedTopBottom.isTerminated());

        blockConnectedTopBottom.terminate();
        blockNotConnected.terminate();

        assertFalse(blockConnectedTop.isTerminated());
        assertFalse(blockConnectedBottom.isTerminated());
        assertTrue(blockNotConnected.isTerminated());
        assertTrue(blockConnectedTopBottom.isTerminated());

        blockConnectedBottom.terminate();

        assertFalse(blockConnectedTop.isTerminated());
        assertTrue(blockConnectedBottom.isTerminated());
        assertTrue(blockNotConnected.isTerminated());
        assertTrue(blockConnectedTopBottom.isTerminated());
    }

    @Test
    void isIllegalExtraStartingBlock() {
        assertTrue(blockConnectedTop.isIllegalExtraStartingBlock());
        assertTrue(blockConnectedBottom.isIllegalExtraStartingBlock());
        assertTrue(blockNotConnected.isIllegalExtraStartingBlock());
        assertTrue(blockConnectedTopBottom.isIllegalExtraStartingBlock());
    }
}