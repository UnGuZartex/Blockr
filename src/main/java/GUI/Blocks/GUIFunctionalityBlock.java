package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

/**
 * A class for functional GUI blocks.
 *
 * @author Alpha-team
 */
public class GUIFunctionalityBlock extends GUIBlock {

    /**
     * Variables referring to the default width and height of a block.
     */
    public static final int DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 50;
    /**
     * Variable referring to the lower sub connector of this
     */
    private GUIConnector lowerSubConnector;

    /**
     * Initialise a new functionality block with given name, id and coordinates
     *
     * @param name The name for this block.
     * @param id The id for this block.
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     *
     * @effect Calls super constructor with given parameters.
     */
    public GUIFunctionalityBlock(String name, String id, int x, int y) {
        super(name, id, x, y);
    }

    // TODO doc
    @Override
    protected void changeHeight(int heightDelta, GUIBlock previousBlock) {
        if (mainConnector.isConnected()) {
            mainConnector.getConnectedGUIBlock().changeHeight(heightDelta, this);
        }
    }

    // TODO doc
    @Override
    public int getHeight() {

        if (lowerSubConnector.isConnected()) {
            return height + lowerSubConnector.getConnectedGUIBlock().getHeight();
        }

        return height;
    }

    /**
     * Set the shapes of this functional block.
     *
     * @post The height of this block is set to the default height.
     * @post The width of this block is set  to the default width.
     *
     * @effect A new collision rectangle is added to the rectangles of this block.
     * @effect A new main connector is initialised for this block.
     * @effect A new lower sub connector is initialised for this block and added
     *         to the list of sub connectors of this block.
     */
    @Override
    protected void setShapes() {
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, Color.white));
        mainConnector = new GUIConnector("MAIN", this, width / 2, 0, Color.blue);
        lowerSubConnector = new GUIConnector( "SUB_1", this, width / 2, height, Color.red);
        subConnectors.add(lowerSubConnector);
    }
}
