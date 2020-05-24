package GUI.Blocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GUIOperatorBlockTest {

    GUIOperatorBlock connectedBlock, notConnectedBlock;
    GUIConditionalBlock conditional;
    Random random;
    int x1, y1, x2, y2;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    String nameConnected, nameNotConnected, nameConditional;

    @BeforeEach
    void setUp() {
        random = new Random();
        x1 = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        y1 = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        x2 = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        y2 = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;

        nameConnected = "Connected operator";
        nameNotConnected = "Not connected operator";
        nameConditional = "Conditional";

        connectedBlock = new GUIOperatorBlock(nameConnected, x1, y1);
        notConnectedBlock = new GUIOperatorBlock(nameNotConnected, x2, y2);
        conditional = new GUIConditionalBlock(nameConditional, 0, 0);

        conditional.setPosition(x1 + GUIOperatorBlock.DEFAULT_WIDTH, y1);
        assertTrue(conditional.canPotentiallyConnectWith(connectedBlock));
        conditional.mainConnector.connect(connectedBlock.subConnectors.get(0));
    }

    @AfterEach
    void tearDown() {
        random = null;
        connectedBlock = null;
        notConnectedBlock = null;
        conditional = null;
    }

    @Test
    void setColor_connected() {
        assertNotEquals(Color.pink, connectedBlock.getColor());
        assertNotEquals(Color.pink, conditional.getColor());
        connectedBlock.setColor(Color.pink);
        assertEquals(Color.pink, connectedBlock.getColor());
        assertEquals(Color.pink, conditional.getColor());
    }

    @Test
    void setColor_notConnected() {
        assertNotEquals(Color.pink, notConnectedBlock.getColor());
        notConnectedBlock.setColor(Color.pink);
        assertEquals(Color.pink, notConnectedBlock.getColor());
    }

    @Test
    void setColor_terminated() {
        connectedBlock.terminate();
        assertTrue(connectedBlock.isTerminated());
        assertEquals(connectedBlock.getColor(), GUIBlock.DEFAULT_BLOCK_COLOR);
        connectedBlock.setColor(Color.pink);
        assertEquals(connectedBlock.getColor(), GUIBlock.DEFAULT_BLOCK_COLOR);
    }

    @Test
    void setShapes() {
        connectedBlock.setShapes();
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, connectedBlock.getColor());
        assertEquals(GUIBlock.DEFAULT_MAIN_CONNECTOR_COLOR, connectedBlock.mainConnector.getCollisionCircle().getColor());
        assertEquals(GUIBlock.DEFAULT_SUB_CONNECTOR_COLOR, connectedBlock.subConnectors.get(0).getCollisionCircle().getColor());
        assertEquals(GUIOperatorBlock.DEFAULT_WIDTH, connectedBlock.getWidth());
        assertEquals(GUIOperatorBlock.DEFAULT_HEIGHT, connectedBlock.getTotalHeight());
    }

    @Test
    void testClone() {
        GUIBlock clone = connectedBlock.clone();
        assertNotEquals(clone, connectedBlock);
        assertEquals(clone.getClass(), connectedBlock.getClass());
        assertEquals(clone.getX(), connectedBlock.getX());
        assertEquals(clone.getY(), connectedBlock.getY());
    }
}