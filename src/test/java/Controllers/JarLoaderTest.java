package Controllers;

import Controllers.Utility.JarLoader;
import GameWorldUtility.LevelInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JarLoaderTest {

    JarLoader loader;

    @BeforeEach
    void setUp() {
        loader = new JarLoader();
    }

    @AfterEach
    void tearDown() {
        loader = null;
    }

    @Test
    void load_noSetup() {
        System.clearProperty("GameWorldRootClass");
        assertThrows(Exception.class, () -> loader.load());
    }

    @Test
    void load() throws Exception {
        System.setProperty("GameWorldRootClass","GameWorldUtility.LevelInitializer");
        assertEquals(LevelInitializer.class, loader.load().getClass());
        assertEquals("RobotGameWorld",System.getProperty("GameWorldJar"));
    }

    @Test
    void load_invalidSetup() {
        System.setProperty("GameWorldRootClass","GameWorldUtility.NoneExistingObject");
        assertThrows(Exception.class, () -> loader.load());
    }
}