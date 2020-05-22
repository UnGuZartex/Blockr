package GUI.Panel;

import Controllers.PaletteListener;
import Controllers.ProgramAreaListener;
import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICallerBlock;
import Images.ImageLibrary;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
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
public class PalettePanel extends GamePanel implements ProgramAreaListener, PaletteListener {

    private static int PALETTE_COLUMNS = 2;

    /**
     * Variables referring to the blocks in this palette.
     */
    private List<GUIBlock> blocks;

    /**
     * TODO KIJK IN DEZE KLASSE ALLE COMMENTS NA!!!!!!!!
     */
    private List<GUICallerBlock> callerBlocks = new ArrayList<>();


    /**
     * Variable indicating if the max amount of blocks is reached in the program area.
     */
    private boolean reachedMaxBlocks;

    /**
     * TODO Comments
     */
    private GUIBlock lastCreated;

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
     * @effect The super constructor is called with given coordinates and dimensions.
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
        setBlockPositions(blocks, 1);
    }

    /**
     * Checks whether the given blocks are valid for the palette.
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

        if (index < 0 || index >= blocks.size() + callerBlocks.size()) {
            throw new IllegalArgumentException("The given index is invalid for this palette!");
        }

        if (index >= blocks.size()) {
            index %= blocks.size();
            lastCreated = callerBlocks.get(index).clone();
        }
        else {
            lastCreated = blocks.get(index).clone();
        }
        return lastCreated;
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
        List<GUIBlock> combined = new ArrayList<>(blocks);
        combined.addAll(callerBlocks);
         return combined.indexOf(combined.stream().filter(b -> b.contains(x, y)).findFirst().orElse(null));
    }

    /**
     * Paint this palette panel.
     *
     * @param g The given graphics.
     * @library library The image library.
     *
     * @effect The palette background is drawn.
     * @effect The palette blocks are drawn.
     */
    @Override
    public void paint(Graphics g, ImageLibrary library) {
        drawBackGround(g, library, "paletteBackground");
        drawBlocks(g);
    }

    /**
     * Draw all the blocks in the panel.
     *
     * @param g The graphics to draw the blocks with.
     *
     * @effect All blocks inside the panel are drawn.
     */
    private void drawBlocks(Graphics g) {
        List<GUIBlock> combined = new ArrayList<>(blocks);
        combined.addAll(callerBlocks);
        if (!reachedMaxBlocks) {
            for (GUIBlock block : combined) {
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



    private <T extends GUIBlock> void setBlockPositions(List<T> blocks, int column) {
        List<T> sortedBlocks = new ArrayList<>(blocks);
        sortedBlocks.sort(Comparator.comparing(GUIBlock::getName));
        int freeHeightPerBlock = (panelRectangle.getHeight() - getTotalBlockHeight(blocks)) / (blocks.size() + 1);
        int currentHeight = freeHeightPerBlock;
        int panelWidth = panelRectangle.getWidth() / PALETTE_COLUMNS;

        for (GUIBlock block : sortedBlocks) {
            block.setPosition((panelWidth * (column - 1)) + (panelWidth - block.getWidth()) / 2, currentHeight);
            currentHeight = currentHeight + block.getTotalHeight() + freeHeightPerBlock;
        }
    }

    /**
     * Get the total number of blocks height in this panel.
     *
     * @return The sum of the heights of all the blocks in this palette.
     */
    /**
     * TODO comments
     * @param blockList
     * @param <T>
     * @return
     */
    private <T extends GUIBlock> int getTotalBlockHeight(List<T> blockList) {
        int totalHeight = 0;
        for (GUIBlock block : blockList) {
            totalHeight += block.getTotalHeight();
        }
        return totalHeight;
    }

    @Override
    public void procedureCreated() {
        GUICallerBlock caller = new GUICallerBlock(0, 0, Integer.valueOf(lastCreated.getName().split(" ")[1]));
        callerBlocks.add(caller);
        setBlockPositions(callerBlocks, 2);
    }

    @Override
    public void procedureDeleted(int index) {
        callerBlocks.get(index).terminate();
        callerBlocks.remove(index);
        setBlockPositions(callerBlocks, 2);
    }
}
