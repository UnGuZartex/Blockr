package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.Block;
import System.GameWorld.Level.*;

public class TurnRightFunctionality implements Functionality {

    @Override
    public void evaluate(Block block, Level level) {
        level.getRobot().turnRight();
    }
}
