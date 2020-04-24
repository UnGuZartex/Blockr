package GUI.Panel;

import Controllers.ProgramAreaListener;
import GUI.Blocks.GUIBlock;
import Images.ImageLibrary;

import java.awt.*;
import java.util.List;

/**
 * A class for the palette panel. This is the panel where the
 * blocks can be selected from.
 *
 * @invar The palette panel must contain valid blocks at any time.
 *        | areValidBlocks(blocks)
 *
 * @author Alpha-team
 */
public class PalettePanel extends GamePanel implements ProgramAreaListener {

    /**
     * Variables referring to the blocks in this palette.
     */
    private final List<GUIBlock> blocks;

    /**
     * Variable indicating if the max amount of blocks is reached in the program area.
     */
    private boolean reachedMaxBlocks;

    /**
     * Initialize a new ui palette panel with given coordinates, dimensions and list of palette ui blocks.
     *
     * @param cornerX The given x coordinate for the corner of this panel.
     * @param cornerY The given y coordinate for the corner of this panel.
     * @param width The given width of this panel.
     * @param height The given height of this panel.
     * @param blocks The given list of palette ui blocks.
     *
     * @post The blocks in this palette are set to the given list of blocks.
     *
     * @effect Super constructor is called with given coordinates and dimensions.
     * @effect The block positions are set.
     *
     * @throws IllegalArgumentException
     *         when the given list doesn't contain at least one block.
     */
    public PalettePanel(int cornerX, int cornerY, int width, int height, List<GUIBlock> blocks) throws IllegalArgumentException {
        super(cornerX, cornerY, width, height);
        if (!areValidBlocks(blocks)) {
            throw new IllegalArgumentException("The given blocks are not valid!");
        }
        this.blocks = blocks;
        setBlockPositions();
    }

    /**
     * Checks whether or not the given blocks are valid for the palette.
     *
     * @param blocks The blocks to check.
     *
     * @return True if and only if the given blocks are not null, contain at least one
     *         gui block but doesn't contain null.
     */
    public static boolean areValidBlocks(List<GUIBlock> blocks) {
        return blocks != null && blocks.size() >= 1 && !blocks.contains(null);
    }

    /**
     * Return a clone of the block at the given palette index.
     *
     * @param index The given block index.
     *
     * @return A clone of the block at the given palette index.
     *
     * @throws IllegalStateException
     *         when the max amount of blocks has been reached.
     * @throws IllegalArgumentException
     *         when the given index is invalid for this palette.
     */
    public GUIBlock getNewBlock(int index) throws IllegalStateException, IllegalArgumentException {
        if (reachedMaxBlocks) {
            throw new IllegalStateException("The max amount of blocks has been reached!");
        }
        if (index < 0 || index >= blocks.size()) {
            throw new IllegalArgumentException("The given index is invalid for this palette!");
        }
        return blocks.get(index).clone();
    }

    /**
     * Return the index of the block colliding with the given x and y coordinates if possible,
     * return null otherwise.
     *
     * @param x The given x-coordinate.
     * @param y The given y-coordinate.
     *
     * @return the index of the block colliding with the given x and y coordinates, -1 otherwise.
     */
    public int getSelectedBlockIndex(int x, int y) {
         return blocks.indexOf(blocks.stream().filter(b -> b.contains(x, y)).findFirst().orElse(null));
    }

    /**
     * Paint this palette panel.
     *
     * @param g The graphics to paint this panel with.
     */
    @Override
    public void paint(Graphics g, ImageLibrary images) {
        drawBackground(g);
        drawBlocks(g);
    }

    /**
     * Draw all the blocks in the panel.
     *
     * @param g The graphics to draw the blocks with.
     */
    private void drawBlocks(Graphics g) {
        if (!reachedMaxBlocks) {
            for (GUIBlock block : blocks) {
                block.paint(g);
            }
        }
    }

    /**
     * This event is called when the program area has either reached its max blocks, or
     * the current amount of blocks is under the max blocks again.
     *
     * @param reachedMaxBlocks the given boolean indicating if the number of max blocks in the program area is reached.
     *
     * @post The variable referring if the max blocks are reached is set to the given boolean.
     */
    @Override
    public void onMaxBlocksReached(boolean reachedMaxBlocks) {
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
