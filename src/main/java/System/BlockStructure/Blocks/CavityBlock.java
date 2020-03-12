package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;

public abstract class CavityBlock extends FunctionalBlock {

    private final SubConnector cavitySubConnector;
    private final SubConnector conditionalSubConnector;

    protected <B extends CavityBlock> CavityBlock(ConditionalBlockFunctionality<B> functionality) {
        super(functionality);
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
        return conditionalSubConnector.getConnectedBlock();
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
            setAlreadyRan(false);
            return cavitySubConnector.getConnectedBlock();
        }
        else {
            setAlreadyRan(true);
            cavitySubConnector.getConnectedBlock().reset();
            return getSubConnectors()[0].getConnectedBlock();
        }
    }

    @Override
    public boolean isValid() {
        if (cavitySubConnector.isConnected()) {
            if (getSubConnectors()[0].isConnected()) {
                if (getConditionalSubConnector().isConnected()) {
                    return cavitySubConnector.getConnectedBlock().isValid()
                            && getSubConnectors()[0].getConnectedBlock().isValid()
                            && getConditionalSubConnector().getConnectedBlock().isValid();
                }
                else {
                    return false;
                }
            }
        }
        return true;

    }
}
