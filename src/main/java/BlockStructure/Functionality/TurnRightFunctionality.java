package BlockStructure.Functionality;

import BlockStructure.Blocks.Block;
import GameWorld.Level.*;

public class TurnRightFunctionality implements Functionality {

    @Override
    public void evaluate(Block block, Level level) {
        level.getRobot().turnRight();
    }
}
