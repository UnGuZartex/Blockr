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
     */
    public IfBlock(CavityFunctionality cavityFunctionality) {
        super(cavityFunctionality);
    }

    /**
     * TODO documentation
     *
     * @return
     */
    @Override
    public Block getNextIfNone() {
        if (hasAlreadyRan()) {
            return super.getNextIfNone();
        }
        return getNext();
    }


}
