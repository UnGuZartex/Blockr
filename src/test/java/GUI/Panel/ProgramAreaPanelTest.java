package GUI.Panel;

import Controllers.ConnectionController;
import Controllers.GUItoSystemInterface;
import Controllers.ProgramController;
import GUI.Blocks.Factories.GUIFactory;
import GUI.Blocks.Factories.MoveForwardGUIFactory;
import GUI.Blocks.GUIBlock;
import System.GameWorld.Level.LevelLoader;
import System.Logic.ProgramArea.PABlockHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ProgramAreaPanelTest {

    ProgramAreaPanel panel;
    int cornerX, cornerY, width, height;
    ProgramController controller;
    GUIBlock block;

    @BeforeEach
    void setUp() {
        cornerX = 5;
        cornerY = 8;
        width = 500;
        height = 800;
        PABlockHandler blockHandler = new PABlockHandler();
        GUItoSystemInterface converter = new GUItoSystemInterface(blockHandler);
        controller = new ProgramController(converter, blockHandler);
        ConnectionController connectionController = new ConnectionController(converter, blockHandler);
        LevelLoader loader = new LevelLoader();
        loader.loadLevel();
        panel = new ProgramAreaPanel(cornerX, cornerY, width, height, controller, connectionController);
        GUIFactory f = new MoveForwardGUIFactory();
        block = f.createBlock("id", 0, 0);
        panel.addBlockToProgramArea(block);
    }

    @AfterEach
    void tearDown() {
        controller = null;
        panel = null;
        block = null;
    }

    @Test
    void addBlockToProgramArea() {
        assertTrue(panel.getBlocks().contains(block));
        GUIFactory f = new MoveForwardGUIFactory();
        GUIBlock block2 = f.createBlock("id", 0, 0);
        assertFalse(panel.getBlocks().contains(block2));
        panel.addBlockToProgramArea(block2);
        assertTrue(panel.getBlocks().contains(block2));
    }

    @Test
    void getBlocks() {
        assertTrue(panel.getBlocks().contains(block));
        assertEquals(1, panel.getBlocks().size());
    }

    @Test
    void onGameWon() {
        panel.onGameWon();
        assertEquals(Color.green, block.getColor());
    }

    @Test
    void onGameLost() {
        panel.onGameLost();
        assertEquals(Color.orange, block.getColor());
    }

    @Test
    void onProgramReset() {
        panel.onProgramReset();
        assertEquals(Color.white, block.getColor());
    }

    @Test
    void onTooManyPrograms() {
        panel.onTooManyPrograms();
        assertEquals(Color.red, block.getColor());
    }

    @Test
    void onProgramInvalid() {
        panel.onProgramInvalid();
        assertEquals(Color.red, block.getColor());
    }
}