package System.BlockStructure.Functionality;

import System.GameWorld.Level.Level;

public abstract class BlockFunctionality implements IFunctionality {

    protected boolean evaluation;

    public boolean getEvaluation() {
        return evaluation;
    }

    @Override
    public abstract void evaluate(Level level);

    public void reset(){
        evaluation = false;
    }
}
