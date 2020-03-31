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
     *
     * @effect Calls super constructor with given functionality.
     */
    public IfBlock() {
        super(new CavityFunctionality());
    }

    @Override
    protected Block getNewReturnBlock() {
        if (getSubConnectorAt(0).isConnected()) {
            return getSubConnectorAt(0).getConnectedBlock();
        }
        return getReturnToBlock();
    }

    @Override
    public Block clone() {
        return new IfBlock();
    }
}
