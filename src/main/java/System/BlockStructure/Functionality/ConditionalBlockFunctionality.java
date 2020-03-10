package System.BlockStructure.Functionality;

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
     * Set the block of this functionality to the given block, only if the block
     * of this functionality is existing.
     *
     * @param block The block to assign to this functionality.
     */
    public void setBlock(B block) {
        if (this.block == null) {
            this.block = block;
        }
    }
}
