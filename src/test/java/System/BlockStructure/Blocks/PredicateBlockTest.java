package System.BlockStructure.Blocks;

import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class PredicateBlockTest {

    Block block;
    PredicateBlock predicateConnected, predicateNotConnected;
    CavityBlock dummy;
    GameWorldType type;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();

        predicateConnected = new PredicateBlock(new PredicateFunctionality(type.getAllPredicates().get(0)));
        predicateNotConnected = new PredicateBlock(new PredicateFunctionality(type.getAllPredicates().get(0)));

        dummy = new IfBlock();
        ConnectionHandler handler = new ConnectionHandler();
        handler.connect(predicateConnected, dummy.getConditionalSubConnector());
    }

    @AfterEach
    void tearDown() {
        predicateConnected = null;
        predicateNotConnected =  null;
        dummy = null;
        type = null;
    }

    @Test
    void hasNext() {
        assertFalse(predicateConnected.hasNext());
        assertFalse(predicateNotConnected.hasNext());
    }

    @Test
    void getSubConnectorAt() {
        assertThrows(IndexOutOfBoundsException.class, () -> predicateConnected.getSubConnectorAt(0));
        assertThrows(IndexOutOfBoundsException.class, () -> predicateNotConnected.getSubConnectorAt(0));
    }

    @Test
    void getNbSubConnectors() {
        assertEquals(0, predicateConnected.getNbSubConnectors());
        assertEquals(0, predicateNotConnected.getNbSubConnectors());
    }

    @Test
    void hasProperConnections() {
        assertTrue(predicateConnected.hasProperConnections());
        assertFalse(predicateNotConnected.hasProperConnections());
    }

    @Test
    void cloneTest() {
        Block block = predicateConnected.clone();
        assertNotEquals(block, predicateConnected);
        assertEquals(block.getFunctionality(), predicateConnected.getFunctionality());
        assertEquals(block.getClass(), predicateConnected.getClass());
        assertFalse(block.getMainConnector().isConnected());
    }

    @Test
    void getBlockAtIndex() {
        assertThrows(IllegalStateException.class, () -> predicateConnected.getBlockAtIndex(1, new Stack<>()));
        assertThrows(IllegalStateException.class, () -> predicateNotConnected.getBlockAtIndex(0, new Stack<>()));
        assertThrows(IllegalStateException.class, () -> predicateConnected.getBlockAtIndex(-1, new Stack<>()));
    }

    @Test
    void getIndexOfBlock() {
        assertThrows(IllegalStateException.class, () -> predicateConnected.getIndexOfBlock(block, new Stack<>()));
        assertThrows(IllegalStateException.class, () -> predicateNotConnected.getIndexOfBlock(block, new Stack<>()));
    }

    @Test
    void pushNextBlocks() {
        assertThrows(IllegalStateException.class, () -> predicateConnected.pushNextBlocks(new Stack<>()));
        assertThrows(IllegalStateException.class, () -> predicateNotConnected.pushNextBlocks(new Stack<>()));
    }
}