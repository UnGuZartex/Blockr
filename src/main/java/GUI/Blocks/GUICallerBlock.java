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
    public GUICallerBlock(String name, int x, int y) {
        super(name, x, y);
        procedureNr = Integer.parseInt(name.split(" ")[1]);
        this.name = name.split(" ")[0] + " " + procedureNr;
    }

    @Override
    public void terminate() {
        super.terminate();
        notifyProcedureDeleted();
    }

    public void notifyProcedureDeleted() {
        for (BlockListener listener : new ArrayList<>(listeners)) {
            listener.onEvent("ProcedureDel");
        }
    }

    public void notifyUpdated() {
        for (BlockListener listener : new ArrayList<>(listeners)) {
            listener.onEvent("Updated");
        }
    }

    @Override
    public void onEvent(String Event) {
        switch(Event) {
            case "ProcedureDel":
                deleteProcedure();
                break;
            case "Updated":
                update();
                break;
            default:
        }

    }

    private void deleteProcedure() {
        this.mainConnector.disconnect();
        this.subConnectors.get(0).disconnect();
    }

    public void unSubscribe(BlockListener listener) {
        listeners.remove(listener);
    }

    public void subscribe(BlockListener listener) {
        listeners.add(listener);
    }

    @Override
    public GUIBlock clone() {
        GUICallerBlock toReturn = new GUICallerBlock(this.name, x, y);
        this.subscribe(toReturn);
        return toReturn;
    }

    public void update() {
        procedureNr--;
        this.name = name.split(" ")[0] + " " + procedureNr;
    }
}
