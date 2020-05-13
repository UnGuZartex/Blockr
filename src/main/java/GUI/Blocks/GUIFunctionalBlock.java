package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

/**
 * A class for functional GUI blocks.
 *
 * @author Alpha-team
 */
public class GUIFunctionalBlock extends GUIBlock {

    /**
     * Variables referring to the default width and height of a block.
     */
    public static final int DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 50;
    /**
     * Variable referring to the lower sub connector of this
     */
    private GUIConnector lowerSubConnector;

    /**
     * Initialise a new functionality block with given name and coordinates
     *
     * @param name The name for this block.
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     *
     * @effect Calls super constructor with given parameters.
     */
    public GUIFunctionalBlock(String name, int x, int y) {
        super(name, x, y);
    }

    /**
     * Change the height of this block if possible and affect all connected
     * blocks accordingly.
     *
     * @param heightDelta The given height difference.
     * @param previousBlock The previous block that called this method.
     *
     * @effect If the main connector is connected to a block, this method is called on that connected block to
     *         further propagate the height change call.
     */
    @Override
    protected void changeHeight(int heightDelta, GUIBlock previousBlock) {
        if (mainConnector.isConnected()) {
            mainConnector.getConnectedGUIBlock().changeHeight(heightDelta, this);
        }
    }

    /**
     * Calculate the total height of the block structure this block is connected to, starting
     * from this block.
     *
     * @return the height of this block if its lower sub connector is not connected to anything,
     *         return this height + the additional height of its connected block otherwise.
     */
    @Override
    public int getTotalHeight() {

        if (lowerSubConnector.isConnected()) {
            return height + lowerSubConnector.getConnectedGUIBlock().getTotalHeight();
        }

        return height;
    }

    /**
     * Set the shapes of this functional block.
     *
     * @post The height of this block is set to the default height.
     * @post The width of this block is set to the default width.
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
        mainConnector = new GUIConnector(this, width / 2, 0, Color.blue);
        lowerSubConnector = new GUIConnector( this, width / 2, height, Color.red);
        subConnectors.add(lowerSubConnector);
    }

    /**
     * Clone this gui block and return the clone.
     *
     * @return A clone of this gui block.
     */
    @Override
    public GUIBlock clone() {
        return new GUIFunctionalBlock(name, x, y);
    }
}
