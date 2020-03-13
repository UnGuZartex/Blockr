package System.GameWorld.Level;

import System.GameState.GameState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class LevelLoaderTest {

    LevelLoader loader;

    @BeforeEach
    void setUp() {
        loader = new LevelLoader();
    }

    @AfterEach
    void tearDown() {
        loader = null;
    }

    @Test
    void loadLevel() {
        loader.loadLevel();
        assertNotNull(GameState.getCurrentLevel());
    }
}