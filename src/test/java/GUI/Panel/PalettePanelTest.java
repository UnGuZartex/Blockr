package GUI.Panel;

import Controllers.GUItoSystemInterface;
import Controllers.ProgramController;
import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;
import System.GameState.GameState;
import System.GameWorld.Level.LevelLoader;
import System.Logic.ProgramArea.PABlockHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PalettePanelTest {

    PalettePanel panel;
    int cornerX, cornerY, width, height;
    ProgramController controller;

    @BeforeEach
    void setUp() {
        cornerX = 5;
        cornerY = 8;
        width = 500;
        height = 800;
        PABlockHandler blockHandler = new PABlockHandler();
        GUItoSystemInterface converter = new GUItoSystemInterface(blockHandler);
        controller = new ProgramController(converter, blockHandler);
        LevelLoader loader = new LevelLoader();
        loader.loadLevel();
        panel = new PalettePanel(cornerX, cornerY, width, height, controller);

    }

    @AfterEach
    void tearDown() {
        controller = null;
        panel = null;
    }

    @Test
    void getBlocks() {
        GameState.setMaxAmountOfBlocks(5);
        panel.update();
        assertEquals(7, panel.getBlocks().size());
    }

    @Test
    void getNewBlock() {
        GUIBlock block = panel.getNewBlock("IF", 2,3);
        assertEquals(2, block.getX());
        assertEquals(3, block.getY());
        assertTrue(block instanceof GUICavityBlock);
    }

    @Test
    void update() {
        GameState.setMaxAmountOfBlocks(0);
        panel.update();;
        assertEquals(0, panel.getBlocks().size());

        GameState.setMaxAmountOfBlocks(5);
        panel.update();
        assertEquals(7, panel.getBlocks().size());
    }
}