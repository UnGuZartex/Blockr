package Controllers;

import GUI.Blocks.IGUIBlock;
import System.BlockStructure.Blocks.Block;

import java.util.*;

/**
 * A class used for linking graphical and system blocks.
 *
 * @author Alpha-team
 */
public class IGUI_System_BlockLink {

    /**
     * Variable representing the map of paired graphical and system blocks.
     */
    private final HashMap<IGUIBlock, Block> currentBlocks = new HashMap<>();

    /**
     * Return the system block linked to the given graphical block.
     *
     * @param block The given graphical block.
     *
     * @return the system block linked to the given graphical block.
     *
     * @throws IllegalArgumentException
     *         when the given graphical block is not linked to any system block.
     */
    public Block getBlockFromGUIBlock(IGUIBlock block) throws IllegalArgumentException {
        if (!currentBlocks.containsKey(block)) {
            throw new IllegalArgumentException("The given graphical block is not linked to any system block.");
        }
        return currentBlocks.get(block);
    }

    /**
     * Return the graphical block linked to the given system block.
     *
     * @param block the given system block.
     *
     * @return the graphical block linked to the given system block.
     *
     * @throws IllegalArgumentException
     *         when the given graphical block is linked to any graphical block.
     */
    public IGUIBlock getGUIBlockFromBlock(Block block) throws IllegalArgumentException {
        for (Map.Entry<IGUIBlock, Block> entry : currentBlocks.entrySet()) {
            if (Objects.equals(block, entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("The given system block is not linked to any graphical block.");
    }

    /**
     * Link a given graphical and system block together in the database.
     *
     * @param GUIBlock The given graphical block.
     * @param block The given system block.
     *
     * @post the given blocks are added to the block map.
     */
    public void addBlockPair(IGUIBlock GUIBlock, Block block) {
        currentBlocks.put(GUIBlock, block);
    }

    /**
     * Remove a link in the database with a given graphical block.
     *
     * @param block The given graphical block.
     *
     * @post The link between the given graphical block and the
     *       linked system block is removed.
     */
    /**
     * TODO comments
     */
    public void removeBlock(IGUIBlock block) {
        Block systemBlock = getBlockFromGUIBlock(block);
        systemBlock.terminate();
        currentBlocks.remove(block);
    }
}