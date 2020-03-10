package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.TurnRightBlock;

public class TurnRightBlockFactory extends BlockFactory{


    @Override
    public TurnRightBlock CreateBlock() {
        return new TurnRightBlock();
    }
}
