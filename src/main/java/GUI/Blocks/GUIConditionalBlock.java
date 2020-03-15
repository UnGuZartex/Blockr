package GUI.Blocks;

import GUI.CollisionShapes.CollisionRectangle;
import GUI.Components.GUIConnector;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class for conditional GUI blocks.
 *
 * @author Alpha-team
 */
public class GUIConditionalBlock extends GUIBlock {

    /**
     * Variables referring to the default with and height.
     */
    public static final int DEFAULT_HEIGHT = 30, DEFAULT_WIDTH = 100;
    /**
     * Variable referring to the color of
     */
    public static final Color DEFAULT_COLOR = Color.WHITE;

    /**
     * Initialise a new conditional block with given id, name and coordinates.
     *
     * @param name The name for this block.
     * @param id The id for this block.
     * @param x The x coordinate for this block.
     * @param y The y coordinate for this block.
     *
     * @effect Calls the super constructor with given parameters.
     */
    public GUIConditionalBlock(String name, String id, int x, int y) {
        super(name, id, x, y);
    }

    /**
     * Change the height of this block.
     *
     * @param heightDelta The height difference.
     * @param previousBlock The previous block.
     *
     * @post Nothing changed.
     */
    @Override
    protected void changeHeight(int heightDelta, GUIBlock previousBlock) { }

    /**
     * Set the shapes of this conditional block.
     *
     * @effect Add a new block at the origin with the correct height, width and default colour.
     * @effect Create a new main connector on the correct position.
     *
     * @post The height is set to the default height.
     * @post The width is set to the default width.
     */
    @Override
    protected void setShapes() {
        height = DEFAULT_HEIGHT;
        width = DEFAULT_WIDTH;
        blockRectangles = new ArrayList<>();
        blockRectangles.add(new CollisionRectangle(0, 0, width, height, DEFAULT_COLOR));
        mainConnector = new GUIConnector("MAIN", this, 0, height / 2, Color.blue);
    }
}
