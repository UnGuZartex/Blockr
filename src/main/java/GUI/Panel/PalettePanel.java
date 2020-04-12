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
     * Variables referring to the blocks in this panel.
     */
    public List<GUIBlock> blocks;

    private boolean reachedMaxBlocks;

    /**
     * TODO COMMENTAAR
     */

    /**
     * Initialise a new panel with given coordinates, dimensions and  controller.
     *
     * @param cornerX The x coordinate for the corner of this panel.
     * @param cornerY The y coordinate for the corner of this panel.
     * @param width The width of this panel.
     * @param height The height of this panel.
     *
     * @effect Super constructor is called with given coordinates and dimensions.
     * @effect The palette is refilled.
     * @effect The block positions are set.
     */
    public PalettePanel(int cornerX, int cornerY, int width, int height, List<GUIBlock> blocks) {
        super(cornerX, cornerY, width, height);
        this.blocks = blocks;
        setBlockPositions();
    }

    /**
     * Draw all the blocks in the panel.
     *
     * @param g The graphics to draw the blocks with.
     */
    public void drawBlocks(Graphics g) {
        if (!reachedMaxBlocks) {
            for (GUIBlock block : blocks) {
                block.paint(g);
            }
        }
    }

    public GUIBlock getNewBlock(int index) {

        if (index < 0 || index >= blocks.size()) {
            throw new IllegalArgumentException("The given index is invalid for this palette!");
        }

        return blocks.get(index).clone();
    }

    public int getSelectedBlockIndex(int x, int y) {
         return blocks.indexOf(blocks.stream().filter(b -> b.contains(x, y)).findFirst().orElse(null));
    }

    /**
     * Return the index of the given block in the palette if possible.
     *
     * @param block the given block
     *
     * @return the index of the block in the palette. Returns -1 if the block is not present
     *         in the palette or if the palette has reached its max blocks capacity.
     */

    /**
     * Paint this palette panel.
     *
     * @param g The graphics to paint this panel with.
     */
    @Override
    public void paint(Graphics g) {
        drawBackground(g);
    }

    // TODO via observer
    private void updatePalette(boolean reachedMaxBlocks) {
        this.reachedMaxBlocks = reachedMaxBlocks;
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
