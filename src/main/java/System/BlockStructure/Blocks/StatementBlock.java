package System.BlockStructure.Blocks;


import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

public class StatementBlock extends ConditionalBlock {

    private final SubConnector[] subConnectors;
    protected <B extends ConditionalBlock> StatementBlock(int id, ConditionalBlockFunctionality<B> functionality) {
        super(id, functionality);
        subConnectors = new SubConnector[] {};

    }

    @Override
    public SubConnector[] getSubConnectors() {
        return subConnectors;
    }

}
