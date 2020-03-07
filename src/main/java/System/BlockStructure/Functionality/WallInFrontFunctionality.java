package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;
import System.GameWorld.CellType;
import System.GameWorld.Level.Level;

public class WallInFrontFunctionality extends ConditionalFunctionality {

    @Override
    public void evaluate(Block block, Level level) {
        setEvaluation(level.getTypeForward() == CellType.WALL);
    }
}
