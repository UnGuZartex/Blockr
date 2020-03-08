package System.BlockStructure.Blocks;


import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

public class StatementBlock extends ConditionalBlock {

    private final SubConnector[] subConnectors;
    protected <B extends ConditionalBlock> StatementBlock(int id, ConditionalBlockFunctionality<B> functionality) {
        super(id, functionality);
        subConnectors = new SubConnector[] {new SubConnector(this, Orientation.FACING_RIGHT, Type.SOCKET)};

    }

    @Override
    public SubConnector[] getSubConnectors() {
        return subConnectors;
    }
}
