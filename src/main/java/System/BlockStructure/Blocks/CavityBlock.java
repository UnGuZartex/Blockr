package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

public abstract class CavityBlock extends FunctionalBlock {

    private final SubConnector cavitySubConnector;
    private final SubConnector conditionalSubConnector;

    protected <B extends CavityBlock> CavityBlock(int id, ConditionalBlockFunctionality<B> functionality) {
        super(id, functionality);
        functionality.setBlock((B) this);
        cavitySubConnector = new SubConnector(this, Orientation.FACING_DOWN, Type.PLUG);
        conditionalSubConnector = new SubConnector(this, Orientation.FACING_RIGHT, Type.SOCKET);
    }


    public SubConnector getCavitySubConnector() {
        return cavitySubConnector;
    }

    public SubConnector getConditionalSubConnector() {
        return conditionalSubConnector;
    }

    public Block getCondition() {
        return conditionalSubConnector.getConnectedConnector().getBlock();
    }

    @Override
    public boolean hasNext() {
        if (getFunctionality().getEvaluation()) {
            return cavitySubConnector.getConnectedConnector() != null;
        }
        else {
            return getSubConnectors()[0].getConnectedConnector() != null;
        }
    }

    @Override
    public Block getNext() {
        if (getFunctionality().getEvaluation()) {
            return cavitySubConnector.getConnectedBlock();
        }
        else {
            return getSubConnectors()[0].getConnectedBlock();
        }
    }

}
