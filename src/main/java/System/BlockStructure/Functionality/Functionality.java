package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;
import System.GameWorld.Level.Level;

public interface Functionality {
    void evaluate(Block<?> block, Level level);
}
