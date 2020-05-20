package GUI.Blocks;

import Controllers.BlockListener;
import GUI.Components.GUIConnector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GUIProcedureBlock extends GUICavityBlock {

    private int currentDefNr;

    private final List<BlockListener> listeners = new ArrayList<>();

    /**
     * TODO
     * @param name
     * @param x
     * @param y
     */
    public GUIProcedureBlock(String name, int x, int y, int startingNr) {
        super(name, x, y);
        this.currentDefNr = startingNr;
        this.name = name + " " + currentDefNr;
        setFirst = true;
    }


    @Override
    protected void setConnectors() {
        cavityConnector = new GUIConnector(this, (width + DEFAULT_CAVITY_WIDTH) / 2, cavityUpHeight, Color.red);
        subConnectors.add(cavityConnector);
    }

    @Override
    public GUIBlock clone() {
        GUIProcedureBlock toReturn =  new GUIProcedureBlock(name.split(" ")[0], x, y, currentDefNr);
        currentDefNr++;
        this.name = name.split(" ")[0] + " " + currentDefNr;
        return toReturn;
    }

    @Override
    public void terminate() {
        super.terminate();
    }


    public void unsubscribe(BlockListener listener) {
        listeners.remove(listener);
    }

    public void subscribe(BlockListener listener) {
        listeners.add(listener);
    }
}
