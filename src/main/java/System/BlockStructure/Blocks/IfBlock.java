package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.CavityFunctionality;

/**
 * A class for if blocks. These are cavity blocks which have a
 * cavity functionality. The cavity can only be ran time.
 *
 * @author Alpha-team
 */
public class IfBlock extends CavityBlock {

    /**
     * Initialise a new if block  with given cavity functionality.
     *
     * @param cavityFunctionality The functionality for this if block.
     *
     * @effect Calls super constructor with given functionality.
     */
    public IfBlock(CavityFunctionality cavityFunctionality) {
        super(cavityFunctionality);
    }

    /**
     * Returns the next block if any other block didn't have a next block to run.
     *
     * @return if this block has already ran all of its connectors then return the closest
     *         cavity. Otherwise return the next block to run.
     */
    @Override
    public Block getNextIfNone() {
        if (hasAlreadyRan()) {
            return super.getNextIfNone();
        }
        return getSubConnectorAt(0).getConnectedBlock();
    }


}
