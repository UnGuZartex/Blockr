package System.BlockStructure.Blocks;

import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.Orientation;
import System.BlockStructure.Connectors.SubConnector;
import System.BlockStructure.Connectors.Type;
import System.BlockStructure.Functionality.BlockFunctionality;
import System.BlockStructure.Functionality.ConditionalBlockFunctionality;
import com.sun.tools.javac.Main;

public abstract class ConditionalBlock extends Block {

    protected final MainConnector mainConnector;

    protected <B extends ConditionalBlock> ConditionalBlock(ConditionalBlockFunctionality<B> functionality) {
        super(functionality);
        mainConnector = new MainConnector(this, Orientation.FACING_LEFT, Type.PLUG);
        functionality.setBlock((B) this);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Block getNext() {
        return null;
    }


    @Override
    public MainConnector getMainConnector() {
        return mainConnector;
    }

    @Override
    public Block returnToClosestCavity() {
        return null;
    }

    @Override
    public boolean isValid() {
        if (!getMainConnector().isConnected()) {
            return false;
        }
        return super.isValid();
    }
}
