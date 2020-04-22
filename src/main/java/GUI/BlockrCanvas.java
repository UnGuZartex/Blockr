package GUI;

import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.ProgramController;
import GUI.Blocks.GUIBlock;
import GUI.Components.ControlHandler;
import GUI.Components.GUIBlockHandler;
import GUI.Panel.GameWorldPanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import GameWorldAPI.GameWorld.GameWorld;
import Images.ImageLibrary;

import java.awt.*;
import java.util.List;

public class BlockrCanvas extends CanvasWindow {

    public static final double PALETTE_WIDTH_RATIO = 0.1;
    public static final double PROGRAM_AREA_WIDTH_RATIO = 0.5;
    public static final double GAME_WORLD_WIDTH_RATIO = 0.4;

    private PalettePanel palettePanel;
    private ProgramAreaPanel programAreaPanel;
    private GameWorldPanel gameWorldPanel;
    private GUIBlockHandler blockHandler;
    private ControlHandler controlHandler;

    private GUIBlock highlightedBlock;
    private ProgramController programController;
    private ConnectionController connectionController;
    private ImageLibrary images;

    /**
     * Initializes a CanvasWindow object. 
     *
     */
    // TODO exception throw (@throws)
    protected BlockrCanvas(ImageLibrary images, ProgramController programController, ConnectionController connectionController) {
        super("Blockr");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        this.programController = programController;
        this.connectionController = connectionController;
        this.images = images;
    }

    public void setPanels(List<GUIBlock> panelBlocks, GameWorld gw) {
        palettePanel = new PalettePanel(0, 0, (int)(width * PALETTE_WIDTH_RATIO), height, panelBlocks);
        programAreaPanel = new ProgramAreaPanel((int)(width * PALETTE_WIDTH_RATIO),0, (int)(width * PROGRAM_AREA_WIDTH_RATIO), height, programController, connectionController);
        gameWorldPanel = new GameWorldPanel(gw, (int)(width * PALETTE_WIDTH_RATIO) + (int)(width * PROGRAM_AREA_WIDTH_RATIO),0, (int)(width * GAME_WORLD_WIDTH_RATIO), height);
        blockHandler = new GUIBlockHandler(palettePanel, programAreaPanel);
        controlHandler = new ControlHandler(programController, blockHandler.getHistory());
    }

    @Override
    protected void paint(Graphics g) {
        g.setColor(Color.black);
        gameWorldPanel.paint(g, images);
        palettePanel.paint(g, images);
        programAreaPanel.paint(g, images);
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);
        resetHighlightedBlock();
        blockHandler.handleMouseEvent(id, x, y);
        updateHighLightedBlock();
        repaint();
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar, int modifiers) {
        resetHighlightedBlock();
        controlHandler.handleKeyEvent(keyCode, modifiers);
        updateHighLightedBlock();
        repaint();
    }

    private void updateHighLightedBlock() {
        highlightedBlock = (GUIBlock) programController.getHightlightedBlock();
        if (highlightedBlock != null) highlightedBlock.setColor(Color.gray);
    }

    private void resetHighlightedBlock() {
        if (highlightedBlock != null) highlightedBlock.setColor(Color.white);
    }
}