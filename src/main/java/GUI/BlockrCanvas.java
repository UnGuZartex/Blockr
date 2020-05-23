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
import System.Logic.ProgramArea.PABlockHandler;

import java.awt.*;
import java.util.List;

public class BlockrCanvas extends CanvasWindow {

    public static final double PALETTE_WIDTH_RATIO = 0.2;
    public static final double PROGRAM_AREA_WIDTH_RATIO = 0.4;
    public static final double GAME_WORLD_WIDTH_RATIO = 0.4;

    private PalettePanel palettePanel;
    private ProgramAreaPanel programAreaPanel;
    private GameWorldPanel gameWorldPanel;
    private MouseHandler mouseHandler;
    private ControlHandler controlHandler;

    private GUIBlock highlightedBlock;
    private final BlockHandlerController blockHandlerController;
    private final ConnectionController connectionController;
    private ImageLibrary library;

    /**
     * Initializes a CanvasWindow object.
     *
     */
    // TODO exception throw (@throws)
    protected BlockrCanvas(ImageLibrary library, BlockHandlerController blockHandlerController,
                           ConnectionController connectionController) {
        super("Blockr");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        this.library = library;
        this.blockHandlerController = blockHandlerController;
        this.connectionController = connectionController;
    }

    public void setPanels(List<GUIBlock> panelBlocks, GameWorld gw, HistoryController historyController, PABlockHandler paBlockHandler) {
        palettePanel = new PalettePanel(0, 0, (int)(width * PALETTE_WIDTH_RATIO), height, panelBlocks);
        paBlockHandler.getPalette().subscribe(palettePanel);

        programAreaPanel = new ProgramAreaPanel((int)(width * PALETTE_WIDTH_RATIO),0, (int)(width * PROGRAM_AREA_WIDTH_RATIO), height, blockHandlerController, connectionController);
        paBlockHandler.getPA().subscribe(programAreaPanel);

        gameWorldPanel = new GameWorldPanel(gw, (int)(width * PALETTE_WIDTH_RATIO) + (int)(width * PROGRAM_AREA_WIDTH_RATIO),0, (int)(width * GAME_WORLD_WIDTH_RATIO), height);
        mouseHandler = new MouseHandler(new GUIBlockHandler(palettePanel, programAreaPanel), historyController);
        controlHandler = new ControlHandler(historyController);
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
}