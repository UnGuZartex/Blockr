package GUI.Blocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GUICallerBlockTest {

    GUICallerBlock call1, call2;
    Random random;
    int x1, y1, procedureNb1, x2, y2, procedureNb2;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;

    @BeforeEach
    void setUp() {
        random = new Random();
        x1 = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        y1 = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        x2 = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        y2 = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;

        procedureNb1 = 1;
        procedureNb2 = 1;

        call1 = new GUICallerBlock(x1, y1, procedureNb1);
        call2 = new GUICallerBlock(x2, y2, procedureNb2);

        call1.subscribe(call2);
    }

    @AfterEach
    void tearDown() {
        random = null;
        call1 = null;
        call2 = null;
    }

    @Test
    void guiCallerBlock_invalidProcedureNr() {
        assertThrows(IllegalArgumentException.class, () -> new GUICallerBlock(x1, y1, -1));
    }

    @Test
    void isValidProcedureNr() {
        assertTrue(GUICallerBlock.isValidProcedureNr(procedureNb1));
        assertTrue(GUICallerBlock.isValidProcedureNr(procedureNb1));
        assertTrue(GUICallerBlock.isValidProcedureNr(2));
        assertTrue(GUICallerBlock.isValidProcedureNr(114562));
        assertFalse(GUICallerBlock.isValidProcedureNr(0));
        assertFalse(GUICallerBlock.isValidProcedureNr(-1));
        assertFalse(GUICallerBlock.isValidProcedureNr(-2));
    }

    @Test
    void terminate_notSubscribed() {
        assertFalse(call1.isTerminated());
        assertFalse(call2.isTerminated());
        call2.terminate();
        assertFalse(call1.isTerminated());
        assertTrue(call2.isTerminated());
    }

    @Test
    void terminate_subscribed() {
        assertFalse(call1.isTerminated());
        assertFalse(call2.isTerminated());
        call1.terminate();
        assertTrue(call1.isTerminated());
        assertTrue(call2.isTerminated());
    }

    @Test
    void onProcedureDeleted_notSubscribed() {
        assertFalse(call1.isTerminated());
        assertFalse(call2.isTerminated());
        call2.onProcedureDeleted();
        assertFalse(call1.isTerminated());
        assertTrue(call2.isTerminated());
    }

    @Test
    void onProcedureDeleted_subscribed() {
        assertFalse(call1.isTerminated());
        assertFalse(call2.isTerminated());
        call1.onProcedureDeleted();
        assertTrue(call1.isTerminated());
        assertTrue(call2.isTerminated());
    }

    @Test
    void unsubscribe() {
        call1.unsubscribe(call2);
        assertFalse(call1.isTerminated());
        assertFalse(call2.isTerminated());
        call1.terminate();
        assertTrue(call1.isTerminated());
        assertFalse(call2.isTerminated());
    }

    @Test
    void testClone() {
        GUIBlock clone = call1.clone();
        assertNotEquals(clone, call1);
        assertEquals(clone.getClass(), call1.getClass());
        assertEquals(clone.getX(), call1.getX());
        assertEquals(clone.getY(), call1.getY());

        assertFalse(call1.isTerminated());
        assertFalse(clone.isTerminated());
        call1.terminate();
        assertTrue(call1.isTerminated());
        assertTrue(clone.isTerminated());
    }
}