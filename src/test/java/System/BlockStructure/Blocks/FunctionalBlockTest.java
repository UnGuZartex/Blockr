package System.BlockStructure.Blocks;

import System.BlockStructure.Blocks.Factory.MoveForwardBlockFactory;
import System.BlockStructure.Blocks.Factory.TurnLeftBlockFactory;
import System.BlockStructure.Blocks.Factory.TurnRightBlockFactory;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalBlockTest {

    FunctionalBlock turnRight, turnLeft, moveForward;

    @BeforeEach
    void setUp() {
        TurnLeftBlockFactory turnLeftBlockFactory = new TurnLeftBlockFactory();
        turnLeft = turnLeftBlockFactory.createBlock();
        TurnRightBlockFactory turnRightBlockFactory = new TurnRightBlockFactory();
        turnRight = turnRightBlockFactory.createBlock();
        MoveForwardBlockFactory moveForwardBlockFactory = new MoveForwardBlockFactory();
        moveForward = moveForwardBlockFactory.createBlock();

        ConnectionHandler handler = new ConnectionHandler();
        handler.connect(turnRight, turnLeft.getSubConnectorAt(0));
    }

    @AfterEach
    void tearDown() {
        turnRight = null;
        turnLeft = null;
        moveForward = null;
    }

    @Test
    void getNbSubConnectors() {
        assertEquals(1, turnLeft.getNbSubConnectors());
        assertEquals(1, turnRight.getNbSubConnectors());
        assertEquals(1, moveForward.getNbSubConnectors());
    }

    @Test
    void hasAlreadyRan() {
        assertFalse(turnLeft.hasAlreadyRan());
        assertFalse(turnRight.hasAlreadyRan());
        assertFalse(moveForward.hasAlreadyRan());
    }

    @Test
    void setAlreadyRan() {
        assertFalse(turnLeft.hasAlreadyRan());
        turnLeft.setAlreadyRan(true); // false -> true
        assertTrue(turnLeft.hasAlreadyRan());
        turnLeft.setAlreadyRan(true); // true -> true
        assertTrue(turnLeft.hasAlreadyRan());
        turnLeft.setAlreadyRan(false); // true -> false
        assertFalse(turnLeft.hasAlreadyRan());
        turnLeft.setAlreadyRan(false); // false -> false
        assertFalse(turnLeft.hasAlreadyRan());
    }

    @Test
    void getSubConnectorWithID() {
        assertEquals(turnLeft.getSubConnectorAt(0), turnLeft.getSubConnectorWithID("SUB_1"));
        assertThrows(IllegalStateException.class,  () -> {turnLeft.getSubConnectorWithID("INVALID");});
    }

    @Test
    void reset() {
        moveForward.setAlreadyRan(true);
        turnLeft.setAlreadyRan(true);
        turnRight.setAlreadyRan(true);
        assertTrue(moveForward.hasAlreadyRan());
        assertTrue(turnLeft.hasAlreadyRan());
        assertTrue(turnRight.hasAlreadyRan());
        moveForward.reset();
        turnLeft.reset();
        assertFalse(moveForward.hasAlreadyRan());
        assertFalse(turnLeft.hasAlreadyRan());
        assertFalse(turnRight.hasAlreadyRan());
    }

    @Test
    void hasProperConnections() {
        assertTrue(turnRight.hasProperConnections());
        assertTrue(turnLeft.hasProperConnections());
        assertTrue(moveForward.hasProperConnections());
    }

    @Test
    void hasNext() {
        assertFalse(turnRight.hasNext());
        assertTrue(turnLeft.hasNext());
        assertFalse(moveForward.hasNext());
    }

    @Test
    void getNext() {
        assertNull(turnRight.getNext());
        assertNull(moveForward.getNext());
        assertEquals(turnLeft.getNext(), turnRight);
    }

    @Test
    void returnToClosestCavity() {
        assertEquals(moveForward.getNextIfNone(), moveForward);
        assertEquals(turnLeft.getNextIfNone(), turnLeft);
        assertEquals(turnRight.getNextIfNone(), turnLeft);
    }
}