package BlockStructure.Functionality;

import BlockStructure.Blocks.Block;
import GameWorld.CellType;
import GameWorld.Level.Level;

public class WallInFrontFunctionality extends ConditionalFunctionality {

    @Override
    public void evaluate(Block<?> block, Level level) {
        setEvaluation(level.getTypeForward() == CellType.WALL);
    }
}
