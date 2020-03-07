package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.WallInFrontBlock;
import System.GameWorld.CellType;
import System.GameWorld.Level.Level;

public class WallInFrontFunctionality extends BlockFunctionality<WallInFrontBlock> {

    @Override
    public void evaluate(Level level) {
        evaluation = (level.getTypeForward() == CellType.WALL);
    }
}
