package System.BlockStructure.Functionality;

import System.GameWorld.Level.Level;

public class NotFunctionality extends ConditionalFunctionality {

    public NotFunctionality(int blockId) {
        super(blockId);
    }

    @Override
    public void evaluate(Level level) {
        ConditionalFunctionality functionality = block.getNext().getFunctionality();
        functionality.evaluate(level);
        setEvaluation(!functionality.getEvaluation());
    }
}
