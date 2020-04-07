package GUI.Panel;

import Controllers.ControllerClasses.ProgramController;
import GUI.Blocks.GUIBlock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for the palette panel. This is the panel where the
 * blocks can be selected from.
 *
 * @author Alpha-team
 */
public class PalettePanel extends GamePanel {

    /**
     * Variable referring to the controller of this panel.
     */
    private ProgramController controller;
    /**
     * Variables referring to the blocks in this panel.
     */
    public List<GUIBlock> blocks = new ArrayList<>();

    /**
     * Initialise a new panel with given coordinates, dimensions and  controller.
     *
     * @param cornerX The x coordinate for the corner of this panel.
     * @param cornerY The y coordinate for the corner of this panel.
     * @param width The width of this panel.
     * @param height The height of this panel.
     * @param controller The controller to control this panel.
     *
     * @effect Super constructor is called with given coordinates and dimensions.
     * @effect The palette is refilled.
     * @effect The block positions are set.
     *
     * @post The controller of this panel is set to the given controller.
     */
    public PalettePanel(int cornerX, int cornerY, int width, int height, ProgramController controller) {
        super(cornerX, cornerY, width, height);
        this.controller = controller;
        refillPalette();
        setBlockPositions();
    }

    /**
     * Get the blocks in this palette.
     *
     * @return A copy of the blocks in this palette.
     */
    public List<GUIBlock> getBlocks() {
        return new ArrayList<>(blocks);
    }

    /**
     * Get a new block with the given ID and coordinates.
     *
     * @param ID The id for the new block.
     * @param x The x coordinate for the new block.
     * @param y The y coordinate for the new block.
     *
     * @return A new GUI block with the given id and coordinates.
     */
    public GUIBlock getNewBlock(String ID, int x, int y) {
        return controller.getBlock(ID, x, y);
    }

    /**
     * Draw all the blocks in the panel.
     *
     * @param g The graphics to draw the blocks with.
     */
    public void drawBlocks(Graphics g) {
        for (GUIBlock block : blocks) {
            block.paint(g);
        }
    }

    /**
     * Paint this palette panel.
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
        g.drawImage(library.getPaletteBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        panelRectangle.paintNonFill(g);
    }

    /**
     * Update this panel.
     *
     * @effect If the maximum number of blocks is reached, then the blocks are cleared.
     * @effect Else f there are no blocks in the list, the palette is refilled and the blocks
     *         are positioned properly.
     */
    public void update() {
        if (controller.reachedMaxBlocks()) {
            blocks.clear();
        } else if (blocks.size() == 0) {
            refillPalette();
            setBlockPositions();
        }
    }

    /**
     * Refill this palette.
     *
     * @effect If the controller hasn't reached the maximum number of blocks, then
     *         are all with in the ID list initialised.
     */
    private void refillPalette() {
        if (!controller.reachedMaxBlocks()) {
            for (String id : IDList) {
                blocks.add(controller.getBlock(id, 0, 0));
            }
        }
    }

    /**
     * Set the positions of the blocks in the palette.
     *
     * @effect The blocks are set in the palette underneath each other.
     */
    private void setBlockPositions() {
        int freeHeightPerBlock = (panelRectangle.getHeight() - getTotalBlockHeight()) / (blocks.size() + 1);
        int currentHeight = freeHeightPerBlock;

        for (GUIBlock block : blocks) {
            block.setPosition((panelRectangle.getWidth() - block.getWidth()) / 2, currentHeight);
            currentHeight = currentHeight + block.getHeight() + freeHeightPerBlock;
        }
    }

    /**
     * Get the total number of blocks height in this panel.
     *
     * @return The sum of the heights of all the blocks in this palette.
     */
    private int getTotalBlockHeight() {
        int totalHeight = 0;
        for (GUIBlock block : blocks) {
            totalHeight += block.getHeight();
        }
        return totalHeight;
    }
}
