package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;

/**
 * A class for operation blocks in the GUI.
 *
 * @author Alpha-team
 */
public class GUIOperatorBlock extends GUIBlock {

    /**
     * Variables referring to the default width and height of a block.
     */
    protected static final int DEFAULT_WIDTH = 40, DEFAULT_HEIGHT = 30;
    /**
     * Variable referring to the sub connector of this operator block.
     */
    private GUIConnector subConnector;

    /**
     * Initialise a new operator GUI block with given name and coordinates.
     *
     * @param name The name for this GUI block.
     * @param x The x coordinate for this GUI block.
     * @param y The y coordinate for this GUI block.
     *
     * @effect calls super constructor with the given parameters.
     */
    public GUIOperatorBlock(String name, int x, int y) {
        super(name, x, y);
    }

    /**
     * Set the color of this block.
     *
     * @param color The new color for this block.
     *
     * @effect The color of this block is set to the given color
     * @effect The color of the connected block is set to the given color.
     */
    @Override
    public void setColor(Color color) {
        super.setColor(color);
        if (subConnector.isConnected()) {
            subConnector.getConnectedGUIBlock().setColor(color);
        }
    }

    /**
     * Change the height of this block if possible and affect all connected
     * blocks accordingly.
     *
     * @param heightDelta The given height difference.
     * @param previousBlock The previous block that called this method.
     */
    @Override
    public void changeHeight(int heightDelta, GUIBlock previousBlock) { }

    /**
     * Clone this gui block and return the clone.
     *
     * @return A new GUI operator block with the same name and coordinates as this block.
     */
    @Override
    public GUIBlock clone() {
        return new GUIOperatorBlock(name, x, y);
    }

    /**
     * Set the shapes of this operator block.
     *
     * @post The height of this block is set to the default height.
     * @post The width of this block is set to the default width.
     *
     * @effect A new main connector is initialised.
     * @effect A new sub connector is initialised and added to the
     *         sub connector list.
     */
    @Override
    protected void setShapes() {
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, DEFAULT_BLOCK_COLOR));
        mainConnector = new GUIConnector(this, 0, height / 2, DEFAULT_MAIN_CONNECTOR_COLOR);
        subConnector = new GUIConnector( this, width, height / 2, DEFAULT_SUB_CONNECTOR_COLOR);
        subConnectors.add(subConnector);
    }
}