package GUI.Panel;

import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.ProgramController;
import Controllers.ProgramListener;
import GUI.Blocks.GUIBlock;
import GameWorldAPI.GameWorld.Result;
import Images.ImageLibrary;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for the panel of the program area. This panel can listen
 * to the program.
 *
 * @author ALpha-team
 */
public class ProgramAreaPanel extends GamePanel implements ProgramListener {

    /**
     * Variable referring to the blocks in the program area panel.
     */
    private List<GUIBlock> blocks = new ArrayList<>();

    /**
     * Variable referring to the block that is currently being dragged from the palette
     * and may or may not end up inside the program area.
     */
    private GUIBlock temporaryBlock;

    /**
     * Variable referring to the program controller.
     */
    private ProgramController programController;

    /**
     * Variable referring to the connection controller.
     */
    private ConnectionController connectionController;

    /**
     * Variable referring to the game state.
     */
    protected String gameState = "";

    /**
     * TODO commentaar
     */
    /**
     * Initialise a new program area panel with given corner, dimensions and controller.
     *
     * @param cornerX The x coordinate for the corner of this panel.
     * @param cornerY The y coordinate for the corner of this panel.
     * @param width The width of this panel.
     * @param height The height of this panel.
     * @param programController The program controller used to handle program actions.
     * @param connectionController The connection controller used to handle connections.
     *
     * @effect Calls super constructor with given coordinates and dimensions.
     * @effect Subscribes this panel as a listener to the panel.
     *
     * @post The program controller of this panel is set to the given program controller.
     * @post The connection controller of this panel is set to the given connection controller.
     */
    public ProgramAreaPanel(int cornerX, int cornerY, int width, int height, ProgramController programController,
                ConnectionController connectionController) {
        super(cornerX, cornerY, width, height);
        this.programController = programController;
        this.connectionController = connectionController;
        programController.subscribeListener(this);
    }

    /**
     * TODO wegkrijgen?
     * @return
     */
    public ConnectionController getConnectionController() {
        return connectionController;
    }

    /**
     * Add a block to the program area.
     *
     * @param block The block to add to the program area.
     * @param
     *
     * @effect TODO commentaar
     */
    public void addTemporaryBlockToProgramArea(int index) {

        if (temporaryBlock == null) {
            throw new IllegalStateException("");
        }

        if (blocks.contains(temporaryBlock)) {
            throw new IllegalStateException("Can't add a block that is already in the program area to the program area.");
        }

        blocks.add(temporaryBlock);
        programController.addBlockToPA(temporaryBlock, index);
    }

    public void setTemporaryBlock(GUIBlock block) {
        temporaryBlock = block;
    }

    /**
     * Disconnect the given block in the program area.
     *
     * @param GUIBlock The block to disconnect.
     *
     * @effect The block is disconnected from the program area
     * @effect The program is reset.
     */
    public void disconnectInProgramArea(GUIBlock GUIBlock) {
        GUIBlock.disconnectMainConnector();
        connectionController.disconnectBlock(GUIBlock);
    }

    /**
     * Delete the given blocks from the program area.
     *
     * @param GUIBlocks The blocks to delete from the program area.
     *
     * @post All given blocks are deleted from the program area.
     */
    public void deleteBlockFromProgramArea(List<GUIBlock> GUIBlocks) {
        blocks.removeAll(GUIBlocks);

        for (GUIBlock block : GUIBlocks) {
            programController.deleteFromPA(block);
        }
    }

    /**
     * Get all blocks in the program area.
     *
     * @return All the blocks in the program area.
     */
    public List<GUIBlock> getBlocks() {
        return new ArrayList<>(blocks);
    }

    /**
     * Set the order to draw the blocks properly.
     *
     * @param highestLayerBlocks The block which should be drawn on top.
     *
     * @post The blocks which should be drawn last are put at the end
     *       of the blocks list.
     */
    public void setBlockDrawLayerFirst(List<GUIBlock> highestLayerBlocks) {
        blocks.remove(highestLayerBlocks);
        blocks.addAll(highestLayerBlocks);
    }

    /**
     * Event to call if the game has finished
     *
     * @param result The result of the game
     *
     * @effect The colors of the blocks are changed to green if the player reached its goal
     *         during the game, orange otherwise.
     */
    @Override
    public void onGameFinished(Result result) {
        switch (result) {
            case END:
                changeBlockColors(Color.green);
                gameState = "YOU WIN!  :)";
                break;

            case FAILURE:
            case SUCCESS:
                changeBlockColors(Color.orange);
                gameState = "YOU LOSE!  :(";

                break;
        }
    }

    /**
     * Event to call when the game is reset.
     *
     * @effect The block colors are set to white.
     */
    @Override
    public void onProgramReset() {
        changeBlockColors(Color.white);
        gameState = "";
    }

    /**
     * Event to call when there are to many programs.
     *
     * @effect The colors of the blocks are set to red.
     */
    @Override
    public void onTooManyPrograms() {
        changeBlockColors(Color.red);
        gameState = "TOO MANY PROGRAMS!";
    }

    /**
     * Event to call when the program is invalid.
     *
     * @effect The block colors are set to red.
     */
    @Override
    public void onProgramInvalid() {
        changeBlockColors(Color.red);
        gameState = "INVALID PROGRAM";

    }

    @Override
    public void onExecuting(boolean b) {
    }

    /**
     * Change the color of the blocks to the given color.
     *
     * @param color The new color for the blocks.
     */
    private void changeBlockColors(Color color) {
        for (GUIBlock block : blocks) {
            block.setColor(color);
        }
    }

    /**
     * Paint this panel.
     *
     * @param g The given graphics.
     * @library library The image library.
     *
     * @effect The program area background is drawn.
     * @effect The program area blocks are drawn.
     * @effect The game state is drawn.
     */
    @Override
    public void paint(Graphics g, ImageLibrary library) {
        drawBackGround(g, library, "programAreaBackground");
        drawBlocks(g);
        drawGameState(g);
    }

    /**
     * Draw all the blocks in the program area + the temporary block if it isn't null.
     *
     * @param g The graphics to draw the blocks with.
     */
    private void drawBlocks(Graphics g) {
        for (GUIBlock block : blocks) {
            block.paint(g);
        }

        if (temporaryBlock != null) {
            temporaryBlock.paint(g);
        }
    }

    /**
     * Draw the gamestate of this panel.
     *
     * @param g The graphics to draw the game state with.
     */
    private void drawGameState(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 4F);
        g.setFont(newFont);
        g.drawString(gameState, panelRectangle.getX(), 100);
        g.setFont(currentFont);
    }
}
