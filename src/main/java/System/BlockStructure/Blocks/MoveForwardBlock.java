package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Functionality.MoveForwardFunctionality;

public class MoveForwardBlock extends FunctionalBlock {
    public MoveForwardBlock() {
        super(new MoveForwardFunctionality());
    }
}
