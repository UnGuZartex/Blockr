package GUI.Panel;

import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.ProgramController;
import Controllers.ProgramListener;
import GUI.Blocks.GUIBlock;

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
    private ProgramController programController;
    private ConnectionController connectionController;

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

    public ProgramController getProgramController() {
        return programController;
    }

    public ConnectionController getConnectionController() {
        return connectionController;
    }

    /**
     * Add a block to the program area.
     *
     * @param block The block to add to the program area.
     */
    public void addBlockToProgramArea(GUIBlock block) {
        programController.addBlockToPA(block);
        if (!blocks.contains(block)) {
            blocks.add(block);
        }
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
        connectionController.disconnectBlock(GUIBlock);
        programController.resetProgram();
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
        for (GUIBlock block:GUIBlocks) {
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
     * Draw all the blocks in the program area.
     *
     * @param g The graphics to draw the blocks with.
     */
    public void drawBlocks(Graphics g) {
        for (GUIBlock block : blocks) {
            block.paint(g);
        }
    }

    /**
     * Paint this panel.
     *
     * @param g The graphics to paint this panel with.
     */
    @Override
    public void paint(Graphics g) {
        drawBackground(g);
    }

    /**
     * Draw the background of this panel.
     *
     * @param g The graphics to draw the background with.
     */
    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getProgramAreaBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        panelRectangle.paintNonFill(g);
    }

    /**
     * Event to call if the game has won.
     *
     * @effect The block colors are set to green.
     */
    @Override
    public void onGameWon() {
        changeBlockColors(Color.green);
    }

    /**
     * Event to call if the game has been lost.
     *
     * @effect The block colors are set to orange.
     */
    @Override
    public void onGameLost() {
        changeBlockColors(Color.orange);
    }

    /**
     * Event to call when the game is reset.
     *
     * @effect The block colors are set to white.
     */
    @Override
    public void onProgramReset() {
        changeBlockColors(Color.white);
    }

    /**
     * Event to call when there are to many programs.
     *
     * @effect The colors of the blocks are set to red.
     */
    @Override
    public void onTooManyPrograms() {
        changeBlockColors(Color.red);
    }

    /**
     * Event to call when the program is invalid.
     *
     * @effect The block colors are set to red.
     */
    @Override
    public void onProgramInvalid() {
        changeBlockColors(Color.red);
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
}
