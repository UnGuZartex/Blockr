package System.BlockStructure.Functionality;

import System.GameWorld.CellType;
import System.GameWorld.Level.Level;

public class WallInFrontFunctionality extends ConditionalFunctionality {

    @Override
    public void evaluate(Level level) {
        setEvaluation(level.getTypeForward() == CellType.WALL);
    }
}
