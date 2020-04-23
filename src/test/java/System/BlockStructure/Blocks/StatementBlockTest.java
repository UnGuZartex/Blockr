package System.BlockStructure.Blocks;

import GameWorld.Level;
import GameWorldUtility.LevelInitializer;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatementBlockTest {

    /*StatementBlock block1, block2;
    CavityBlock cavoc;

    @BeforeEach
    void setUp() {
        LevelInitializer init = new LevelInitializer();
        Level level = (Level) init.createNewGameWorld();

        block1 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate(),level));
        block2 = new StatementBlock(new PredicateFunctionality(new WallInFrontPredicate(),level));

        cavoc = new IfBlock(new CavityFunctionality(level));
        ConnectionHandler handler = new ConnectionHandler();
        handler.connect(block1, cavoc.getConditionalSubConnector());
    }

    @AfterEach
    void tearDown() {
        block1 = null;
        block2 =  null;
        cavoc = null;
    }

    @Test
    void hasNext() {
        assertFalse(block1.hasNext());
        assertFalse(block2.hasNext());
    }

    @Test
    void getNext() {
        assertNull(block1.getNext());
        assertNull(block2.getNext());
    }

    @Test
    void getMainConnector() {
        assertEquals(block1.getMainConnector(), block1.mainConnector);
        assertEquals(block2.getMainConnector(), block2.mainConnector);
    }

//    @Test
//    void returnToClosestCavity() {
//        assertNull(block1.getNextIfNone());
//        assertNull(block2.getNextIfNone());
//    }

    @Test
    void getSubConnectorAt() {
        assertThrows(IndexOutOfBoundsException.class, () -> { block1.getSubConnectorAt(0); });
        assertThrows(IndexOutOfBoundsException.class, () -> { block2.getSubConnectorAt(0); });
    }

    @Test
    void getNbSubConnectors() {
        assertEquals(0, block1.getNbSubConnectors());
        assertEquals(0, block2.getNbSubConnectors());
    }

//    @Test
//    void setAlreadyRan() {
//        assertFalse(block1.hasAlreadyRan());
//        block1.setAlreadyRan(true); // false -> true
//        assertTrue(block1.hasAlreadyRan());
//        block1.setAlreadyRan(true); // true -> true
//        assertTrue(block1.hasAlreadyRan());
//        block1.setAlreadyRan(false); // true -> false
//        assertFalse(block1.hasAlreadyRan());
//        block1.setAlreadyRan(false); // false -> false
//        assertFalse(block1.hasAlreadyRan());
//    }

//    @Test
//    void reset() {
//        block1.setAlreadyRan(true);
//        assertTrue(block1.hasAlreadyRan());
//        block1.reset();
//        assertFalse(block1.hasAlreadyRan());
//    }

    @Test
    void hasProperConnections() {
        assertTrue(block1.hasProperConnections());
        assertFalse(block2.hasProperConnections());
    }*/
}