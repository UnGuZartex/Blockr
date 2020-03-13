package System.BlockStructure.Functionality;

import System.BlockStructure.Blocks.CavityBlock;
import System.BlockStructure.Blocks.IfBlock;
import System.GameWorld.Level.Level;

public class CavityFunctionality extends ConditionalBlockFunctionality<CavityBlock> {


    @Override
    public void evaluate(Level level) {
        try {
            BlockFunctionality functionality = block.getCondition().getFunctionality();
            functionality.evaluate(level);
            evaluation = functionality.getEvaluation();
        } catch (NullPointerException e) {
            evaluation = false;
        }
    }

}
