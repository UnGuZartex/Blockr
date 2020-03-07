package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.TurnRightBlock;
import System.GameWorld.Level.Level;

public class TurnRightFunctionality extends BlockFunctionality<TurnRightBlock> {

    @Override
    public void evaluate(Level level) {
        level.getRobot().turnRight();
    }
}
