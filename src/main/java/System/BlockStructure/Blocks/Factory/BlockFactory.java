package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.*;

/**
 *  An abstract class representing a factory for the creation of blocks.
 *
 * @author Alpha-team
 */
public abstract class BlockFactory {

    /**
     * Abstract method a BlockFactory needs to implement to create a new Block.
     *
     * @return a new Block that has the correct properties for this type of Block
     */
    public abstract Block createBlock();

}
