package System.BlockStructure.Functionality;

import System.GameWorld.CellType;
import System.GameWorld.Level.Level;

public class WallInFrontFunctionality extends BlockFunctionality {

    @Override
    public void evaluate(Level level) {
        evaluation = (level.getTypeForward() == CellType.WALL);
    }
}
