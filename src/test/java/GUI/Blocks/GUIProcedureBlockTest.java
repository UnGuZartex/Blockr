package GUI.Blocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GUIProcedureBlockTest {

    GUIProcedureBlock procedure;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        procedure = new GUIProcedureBlock("Procedure", random.nextInt(MAX_X + 1 - MIN_X) + MIN_X, random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y);
    }

    @AfterEach
    void tearDown() {
        random = null;
        procedure = null;
    }

    @Test
    void testClone() {
        GUIBlock clone1 = procedure.clone();
        assertNotEquals(clone1, procedure);
        assertEquals(clone1.getClass(), procedure.getClass());
        assertEquals(clone1.getX(), procedure.getX());
        assertEquals(clone1.getY(), procedure.getY());
        assertNotEquals(clone1.getName(), procedure.getName());
        assertEquals("Def 1", clone1.getName());

        GUIBlock clone2 = procedure.clone();
        GUIBlock clone3 = procedure.clone();
        GUIBlock clone4 = procedure.clone();
        assertEquals("Def 1", clone1.getName());
        assertEquals("Def 2", clone2.getName());
        assertEquals("Def 3", clone3.getName());
        assertEquals("Def 4", clone4.getName());

        clone2.terminate();
        assertEquals("Def 1", clone1.getName());
        assertEquals("Def 3", clone3.getName());
        assertEquals("Def 4", clone4.getName());

        GUIBlock clone5 = procedure.clone();
        assertEquals("Def 1", clone1.getName());
        assertEquals("Def 2", clone5.getName());
        assertEquals("Def 3", clone3.getName());
        assertEquals("Def 4", clone4.getName());

        GUIBlock clone6 = procedure.clone();
        assertEquals("Def 1", clone1.getName());
        assertEquals("Def 2", clone5.getName());
        assertEquals("Def 3", clone3.getName());
        assertEquals("Def 4", clone4.getName());
        assertEquals("Def 5", clone6.getName());

        clone1.terminate();
        assertEquals("Def 2", clone5.getName());
        assertEquals("Def 3", clone3.getName());
        assertEquals("Def 4", clone4.getName());
        assertEquals("Def 5", clone6.getName());

        clone6.terminate();
        assertEquals("Def 2", clone5.getName());
        assertEquals("Def 3", clone3.getName());
        assertEquals("Def 4", clone4.getName());

        clone3.terminate();
        clone4.terminate();
        clone5.terminate();
    }

    @Test
    void terminate() {
        assertFalse(procedure.isTerminated());
        procedure.terminate();
        assertTrue(procedure.isTerminated());
    }

    @Test
    void subConnectors() {
        assertNull(procedure.conditionalConnector);
        assertNull(procedure.lowerSubConnector);
        assertNotNull(procedure.cavityConnector);
        assertEquals(GUIBlock.DEFAULT_SUB_CONNECTOR_COLOR, procedure.cavityConnector.getCollisionCircle().getColor());
    }
}