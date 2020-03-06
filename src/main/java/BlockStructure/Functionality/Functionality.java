package BlockStructure.Functionality;

import BlockStructure.Blocks.Block;
import GameWorld.Level.Level;

public interface Functionality {
    void evaluate(Block<?> block, Level level);
}
