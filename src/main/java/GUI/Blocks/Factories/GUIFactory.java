package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;

/**
 *  An abstract class representing a factory for the creation of blocks.
 *
 * @author Alpha-team
 */
public abstract class GUIFactory {
    /**
     * Abstract method a GUIBlockFactory needs to implement to create a new GUIBlock.
     *
     * @param id The id for the block to create.
     * @param x The x coordinate for the block to create.
     * @param y The y coordinate for the block to create.
     *
     * @return a new GUIBlock with given id and coordinates.
     */
    public abstract GUIBlock createBlock(String id, int x, int y);
}

