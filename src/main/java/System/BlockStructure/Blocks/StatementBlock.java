package System.BlockStructure.Blocks;


import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

public class StatementBlock extends ConditionalBlock {

    public <B extends ConditionalBlock> StatementBlock(ConditionalBlockFunctionality<B> functionality) {
        super(functionality);
    }


}
