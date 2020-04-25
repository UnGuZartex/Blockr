package System.BlockStructure.Blocks;

import GameWorld.Level;
import GameWorldUtility.LevelInitializer;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.NotFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationalBlockTest {

    /*OperationalBlock not1, not2, not3, not4;
    StatementBlock wallInFront1, wallInFront2;
    CavityBlock cavoc3, cavoc2;

    @BeforeEach
    void setUp() {

        LevelInitializer init = new LevelInitializer();
        Level level = (Level) init.createNewGameWorld();
        not1 = new NotBlock(new NotFunctionality(level));
        not2 = new NotBlock(new NotFunctionality(level));
        not3 = new NotBlock(new NotFunctionality(level));
        not4 = new NotBlock(new NotFunctionality(level));

        wallInFront1 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate(),level));
        wallInFront2 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate(),level));

        cavoc3 = new IfBlock(new CavityFunctionality(level));
        cavoc2 = new IfBlock(new CavityFunctionality(level));

        ConnectionHandler handler = new ConnectionHandler();
        handler.connect(wallInFront1, not1.getSubConnectorAt(0));
        handler.connect(wallInFront2, not2.getSubConnectorAt(0));
        handler.connect(not2, cavoc2.getConditionalSubConnector());
        handler.connect(not3, cavoc3.getConditionalSubConnector());

        
    }

    @AfterEach
    void tearDown() {
        not1 = null;
        not2 = null;
        not3 = null;
        not4 = null;

        wallInFront1 = null;
        wallInFront2 = null;

        cavoc3 = null;
        cavoc2 = null;
    }

    @Test
    void hasNext() {
        assertTrue(not1.hasNext());
        assertTrue(not2.hasNext());
        assertFalse(not3.hasNext());
        assertFalse(not4.hasNext());
    }

    @Test
    void getNext() {
        assertEquals(not1.getNext(), wallInFront1);
        assertEquals(not2.getNext(), wallInFront2);
        assertNull(not3.getNext());
        assertNull(not4.getNext());
    }

    @Test
    void getMainConnector() {
        assertEquals(not1.getMainConnector(), not1.mainConnector);
        assertEquals(not2.getMainConnector(), not2.mainConnector);
        assertEquals(not3.getMainConnector(), not3.mainConnector);
        assertEquals(not4.getMainConnector(), not4.mainConnector);
    }

//    @Test
//    void returnToClosestCavity() {
//        assertNull(not1.getNextIfNone());
//        assertNull(not2.getNextIfNone());
//        assertNull(not3.getNextIfNone());
//        assertNull(not4.getNextIfNone());
//    }

    @Test
    void hasProperConnections() {
        assertFalse(not1.hasProperConnections()); // Not connected to main
        assertTrue(not2.hasProperConnections());
        assertFalse(not3.hasProperConnections()); // Not connected to sub
        assertFalse(not4.hasProperConnections()); // Not connected to main and sub
    }

    @Test
    void getNbSubConnectors() {
        assertEquals(1, not1.getNbSubConnectors());
        assertEquals(1, not2.getNbSubConnectors());
        assertEquals(1, not3.getNbSubConnectors());
        assertEquals(1, not4.getNbSubConnectors());
    }

//    @Test
//    void setAlreadyRan() {
//        assertFalse(not1.hasAlreadyRan());
//        not1.setAlreadyRan(true); // false -> true
//        assertTrue(not1.hasAlreadyRan());
//        not1.setAlreadyRan(true); // true -> true
//        assertTrue(not1.hasAlreadyRan());
//        not1.setAlreadyRan(false); // true -> false
//        assertFalse(not1.hasAlreadyRan());
//        not1.setAlreadyRan(false); // false -> false
//        assertFalse(not1.hasAlreadyRan());
//    }
//
//    @Test
//    void reset() {
//        not1.setAlreadyRan(true);
//        assertTrue(not1.hasAlreadyRan());
//        not1.reset();
//        assertFalse(not1.hasAlreadyRan());
//    }*/
}