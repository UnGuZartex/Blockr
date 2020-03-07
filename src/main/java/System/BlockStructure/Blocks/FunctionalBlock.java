package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.IFunctionality;

public class FunctionalBlock extends BasicBlock<IFunctionality> {
    public FunctionalBlock(int id, IFunctionality functionality) {
        super(id, functionality);
    }
}
