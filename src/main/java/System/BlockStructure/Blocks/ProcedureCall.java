package System.BlockStructure.Blocks;

import Controllers.BlockListener;
import System.BlockStructure.Functionality.DummyFunctionality;

import java.util.ArrayList;
import java.util.List;

public class ProcedureCall extends FunctionalBlock implements BlockListener {

    private final ProcedureBlock procedure;

    private final List<BlockListener> listeners = new ArrayList<>();

    public ProcedureCall(ProcedureBlock procedure) {
        super(new DummyFunctionality());
        this.procedure = procedure;
    }

    @Override
    public void terminate() {
        super.terminate();
        notifyProcedureDeleted();
    }

    private void notifyProcedureDeleted() {
        for (BlockListener listener : new ArrayList<>(listeners)) {
            listener.onProcedureDeleted();
        }
    }

    @Override
    public boolean hasProperConnections() {
        return !hasNext() || (procedure.hasProperConnections() && (!getSubConnectorAt(0).isConnected() || getSubConnectorAt(0).getConnectedBlock().hasProperConnections()));
    }

    @Override
    public boolean hasNext() {
        if (procedure != null) {
            return !procedure.isTerminated() && !procedure.isPassed();
        }
        return false;
    }

    @Override
    public Block getNext() {
        if (hasNext()) {
            setReturnToOfNext();
            return procedure;
        }
        return getReturnToBlock();
    }

    private void setReturnToOfNext() {
        if (getSubConnectorAt(0).isConnected()) {
            procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
            getSubConnectorAt(0).getConnectedBlock().setReturnToBlock(getReturnToBlock());
        } else {
            if (getReturnToBlock() != procedure.getReturnToBlock()) {
                procedure.setReturnToBlock(getReturnToBlock());
            }
        }
    }

    @Override
    public Block getBlockAtIndex(int index) {

        if (index < 0) {
            return null;
        }
        if (index == 0) {
            return this;
        }
        if (hasNext()) {
            Block backup = procedure.getReturnToBlock();
            procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
            Block toReturn = procedure.getBlockAtIndex(index-1);
            procedure.setReturnToBlock(backup);
            return toReturn;
        }
        return super.getBlockAtIndex(index);
    }

    @Override
    public int getIndexOfBlock(Block block) {

        if (block == null) {
            return -1;
        }
        if (block == this) {
            return 0;
        }
        if (hasNext()) {
            Block backup = procedure.getReturnToBlock();
            procedure.setReturnToBlock(getSubConnectorAt(0).getConnectedBlock());
            int toReturn = 1 + procedure.getIndexOfBlock(block);
            procedure.setReturnToBlock(backup);
            return toReturn;
        }
        return super.getIndexOfBlock(block);
    }

    @Override
    public Block clone() {
        ProcedureCall toReturn = new ProcedureCall(procedure);
        this.subscribe(toReturn);
        return toReturn;
    }

    public ProcedureBlock getProcedure() {
        return procedure;
    }

    @Override
    public void onProcedureDeleted() {
        this.terminate();
        if (getMainConnector().isConnected()) {
            getMainConnector().disconnect();
        }
        if (getSubConnectorAt(0).isConnected()) {
            getSubConnectorAt(0).getConnectedBlock().getMainConnector().disconnect();
        }
    }

    public void unSubscribe(BlockListener listener) {
        listeners.remove(listener);
    }

    public void subscribe(BlockListener listener) {
        listeners.add(listener);
    }
}
