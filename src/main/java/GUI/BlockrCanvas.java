package GUI;

import Controllers.ProgramController;
import GUI.Components.GUIBlockHandler;
import GUI.Images.ImagePreLoader;
import GUI.Panel.GamePanel;
import GUI.Panel.GameWorldPanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;

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

    private ProgramController programController = new ProgramController();


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
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);
        blockHandler.handleMouseEvent(id, x, y, programController);
        repaint();
    }

    private void setPanels() {
        palettePanel = new PalettePanel(0, 0, (int)(width * PALETTE_WIDTH_RATIO), height, programController);
        programAreaPanel = new ProgramAreaPanel((int)(width * PALETTE_WIDTH_RATIO),0, (int)(width * PROGRAMAREA_WIDTH_RATIO), height);
        gameWorldPanel = new GameWorldPanel((int)(width * PALETTE_WIDTH_RATIO) + (int)(width * PROGRAMAREA_WIDTH_RATIO),0, (int)(width * GAMEWORLD_WIDTH_RATIO), height);
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {

        if (keyCode == KeyEvent.VK_F5) {
            programController.runProgramStep();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            programController.resetProgram();
        }

        repaint();
    }
}
