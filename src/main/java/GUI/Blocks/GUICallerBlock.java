package GUI.Blocks;

import Controllers.BlockListener;

import java.util.ArrayList;
import java.util.List;

public class GUICallerBlock extends GUIFunctionalBlock implements BlockListener {

    private int procedureNr;

    private final List<BlockListener> listeners = new ArrayList<>();

    /**
     * Initialise a new functionality block with given name and coordinates
     *
     * @param name The name for this block.
     * @param x    The x coordinate for this block.
     * @param y    The y coordinate for this block.
     * @effect Calls super constructor with given parameters.
     */
    public GUICallerBlock(int x, int y, int procedureNr) {
        super("Call " + procedureNr, x, y);
        this.procedureNr = procedureNr;
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
    public void onProcedureDeleted() {
        terminate();
    }

    public void unsubscribe(BlockListener listener) {
        listeners.remove(listener);
    }

    public void subscribe(BlockListener listener) {
        listeners.add(listener);
    }

    @Override
    public GUIBlock clone() {
        GUICallerBlock toReturn = new GUICallerBlock(x, y, procedureNr);
        this.subscribe(toReturn);
        return toReturn;
    }
}
