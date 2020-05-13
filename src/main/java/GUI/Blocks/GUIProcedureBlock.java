package GUI.Blocks;

import Controllers.BlockListener;
import GUI.Components.GUIConnector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIProcedureBlock extends GUICavityBlock implements BlockListener {

    private int currentDefNr;

    private final List<BlockListener> listeners = new ArrayList<>();

    private GUIProcedureBlock previousClone;


    /**
     * Initialise a new cavity block with given name and coordinates.
     *
     * @param name The name for this procedure block.
     * @param x    The x coordinate for this block.
     * @param y    The y coordinate for this block.
     * @effect Calls the super constructor with given parameters.
     */
    public GUIProcedureBlock(String name, int x, int y, int startingNr) {
        super(name, x, y);
        this.currentDefNr = startingNr;
        this.name = name + " " + currentDefNr;
    }


    @Override
    protected void setConnectors() {
        cavityConnector = new GUIConnector(this, (width + DEFAULT_CAVITY_WIDTH) / 2, cavityUpHeight, Color.red);
        subConnectors.add(cavityConnector);
    }

    @Override
    public GUIBlock clone() {
        GUIProcedureBlock toReturn =  new GUIProcedureBlock(name.split(" ")[0], x, y, currentDefNr);
        toReturn.subscribe(this);
        if (previousClone != null) {
            previousClone.unSubscribe(this);
            previousClone.subscribe(toReturn);
        }
        previousClone = toReturn;
        currentDefNr++;
        this.name = name.split(" ")[0] + " " + currentDefNr;
        return toReturn;
    }

    @Override
    public void terminate() {
        super.terminate();
        notifyProcedureDeleted();
    }

    private void notifyProcedureDeleted() {
        for (BlockListener listener : new ArrayList<>(listeners)) {
            listener.onEvent("PrcedureDel");
        }
    }

    public void onProcedureDeleted() {
        currentDefNr--;
        this.name = name.split(" ")[0] + " " + currentDefNr;
        notifyProcedureDeleted();
    }

    public void unSubscribe(BlockListener listener) {
        listeners.remove(listener);
    }

    public void subscribe(BlockListener listener) {
        listeners.add(listener);
    }


    @Override
    public void onEvent(String Event) {
        if (Event.equals("ProcedureDel")) {
            onProcedureDeleted();
        }
    }
}
