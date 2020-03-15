package GUI;

import Controllers.ConnectionController;
import Controllers.GUItoSystemInterface;
import Controllers.ProgramController;
import GUI.Blocks.GUIBlock;
import GUI.Components.GUIBlockHandler;
import GUI.Images.ImagePreLoader;
import GUI.Panel.GamePanel;
import GUI.Panel.GameWorldPanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import System.Logic.ProgramArea.PABlockHandler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class BlockrCanvas extends CanvasWindow {

    public static final double PALETTE_WIDTH_RATIO = 0.1;
    public static final double PROGRAMAREA_WIDTH_RATIO = 0.5;
    public static final double GAMEWORLD_WIDTH_RATIO = 0.4;

    private ProgramAreaPanel programAreaPanel;
    private GameWorldPanel gameWorldPanel;
    private PalettePanel palettePanel;
    private GUIBlockHandler blockHandler;
    private GUIBlock previousBlock;

    private ProgramController programController;
    private ConnectionController connectionController;

    /**
     * Initializes a CanvasWindow object. 
     *
     * @param title Window title
     */
    // TODO exception throw (@throws)
    protected BlockrCanvas(String title, int width, int height, String imagePackName) throws IOException {
        super(title);

        this.width = width;
        this.height = height;

        GamePanel.setImageLibrary(ImagePreLoader.createImageLibrary(imagePackName));
        setControllers();
        setPanels();

        blockHandler = new GUIBlockHandler(palettePanel, programAreaPanel);
    }

    protected BlockrCanvas(String title, int width, int height) throws IOException {
        this(title, width, height, "");
    }

    @Override
    protected void paint(Graphics g) {
        g.setColor(Color.black);
        programAreaPanel.paint(g);
        gameWorldPanel.paint(g);
        palettePanel.paint(g);
        programAreaPanel.drawBlocks(g);
        palettePanel.drawBlocks(g);
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);
        blockHandler.handleMouseEvent(id, x, y);
        repaint();
    }

    private void setPanels() {
        palettePanel = new PalettePanel(0, 0, (int)(width * PALETTE_WIDTH_RATIO), height, programController);
        programAreaPanel = new ProgramAreaPanel((int)(width * PALETTE_WIDTH_RATIO),0, (int)(width * PROGRAMAREA_WIDTH_RATIO), height, programController, connectionController);
        gameWorldPanel = new GameWorldPanel((int)(width * PALETTE_WIDTH_RATIO) + (int)(width * PROGRAMAREA_WIDTH_RATIO),0, (int)(width * GAMEWORLD_WIDTH_RATIO), height, programController);
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {

        if (keyCode == KeyEvent.VK_F5) {

            if (previousBlock != null) {
                previousBlock.setColor(Color.white);
            }

            programController.runProgramStep();
            previousBlock = programController.getHightlightedBlock();
            if (previousBlock != null) {
                previousBlock.setColor(Color.red);
            }
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            programController.resetProgram();
            gameWorldPanel.resetGameText();
        }

        repaint();
    }

    private void setControllers() {
        PABlockHandler blockHandler = new PABlockHandler();
        GUItoSystemInterface converter = new GUItoSystemInterface(blockHandler);
        connectionController = new ConnectionController(converter, blockHandler);
        programController = new ProgramController(converter, blockHandler);
    }
}
