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
    public static final int DEFAULT_WIDTH = 40, DEFAULT_HEIGHT = 30;
    /**
     * Variable referring to the sub connector of this operator block.
     */
    private GUIConnector subConnector;

    /**
     * Initialise a new operator GUI block with given name, id and coordinates.
     *
     * @param name The name for this GUI block.
     * @param id The id for this GUI block.
     * @param x The x coordinate for this GUI block.
     * @param y The y coordinate for this GUI block.
     *
     * @effect calls super constructor with the given parameters.
     */
    public GUIOperatorBlock(String name, String id, int x, int y) {
        super(name, id, x, y);
    }

    /**
     * Set the color of this block.
     *
     * @param color The new color for this block.
     *
     * @effect The color of the connected block is also set to the given color.
     */
    @Override
    public void setColor(Color color) {
        super.setColor(color);

        if (subConnector.isConnected()) {
            subConnector.getConnectedGUIBlock().setColor(color);
        }
    }

    /**
     * Change the height of this operator block.
     *
     * @param heightDelta The height difference.
     * @param previousBlock The previous block.
     *
     * @post Nothing changes.
     */
    @Override
    protected void changeHeight(int heightDelta, GUIBlock previousBlock) { }

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

        blockRectangles.add(new CollisionRectangle(0, 0, width, height, Color.white));
        mainConnector = new GUIConnector("MAIN",this, 0, height / 2, Color.blue);
        subConnector = new GUIConnector("SUB_1", this, width, height / 2, Color.red);
        subConnectors.add(subConnector);
    }
}
