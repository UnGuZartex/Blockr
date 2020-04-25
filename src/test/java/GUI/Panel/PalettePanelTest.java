package GUI.Panel;

import GUI.Blocks.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PalettePanelTest {

    PalettePanel panel;
    int cornerX, cornerY, width, height;
    Random random;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    static final int MIN_WIDTH = 100, MAX_WIDTH = 800, MIN_HEIGHT = 100, MAX_HEIGHT = 800;
    GUIBlock cavity, functional, conditional, operator;
    String cavityName, functionalName, conditionalName, operatorName;

    @BeforeEach
    void setUp() {
        random = new Random();
        cornerX = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        cornerY = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        width = random.nextInt(MAX_WIDTH + 1 - MIN_WIDTH) + MIN_WIDTH;
        height = random.nextInt(MAX_HEIGHT + 1 - MIN_HEIGHT) + MIN_HEIGHT;

        cavityName = "Cavity";
        functionalName = "functional";
        conditionalName = "conditional";
        operatorName = "operator";

        cavity = new GUICavityBlock(cavityName, 0,0);
        functional = new GUIFunctionalBlock(functionalName, 0,0);
        conditional = new GUIConditionalBlock(conditionalName, 0,0);
        operator = new GUIOperatorBlock(operatorName, 0,0);

        panel = new PalettePanel(cornerX, cornerY, width, height, new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator)));
    }

    @AfterEach
    void tearDown() {
        random = null;
        cavityName = null;
        functionalName = null;
        conditionalName = null;
        operatorName = null;
        cavity = null;
        functional = null;
        conditional = null;
        operator = null;
        panel = null;
    }

    @Test
    void palettePanel_invalidBlocks() {
        assertThrows(IllegalArgumentException.class, () -> new PalettePanel(cornerX, cornerY, width, height, new ArrayList<>(Collections.emptyList())));
    }

    @Test
    void areValidBlocks() {
        assertTrue(PalettePanel.areValidBlocks(new ArrayList<>(Arrays.asList(cavity, functional, conditional, operator))));
        assertFalse(PalettePanel.areValidBlocks(null));
        assertFalse(PalettePanel.areValidBlocks(new ArrayList<>(Collections.emptyList())));
        assertFalse(PalettePanel.areValidBlocks(new ArrayList<>(Arrays.asList(cavity, null, functional, conditional, operator))));
    }

    @Test
    void getPanelRectangle() {
        assertEquals(cornerX, panel.getPanelRectangle().getX());
        assertEquals(cornerY, panel.getPanelRectangle().getY());
        assertEquals(width, panel.getPanelRectangle().getWidth());
        assertEquals(height, panel.getPanelRectangle().getHeight());
        assertEquals(Color.lightGray, panel.getPanelRectangle().getColor());
    }

    @Test
    void getSize() {
        assertEquals(width, panel.getSize().getX());
        assertEquals(height, panel.getSize().getY());
    }

    @Test
    void getNewBlock_maxBlocksReached() {
        panel.onMaxBlocksReached(true);
        assertThrows(IllegalStateException.class, () -> panel.getNewBlock(0));
    }

    @Test
    void getNewBlock_outOfRange() {
        assertThrows(IllegalArgumentException.class, () -> panel.getNewBlock(-1));
        assertThrows(IllegalArgumentException.class, () -> panel.getNewBlock(4));
    }

    @Test
    void getNewBlock() {
        GUIBlock block = panel.getNewBlock(0);
        assertNotEquals(cavity, block);
        assertEquals(cavity.getClass(), block.getClass());

        block = panel.getNewBlock(1);
        assertNotEquals(functional, block);
        assertEquals(functional.getClass(), block.getClass());

        block = panel.getNewBlock(2);
        assertNotEquals(conditional, block);
        assertEquals(conditional.getClass(), block.getClass());

        block = panel.getNewBlock(3);
        assertNotEquals(operator, block);
        assertEquals(operator.getClass(), block.getClass());
    }

    @Test
    void getSelectedBlockIndex() {
        assertEquals(0, panel.getSelectedBlockIndex(cavity.getX(), cavity.getY()));
        assertEquals(1, panel.getSelectedBlockIndex(functional.getX(), functional.getY()));
        assertEquals(2, panel.getSelectedBlockIndex(conditional.getX(), conditional.getY()));
        assertEquals(3, panel.getSelectedBlockIndex(operator.getX(), operator.getY()));
        assertEquals(-1, panel.getSelectedBlockIndex(0,0));
    }

    @Test
    void onMaxBlocksReached() {
        panel.getNewBlock(0);
        panel.onMaxBlocksReached(true);
        assertThrows(IllegalStateException.class, () -> panel.getNewBlock(0));
        panel.onMaxBlocksReached(false);
        panel.getNewBlock(0);
    }
}