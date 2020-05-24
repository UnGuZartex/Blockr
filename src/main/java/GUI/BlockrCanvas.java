package GUI;

import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.HistoryController;
import Controllers.ControllerClasses.BlockHandlerController;
import GUI.Blocks.GUIBlock;
import GUI.Components.ControlHandler;
import GUI.Components.GUIBlockHandler;
import GUI.Components.MouseHandler;
import GUI.Panel.GameWorldPanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import GameWorldAPI.GameWorld.GameWorld;
import Images.ImageLibrary;

import java.awt.*;
import java.util.List;

public class BlockrCanvas extends CanvasWindow {

    private static final double PALETTE_WIDTH_RATIO = 0.2;
    private static final double PROGRAM_AREA_WIDTH_RATIO = 0.4;
    private static final double GAME_WORLD_WIDTH_RATIO = 0.4;

    private PalettePanel palettePanel;
    private ProgramAreaPanel programAreaPanel;
    private GameWorldPanel gameWorldPanel;
    private MouseHandler mouseHandler;
    private ControlHandler controlHandler;

    private GUIBlock highlightedBlock;
    private final BlockHandlerController blockHandlerController;
    private final ImageLibrary library;

    /**
     * Initializes a CanvasWindow object.
     *
     */
    // TODO exception throw (@throws)
    protected BlockrCanvas(ImageLibrary library, BlockHandlerController blockHandlerController,
                           ConnectionController connectionController, HistoryController historyController,
                           List<GUIBlock> panelBlocks, GameWorld gw) {
        super("Blockr");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        this.library = library;
        this.blockHandlerController = blockHandlerController;

        createPanels(connectionController, panelBlocks, gw);
        createHandlers(historyController);
    }

    public PalettePanel getPalettePanel() {
        return palettePanel;
    }

    public ProgramAreaPanel getProgramAreaPanel() {
        return programAreaPanel;
    }

    public GameWorldPanel getGameWorldPanel() {
        return gameWorldPanel;
    }

    @Override
    protected void paint(Graphics g) {
        g.setColor(Color.black);
        gameWorldPanel.paint(g, library);
        palettePanel.paint(g, library);
        programAreaPanel.paint(g, library);
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);
        resetHighlightedBlock();
        mouseHandler.handleMouseEvent(id, x, y);
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
        highlightedBlock = (GUIBlock) blockHandlerController.getHighlightedBlock();
        if (highlightedBlock != null) highlightedBlock.setColor(Color.gray);
    }

    private void resetHighlightedBlock() {
        if (highlightedBlock != null) highlightedBlock.setColor(Color.white);
    }

    private void createPanels(ConnectionController connectionController, List<GUIBlock> panelBlocks, GameWorld gw) {
        palettePanel = new PalettePanel(0, 0, (int)(width * PALETTE_WIDTH_RATIO), height, panelBlocks);
        programAreaPanel = new ProgramAreaPanel((int)(width * PALETTE_WIDTH_RATIO),0, (int)(width * PROGRAM_AREA_WIDTH_RATIO), height, blockHandlerController, connectionController);
        gameWorldPanel = new GameWorldPanel(gw, (int)(width * PALETTE_WIDTH_RATIO) + (int)(width * PROGRAM_AREA_WIDTH_RATIO),0, (int)(width * GAME_WORLD_WIDTH_RATIO), height);
    }

    private void createHandlers(HistoryController historyController) {
        mouseHandler = new MouseHandler(new GUIBlockHandler(palettePanel, programAreaPanel), historyController);
        controlHandler = new ControlHandler(historyController);
    }
}