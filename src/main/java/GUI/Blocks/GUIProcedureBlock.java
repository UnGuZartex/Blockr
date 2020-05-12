package GUI.Blocks;

import GUI.Components.GUIConnector;

import java.awt.*;

public class GUIProcedureBlock extends GUICavityBlock {

    private int currentDefNr;
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
        GUIBlock toReturn =  new GUIProcedureBlock(name.split(" ")[0], x, y, currentDefNr);
        currentDefNr++;
        this.name = name.split(" ")[0] + " " + currentDefNr;
        return toReturn;
    }
}
