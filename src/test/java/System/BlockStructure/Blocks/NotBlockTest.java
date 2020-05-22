package System.BlockStructure.Blocks;

import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Functionality.NotFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import System.Logic.ProgramArea.ExecutionStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotBlockTest {

    ExecutionStack stack;
    GameWorldType type;
    ConnectionHandler handler;
    NotBlock notFullConnected, notOnlyPredicate, notOnlyCavity, notNoConnections;
    PredicateBlock predicateFull, predicateHalve;
    CavityBlock cavityFull, cavityHalve;
    Block block;

    @BeforeEach
    void setUp() {
        stack = new ExecutionStack();
        type = new LevelInitializer();

        notFullConnected = new NotBlock();
        notOnlyPredicate = new NotBlock();
        notOnlyCavity = new NotBlock();
        notNoConnections = new NotBlock();

        predicateFull = new PredicateBlock(new PredicateFunctionality(type.getAllPredicates().get(0)));
        predicateHalve = new PredicateBlock(new PredicateFunctionality(type.getAllPredicates().get(0)));

        cavityFull = new WhileBlock();
        cavityHalve = new IfBlock();

        handler = new ConnectionHandler();
        handler.connect(predicateFull, notFullConnected.getSubConnectorAt(0));
        handler.connect(notFullConnected, cavityFull.getConditionalSubConnector());
        handler.connect(predicateHalve, notOnlyPredicate.getSubConnectorAt(0));
        handler.connect(notOnlyCavity, cavityHalve.getConditionalSubConnector());
    }

    @AfterEach
    void tearDown() {
        stack = null;
        type = null;
        handler = null;

        notFullConnected = null;
        notOnlyPredicate = null;
        notOnlyCavity = null;
        notNoConnections = null;
        predicateFull = null;
        predicateHalve = null;
        cavityFull = null;
        cavityHalve = null;
    }

    @Test
    void hasNext() {
        assertTrue(notFullConnected.hasNext());
        assertTrue(notOnlyPredicate.hasNext());
        assertFalse(notOnlyCavity.hasNext());
        assertFalse(notNoConnections.hasNext());
    }

    @Test
    void hasProperConnections() {
        assertTrue(notFullConnected.hasProperConnections());
        assertFalse(notOnlyPredicate.hasProperConnections());
        assertFalse(notOnlyCavity.hasProperConnections());
        assertFalse(notNoConnections.hasProperConnections());
    }

    @Test
    void getNbSubConnectors() {
        assertEquals(1, notFullConnected.getNbSubConnectors());
        assertEquals(1, notOnlyPredicate.getNbSubConnectors());
        assertEquals(1, notOnlyCavity.getNbSubConnectors());
        assertEquals(1, notNoConnections.getNbSubConnectors());
    }

    @Test
    void cloneTest() {
        Block block = notFullConnected.clone();
        assertNotEquals(block, notFullConnected);
        assertNotEquals(block.getFunctionality(), notFullConnected.getFunctionality());
        assertEquals(block.getClass(), notFullConnected.getClass());
        assertTrue(block.getFunctionality() instanceof NotFunctionality);
        assertFalse(block.getSubConnectorAt(0).isConnected());
        assertFalse(block.getMainConnector().isConnected());
    }

    @Test
    void getBlockAtIndex() {
        assertThrows(IllegalStateException.class, () -> notFullConnected.getBlockAtIndex(1, stack));
        assertThrows(IllegalStateException.class, () -> notOnlyPredicate.getBlockAtIndex(0, stack));
        assertThrows(IllegalStateException.class, () -> notOnlyCavity.getBlockAtIndex(-1, stack));
    }

    @Test
    void getIndexOfBlock() {
        assertThrows(IllegalStateException.class, () -> notOnlyCavity.getIndexOfBlock(block, stack));
        assertThrows(IllegalStateException.class, () -> notNoConnections.getIndexOfBlock(block, stack));
    }

    @Test
    void pushNextBlocks() {
        assertThrows(IllegalStateException.class, () -> notNoConnections.pushNextBlocks(stack));
    }
}