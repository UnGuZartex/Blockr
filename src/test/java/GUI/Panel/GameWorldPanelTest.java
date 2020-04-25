package GUI.Panel;

import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameWorldPanelTest {

    GameWorldPanel panel;
    int cornerX, cornerY, width, height;
    Random random;
    static final int MIN_X = 0, MAX_X = 150, MIN_Y = 0, MAX_Y = 150;
    static final int MIN_WIDTH = 100, MAX_WIDTH = 800, MIN_HEIGHT = 100, MAX_HEIGHT = 800;
    GameWorldType type;

    @BeforeEach
    void setUp() {
        random = new Random();
        cornerX = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        cornerY = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        width = random.nextInt(MAX_WIDTH + 1 - MIN_WIDTH) + MIN_WIDTH;
        height = random.nextInt(MAX_HEIGHT + 1 - MIN_HEIGHT) + MIN_HEIGHT;
        type = new LevelInitializer();
        panel = new GameWorldPanel(type.createNewGameWorld(), cornerX, cornerY, width, height);
    }

    @AfterEach
    void tearDown() {
        random = null;
        type = null;
        panel = null;
    }

    @Test
    void gameWorldPanel_invalidGameWorld() {
        assertThrows(IllegalArgumentException.class, () -> new GameWorldPanel(null, cornerX, cornerY, width, height));
    }

    @Test
    void isValidGameWorld() {
        assertTrue(GameWorldPanel.isValidGameWorld(type.createNewGameWorld()));
        assertFalse(GameWorldPanel.isValidGameWorld(null));
    }
}