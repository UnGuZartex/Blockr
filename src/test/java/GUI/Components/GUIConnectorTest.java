package GUI.Components;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIFunctionalBlock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GUIConnectorTest {

    GUIBlock block1, block2, block3, block4;
    GUIConnector connector1, connector2, connector3, connector4;
    String id1, id2, id3, id4;
    int x1, y1, x2, y2, x3, y3, x4, y4;
    Random random;
    final static int MAX_X = 10, MAX_Y = 10;
    Color color1, color2, color3, color4;

    @BeforeEach
    void setUp() {
        block1 = new GUIFunctionalBlock( "id1",3,3);
        block2 =new GUIFunctionalBlock("id2",3,3);
        block3 =new GUIFunctionalBlock("id3",3,3);
        block4 = new GUIFunctionalBlock("id4",3,3);

        id1 = "1";
        id2 = "2";
        id3 = "3";
        id4 = "4";

        random = new Random();
        x1 = random.nextInt(MAX_X) + 1;
        x2 = random.nextInt(MAX_X) + 1;
        x3 = random.nextInt(MAX_X) + 1;
        x4 = random.nextInt(MAX_X) + 1;
        y1 = random.nextInt(MAX_Y) + 1;
        y2 = random.nextInt(MAX_Y) + 1;
        y3 = random.nextInt(MAX_Y) + 1;
        y4 = random.nextInt(MAX_Y) + 1;

        color1 = Color.RED;
        color2 = Color.GREEN;
        color3 = Color.BLUE;
        color4 = Color.YELLOW;

        connector1 = new GUIConnector(block1, x1, y1, color1);
        connector2 = new GUIConnector(block2, x2, y2, color2);
        connector1.connect(connector2);

        connector3 = new GUIConnector(block3, x3, y3, color3);
        connector4 = new GUIConnector(block4, x4, y4, color4);

    }

    @AfterEach
    void tearDown() {
        block1 = null;
        block2 = null;
        block3 = null;
        block4 = null;

        random = null;

        color1 = null;
        color2 = null;
        color3 = null;
        color4 = null;

        connector1 = null;
        connector2 = null;
        connector3 = null;
        connector4 = null;
    }

    @Test
    void guiConnector_invalidParentBlock() {
        assertThrows(IllegalArgumentException.class, () -> new GUIConnector(null, x1, y1, color1));
    }

    @Test
    void isValidParentBlock() {
        assertTrue(GUIConnector.isValidParentBlock(block1));
        assertTrue(GUIConnector.isValidParentBlock(block2));
        assertTrue(GUIConnector.isValidParentBlock(block3));
        assertTrue(GUIConnector.isValidParentBlock(block4));
        assertFalse(GUIConnector.isValidParentBlock(null));
    }

    @Test
    void getCollisionCircle() {
        assertEquals(x1, connector1.getCollisionCircle().getX());
        assertEquals(y1, connector1.getCollisionCircle().getY());
        assertEquals(color1, connector1.getCollisionCircle().getColor());

        assertEquals(x1, connector1.getCollisionCircle().getX());
        assertEquals(y1, connector1.getCollisionCircle().getY());
        assertEquals(color1, connector1.getCollisionCircle().getColor());

        assertEquals(x3, connector3.getCollisionCircle().getX());
        assertEquals(y3, connector3.getCollisionCircle().getY());
        assertEquals(color3, connector3.getCollisionCircle().getColor());

        assertEquals(x4, connector4.getCollisionCircle().getX());
        assertEquals(y4, connector4.getCollisionCircle().getY());
        assertEquals(color4, connector4.getCollisionCircle().getColor());
    }

    @Test
    void getConnectedConnector() {
        assertEquals(connector2, connector1.getConnectedConnector());
        assertEquals(connector1, connector2.getConnectedConnector());
        assertNull(connector3.getConnectedConnector());
        assertNull(connector4.getConnectedConnector());
    }

//    @Test TODO test
//    void getPosition() {
//        assertEquals(x1, block1.getPosition().getX());
//        assertEquals(y1, block1.getPosition().getY());
//    }

    @Test
    void isConnected() {
        assertTrue(connector1.isConnected());
        assertTrue(connector2.isConnected());
        assertFalse(connector3.isConnected());
        assertFalse(connector4.isConnected());
    }

    @Test
    void disconnect() {
        assertTrue(connector1.isConnected());
        assertTrue(connector2.isConnected());
        connector1.disconnect();
        assertFalse(connector1.isConnected());
        assertFalse(connector2.isConnected());

        assertFalse(connector3.isConnected());
        connector3.disconnect();
        assertFalse(connector3.isConnected());
    }

    @Test
    void connect_NullArgument() {
        assertThrows(IllegalArgumentException.class, () -> { connector3.connect(null); });
    }

    @Test
    void connect_AlreadyConnected() {
        assertThrows(IllegalArgumentException.class, () -> { connector1.connect(connector3); });
    }

    @Test
    void connect_OtherAlreadyConnected() {
        assertThrows(IllegalArgumentException.class, () -> { connector3.connect(connector1); });
    }

    @Test
    void connect_Success() {
        assertFalse(connector3.isConnected());
        assertFalse(connector4.isConnected());
        assertNull(connector3.getConnectedGUIBlock());
        assertNull(connector4.getConnectedGUIBlock());
        connector3.connect(connector4);
        assertTrue(connector3.isConnected());
        assertTrue(connector4.isConnected());
        assertEquals(block4, connector3.getConnectedGUIBlock());
        assertEquals(block3, connector4.getConnectedGUIBlock());
    }

    @Test
    void getParentBlock() {
        assertEquals(block1, connector1.getParentBlock());
        assertEquals(block2, connector2.getParentBlock());
        assertEquals(block3, connector3.getParentBlock());
        assertEquals(block4, connector4.getParentBlock());
    }

    @Test
    void getConnectedGUIBlock() {
        assertEquals(block2, connector1.getConnectedGUIBlock());
        assertEquals(block1, connector2.getConnectedGUIBlock());
        assertNull(connector3.getConnectedGUIBlock());
        assertNull(connector4.getConnectedGUIBlock());
    }
}