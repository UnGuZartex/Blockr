package BlockStructure.Functionality;

import BlockStructure.Blocks.Block;
import GameWorld.Level.Level;

public class MoveForwardFunctionality implements Functionality {

    @Override
    public void evaluate(Block<?> block, Level level) {
        if (level.canMoveForward()) {
            level.getRobot().moveForward();
        }
    }
}
