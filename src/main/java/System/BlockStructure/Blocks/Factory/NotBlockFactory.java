package System.BlockStructure.Blocks.Factory;


import System.BlockStructure.Blocks.NotBlock;

public class NotBlockFactory extends BlockFactory{
    @Override
    public NotBlock CreateBlock() {
        return new NotBlock();
    }
}
