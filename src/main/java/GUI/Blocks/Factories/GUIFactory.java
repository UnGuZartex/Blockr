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
     * @return a new GUIBlock
     */
    public abstract GUIBlock createBlock(int x, int y);
}

