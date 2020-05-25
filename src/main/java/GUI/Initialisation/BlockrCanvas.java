package GUI.Initialisation;

import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.HistoryController;
import Controllers.ControllerClasses.BlockHandlerController;
import GUI.Blocks.GUIBlock;
import GUI.Handlers.ControlHandler;
import GUI.Handlers.GUIBlockHandler;
import GUI.Handlers.MouseHandler;
import GUI.Panels.GameWorldPanel;
import GUI.Panels.PalettePanel;
import GUI.Panels.ProgramAreaPanel;
import GameWorldAPI.GameWorld.GameWorld;
import Images.ImageLibrary;

import java.awt.*;
import java.util.List;

/**
 * A class for the blockr canvas, which is a specific type of canvas window.
 *
 * @author Alhpa-team
 */
public class BlockrCanvas extends CanvasWindow {

    /**
     * Variables referring to the ratios of the different panels.
     */
    private static final double PALETTE_WIDTH_RATIO = 0.2;
    private static final double PROGRAM_AREA_WIDTH_RATIO = 0.4;
    private static final double GAME_WORLD_WIDTH_RATIO = 0.4;

    /**
     * Variable referring to the palette panel.
     */
    private PalettePanel palettePanel;
    /**
     * Variable referring to the program area panel.
     */
    private ProgramAreaPanel programAreaPanel;
    /**
     * Variable referring to the game world panel.
     */
    private GameWorldPanel gameWorldPanel;
    /**
     * Variable referring to the mouse handler.
     */
    private MouseHandler mouseHandler;
    /**
     * Variable referring to the control handler.
     */
    private ControlHandler controlHandler;
    /**
     * Variable referring to the block handler controller.
     */
    private final BlockHandlerController blockHandlerController;
    /**
     * Variable referring to the image library.
     */
    private final ImageLibrary library;
    /**
     * Variable referring to the GUI block which should be highlighted.
     */
    private GUIBlock highlightedBlock;

    /**
     * Initializes a Blockr Canvas Window object.
     *
     * @param library The image library for this canvas.
     * @param blockHandlerController The block handler controller for this canvas.
     * @param connectionController The connection controller to use in this canvas.
     * @param historyController The history controller to use in this canvas.
     * @param paletteBlocks The blocks to use in the palette of this canvas.
     * @param gw The game world to use in this canvas.
     *
     * @post The width of this canvas is set to the width of the default screen.
     * @post The height of this canvas is set to the height of the default screen.
     * @post The library is set to the given library.
     * @post The block handler controller is set to the given block handler.
     *
     * @effect Calls super constructor with the title 'Blockr'.
     * @effect Create the panels using the given connection controller, panel blocks and game world.
     * @effect Create the handlers using the given history controller.
     */
    protected BlockrCanvas(ImageLibrary library, BlockHandlerController blockHandlerController,
                           ConnectionController connectionController, HistoryController historyController,
                           List<GUIBlock> paletteBlocks, GameWorld gw) {
        super("Blockr");

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
        this.library = library;
        this.blockHandlerController = blockHandlerController;

        createPanels(connectionController, paletteBlocks, gw);
        createHandlers(historyController);
    }

    /**
     * Get the palette panel of this canvas.
     *
     * @return The palette panel of this canvas.
     */
    public PalettePanel getPalettePanel() {
        return palettePanel;
    }

    /**
     * Get the program area panel of this canvas.
     *
     * @return The program area panel of this canvas.
     */
    public ProgramAreaPanel getProgramAreaPanel() {
        return programAreaPanel;
    }

    /**
     * Get the game world panel of this canvas.
     *
     * @return The game world panel of this canvas.
     */
    public GameWorldPanel getGameWorldPanel() {
        return gameWorldPanel;
    }

    /**
     * Paint this canvas window.
     *
     * @param g This object offers the methods that allow you to paint on the canvas.
     *
     * @effect The colour of the given graphics is set to black.
     * @effect The game world panel is painted using the given graphics and image library.
     * @effect The palette panel is painted using the given graphics and image library.
     * @effect The program area panel is painted using the given graphics and image library.
     */
    @Override
    protected void paint(Graphics g) {
        g.setColor(Color.black);
        gameWorldPanel.paint(g, library);
        palettePanel.paint(g, library);
        programAreaPanel.paint(g, library);
    }

    /**
     * Handle the mouse event.
     *
     * @param id The id of the mouse event which occurred.
     * @param x The x coordinate of where the event occurred.
     * @param y The y coordinate of where the event occurred.
     * @param clickCount The amount of clicks.
     *
     * @effect The mouse event is handled with the given parameters.
     * @effect The highlighted block is reset.
     * @effect The mouse handler handles the mouse event.
     * @effect The highlighted block is updated.
     * @effect The canvas is repainted.
     */
    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);
        resetHighlightedBlock();
        mouseHandler.handleMouseEvent(id, x, y);
        updateHighLightedBlock();
        repaint();
    }

    /**
     * Handle the key event.
     *
     * @param id The id of the key event which occurred.
     * @param keyCode The key code of the pressed key.
     * @param keyChar The key char of the pressed key.
     * @param modifiers The extra modifiers which where pressed.
     *
     * @effect The highlighted block is reset.
     * @effect The control handler handles the key event.
     * @effect The highlighted block is updated.
     * @effect The canvas is repainted.
     */
    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar, int modifiers) {
        resetHighlightedBlock();
        controlHandler.handleKeyEvent(keyCode, modifiers);
        updateHighLightedBlock();
        repaint();
    }

    /**
     * Update the highlighted block.
     *
     * @post The highlighted block from the block handler controller is set in a gray
     *       colour, if any exists.
     */
    private void updateHighLightedBlock() {
        highlightedBlock = (GUIBlock) blockHandlerController.getHighlightedBlock();
        if (highlightedBlock != null) highlightedBlock.setColor(Color.gray);
    }

    /**
     * Reset the highlighted block.
     *
     * @post IF this canvas contains a highlighted block, then its colour is set to white.
     */
    private void resetHighlightedBlock() {
        if (highlightedBlock != null) highlightedBlock.setColor(Color.white);
    }

    /**
     * Create the panels of this canvas.
     *
     * @param connectionController The connection controller to use in the panels.
     * @param paletteBlocks The blocks for the palette.
     * @param gw The game world to use in the panels.
     *
     * @post The palette panel is set to a new panel starting on 0,0 with the width (according to the
     *       panel ratio) and height of this canvas, as well as the given game world and palette blocks.
     * @post The program area panel is set to a new panel starting on 0,0 with the width (according to
     *       the panel ratio) and height of this canvas, as well as the given connection controller.
     * @post The game world panel is set to a new panel starting on 0,0 with the width (according to the
     *       panel ratio) and height of this canvas, as well as the given game world.
     */
    private void createPanels(ConnectionController connectionController, List<GUIBlock> paletteBlocks, GameWorld gw) {
        palettePanel = new PalettePanel(0, 0, (int)(width * PALETTE_WIDTH_RATIO), height, paletteBlocks);
        programAreaPanel = new ProgramAreaPanel((int)(width * PALETTE_WIDTH_RATIO),0, (int)(width * PROGRAM_AREA_WIDTH_RATIO), height, blockHandlerController, connectionController);
        gameWorldPanel = new GameWorldPanel(gw, (int)(width * PALETTE_WIDTH_RATIO) + (int)(width * PROGRAM_AREA_WIDTH_RATIO),0, (int)(width * GAME_WORLD_WIDTH_RATIO), height);
    }

    /**
     * The handlers of this canvas are set.
     *
     * @param historyController The history controller for the handlers.
     *
     * @post The mouse handler is set to a new mouse handler with a new gui block handler using
     *       the palette and program area panel of this canvas, as well as the given history
     *       controller.
     * @post The control handler is set to a new control handler with the given history controller.
     */
    private void createHandlers(HistoryController historyController) {
        mouseHandler = new MouseHandler(new GUIBlockHandler(palettePanel, programAreaPanel), historyController);
        controlHandler = new ControlHandler(historyController);
    }
}