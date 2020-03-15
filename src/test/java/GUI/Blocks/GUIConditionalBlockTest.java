package GUI.Blocks;

import GUI.Blocks.Factories.MoveForwardGUIFactory;
import GUI.Blocks.Factories.WallInFrontGUIFactory;
import GUI.CollisionShapes.CollisionRectangle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GUIConditionalBlockTest {

    GUIBlock block;
    GUIConditionalBlock cond1;
    int x1, y1;
    Random random;
    final static int MAX_X = 10, MAX_Y = 10;
    String id1;


    @BeforeEach
    void setUp() {
        random = new Random();
        x1 = random.nextInt(MAX_X) + 1;
        y1 = random.nextInt(MAX_Y) + 1;

        id1 = "Cond1";

        WallInFrontGUIFactory factory = new WallInFrontGUIFactory();
        cond1 = factory.createBlock(id1, x1, y1);
    }

    @AfterEach
    void tearDown() {
        random = null;
        cond1 = null;
        id1 = null;
    }

    @Test
    void getX() {
        assertEquals(x1, cond1.getX());
    }

    @Test
    void getY() {
        assertEquals(y1, cond1.getY());
    }

    @Test
    void getId() {
        assertEquals(id1, cond1.getId());
    }

    @Test
    void getHeight() {
        assertEquals(GUIConditionalBlock.DEFAULT_HEIGHT, cond1.getHeight());
    }

    @Test
    void getWidth() {
        assertEquals(GUIConditionalBlock.DEFAULT_WIDTH, cond1.getWidth());
    }

    @Test
    void setColor() {
        for (CollisionRectangle rect : cond1.blockRectangles) {
            assertEquals(GUIConditionalBlock.DEFAULT_COLOR, rect.getColor());
        }
        cond1.setColor(Color.black);
        for (CollisionRectangle rect : cond1.blockRectangles) {
            assertEquals(Color.black, rect.getColor());
        }
    }

    @Test
    void setPosition() {
        cond1.setPosition(x1 + 3, y1 - 5);
        assertEquals(x1 + 3, cond1.getX());
        assertEquals(y1 - 5, cond1.getY());
    }

    @Test
    void changeHeight() {
        cond1.changeHeight(5, block);
        assertEquals(GUIConditionalBlock.DEFAULT_HEIGHT, cond1.getHeight());
    }

    @Test
    void setShapes() {
        cond1.setShapes();
        assertEquals(GUIConditionalBlock.DEFAULT_HEIGHT, cond1.getHeight());
        assertEquals(GUIConditionalBlock.DEFAULT_WIDTH, cond1.getWidth());
        assertEquals(0, cond1.subConnectors.size());
        assertEquals(1, cond1.blockRectangles.size());
    }

    @Test
    void translate() {
        cond1.translate(3,-5);
        assertEquals(x1 + 3, cond1.getX());
        assertEquals(y1 - 5, cond1.getY());
    }

    @Test
    void contains() {
        assertTrue(cond1.contains(x1, y1));
        assertTrue(cond1.contains(x1 + GUIConditionalBlock.DEFAULT_WIDTH, y1 + GUIConditionalBlock.DEFAULT_HEIGHT));
        assertTrue(cond1.contains(x1 + GUIConditionalBlock.DEFAULT_WIDTH/2, y1 + GUIConditionalBlock.DEFAULT_HEIGHT/2));
        assertFalse(cond1.contains(x1 + GUIConditionalBlock.DEFAULT_WIDTH, y1 + GUIConditionalBlock.DEFAULT_HEIGHT + 10));
        assertFalse(cond1.contains(x1 + GUIConditionalBlock.DEFAULT_WIDTH + 10,y1 +  GUIConditionalBlock.DEFAULT_HEIGHT));
    }

    @Test
    void isInside() {
        assertTrue(cond1.isInside(new CollisionRectangle(-1,-1, (MAX_X + GUIConditionalBlock.DEFAULT_WIDTH) * 2 , (MAX_Y + GUIConditionalBlock.DEFAULT_HEIGHT) * 2, Color.DARK_GRAY)));
        assertFalse(cond1.isInside(new CollisionRectangle(-5,-5, 3, 3, Color.DARK_GRAY)));
        assertFalse(cond1.isInside(new CollisionRectangle(x1 + 50,0, MAX_X, MAX_Y, Color.DARK_GRAY)));
    }

    @Test
    void intersectsWithConnector() {
        MoveForwardGUIFactory f = new MoveForwardGUIFactory();
        block = f.createBlock("id", x1, y1);
        assertFalse(cond1.intersectsWithConnector(block));
    }

    @Test
    void connectWithStaticBlock() {

    }

    @Test
    void getHighest() {
    }

    @Test
    void resetHeight() {
    }

    @Test
    void disconnectHeight() {
    }

    @Test
    void disconnectMainConnector() {
    }

    @Test
    void getConnectedBlocks() {
    }



}