package GUI.Blocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GUICavityBlockTest {

    GUIConditionalBlock conditional2, conditional3;
    GUICavityBlock cavity1, cavity2, cavity3, cavity4, cavity5;
    GUIFunctionalBlock in1, under1, in2, under3, under4, in5;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    Random random;

    @BeforeEach
    void setUp() {
        random = new Random();

        cavity1 = new GUICavityBlock("Cavity 1",random.nextInt(MAX_X + 1 - MIN_X) + MIN_X, random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y);
        cavity2 = new GUICavityBlock("Cavity 2",random.nextInt(MAX_X + 1 - MIN_X) + MIN_X, random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y);
        cavity3 = new GUICavityBlock("Cavity 3",random.nextInt(MAX_X + 1 - MIN_X) + MIN_X, random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y);
        cavity4 = new GUICavityBlock("Cavity 4",random.nextInt(MAX_X + 1 - MIN_X) + MIN_X, random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y);
        cavity5 = new GUICavityBlock("Cavity 5",random.nextInt(MAX_X + 1 - MIN_X) + MIN_X, random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y);

        conditional2 = new GUIConditionalBlock("Conditional 2", cavity2.getX() + cavity2.getWidth(), cavity2.getY());
        assertTrue(conditional2.canPotentiallyConnectWith(cavity2));
        conditional2.mainConnector.connect(cavity2.conditionalConnector);
        conditional3 = new GUIConditionalBlock("Conditional 3", cavity3.getX() + cavity3.getWidth(), cavity3.getY());
        assertTrue(conditional3.canPotentiallyConnectWith(cavity3));
        conditional3.mainConnector.connect(cavity3.conditionalConnector);

        in1 = new GUIFunctionalBlock("In 1", cavity1.getX() + GUICavityBlock.DEFAULT_CAVITY_WIDTH, cavity1.getY() + GUICavityBlock.DEFAULT_CAVITY_UP_HEIGHT);
        assertTrue(in1.canPotentiallyConnectWith(cavity1));
        in1.mainConnector.connect(cavity1.cavityConnector);
        in2 = new GUIFunctionalBlock("In 2", cavity2.getX() + GUICavityBlock.DEFAULT_CAVITY_WIDTH, cavity2.getY() + GUICavityBlock.DEFAULT_CAVITY_UP_HEIGHT);
        assertTrue(in2.canPotentiallyConnectWith(cavity2));
        in2.mainConnector.connect(cavity2.cavityConnector);
        in5 = new GUIFunctionalBlock("In 5", cavity5.getX() + GUICavityBlock.DEFAULT_CAVITY_WIDTH, cavity5.getY() + GUICavityBlock.DEFAULT_CAVITY_UP_HEIGHT);
        assertTrue(in5.canPotentiallyConnectWith(cavity5));
        in5.mainConnector.connect(cavity5.cavityConnector);

        under1 = new GUIFunctionalBlock("Under 1", cavity1.getX(), cavity1.getY()  + cavity1.getTotalHeight());
        assertTrue(under1.canPotentiallyConnectWith(cavity1));
        under1.mainConnector.connect(cavity1.lowerSubConnector);
        under3 = new GUIFunctionalBlock("Under 3", cavity3.getX(), cavity3.getY()  + cavity3.getTotalHeight());
        assertTrue(under3.canPotentiallyConnectWith(cavity3));
        under3.mainConnector.connect(cavity3.lowerSubConnector);
        under4 = new GUIFunctionalBlock("Under 4", cavity4.getX(), cavity4.getY()  + cavity4.getTotalHeight());
        assertTrue(under4.canPotentiallyConnectWith(cavity4));
        under4.mainConnector.connect(cavity4.lowerSubConnector);
    }

    @AfterEach
    void tearDown() {
        random = null;

        cavity1 = null;
        cavity2 = null;
        cavity3 = null;
        cavity4 = null;
        cavity5 = null;

        conditional2 = null;
        conditional3 = null;

        in1 = null;
        in2 = null;
        in5 = null;

        under1 = null;
        under3 = null;
        under4 = null;
    }

    @Test
    void setColor_noConditional() {
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, cavity1.getColor());
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, in1.getColor());
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, under1.getColor());
        cavity1.setColor(Color.pink);
        assertEquals(Color.pink, cavity1.getColor());
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, in1.getColor());
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, under1.getColor());
    }

    @Test
    void setColor_conditional() {
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, cavity2.getColor());
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, in2.getColor());
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, conditional2.getColor());
        cavity2.setColor(Color.pink);
        assertEquals(Color.pink, cavity2.getColor());
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, in2.getColor());
        assertEquals(Color.pink, conditional2.getColor());
    }

    @Test
    void changeHeight_invalidHeight() {
        assertThrows(IllegalArgumentException.class, () -> cavity1.changeHeight(-5000000, in1));
    }

    @Test
    void changeHeight_differentPreviousBlock() {
        int h = cavity1.getTotalHeight();
        cavity1.changeHeight(50, in2);
        assertEquals(h, cavity1.getTotalHeight());
    }

    @Test
    void changeHeight_connectedMain() {
        cavity1.setPosition(cavity3.getX() + GUICavityBlock.DEFAULT_CAVITY_WIDTH, cavity3.getY() + GUICavityBlock.DEFAULT_CAVITY_UP_HEIGHT);
        assertTrue(cavity1.canPotentiallyConnectWith(cavity3));
        cavity1.mainConnector.connect(cavity3.cavityConnector);

        int h = cavity3.getTotalHeight();
        cavity1.changeHeight(50, in1);
        assertEquals(h + 50, cavity3.getTotalHeight());
    }

    @Test
    void changeHeight() {
        int h = cavity1.getTotalHeight();
        cavity1.changeHeight(50, in1);
        assertEquals(h + 50, cavity1.getTotalHeight());
    }

    @Test
    void getTotalHeight() {
        assertEquals(GUIFunctionalBlock.DEFAULT_HEIGHT*2 + GUICavityBlock.DEFAULT_CAVITY_UP_HEIGHT + GUICavityBlock.DEFAULT_CAVITY_DOWN_HEIGHT, cavity1.getTotalHeight());
        assertEquals(GUIFunctionalBlock.DEFAULT_HEIGHT + GUICavityBlock.DEFAULT_CAVITY_UP_HEIGHT + GUICavityBlock.DEFAULT_CAVITY_DOWN_HEIGHT, cavity2.getTotalHeight());
        assertEquals(GUIFunctionalBlock.DEFAULT_HEIGHT + GUICavityBlock.DEFAULT_CAVITY_UP_HEIGHT + GUICavityBlock.DEFAULT_CAVITY_DOWN_HEIGHT, cavity3.getTotalHeight());
    }

    @Test
    void setShapes() {
        cavity1.setShapes();
        assertEquals(GUIBlock.DEFAULT_BLOCK_COLOR, cavity1.getColor());
        assertEquals(GUIBlock.DEFAULT_MAIN_CONNECTOR_COLOR, cavity1.mainConnector.getCollisionCircle().getColor());
        assertEquals(GUIBlock.DEFAULT_SUB_CONNECTOR_COLOR, cavity1.conditionalConnector.getCollisionCircle().getColor());
        assertEquals(GUIBlock.DEFAULT_SUB_CONNECTOR_COLOR, cavity1.cavityConnector.getCollisionCircle().getColor());
        assertEquals(GUIBlock.DEFAULT_SUB_CONNECTOR_COLOR, cavity1.lowerSubConnector.getCollisionCircle().getColor());
    }

    @Test
    void testClone() {
        GUIBlock clone = cavity1.clone();
        assertNotEquals(clone, cavity1);
        assertEquals(clone.getClass(), cavity1.getClass());
        assertEquals(clone.getX(), cavity1.getX());
        assertEquals(clone.getY(), cavity1.getY());
        assertEquals(clone.getName(), cavity1.getName());
    }
}