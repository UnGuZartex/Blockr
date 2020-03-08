package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;

public abstract class ConditionalBlockFunctionality<B extends Block> extends BlockFunctionality {

    protected B block;

    public void setBlock(B block) {
        if (this.block == null) {
            this.block = block;
        }
    }
}
