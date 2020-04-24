package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import System.BlockStructure.Blocks.Block;

/**
 * An abstract class for conditional functionality's.
 *
 * @param <B> The block to which this condition functionality is connected.
 *
 * @author Alpha-team
 */
public abstract class ConditionalBlockFunctionality<B extends Block> extends BlockFunctionality {

    /**
     * Variable referring to the block to which this functionality is connected.
     */
    protected B block;

    /**
     * Initialise a new block functionality
     *
     * @param gameWorld the game world this functionality is linked to
     */
    public ConditionalBlockFunctionality(GameWorld gameWorld) {
        super(gameWorld);
    }

    /**
     * Set the block of this functionality to the given block, only if the block
     * of this functionality is existing.
     *
     * @param block The block to assign to this functionality.
     *
     * @post The block of this functionality is set to the given block if it was
     *       not effective yet.
     */
    public void setBlock(B block) {
        if (this.block == null) {
            this.block = block;
        }
    }
}
