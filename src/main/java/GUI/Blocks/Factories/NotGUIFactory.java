package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIOperatorBlock;

/**
 * A factory to create new not GUI blocks.
 *
 * @author Alpha-team
 */
public class NotGUIFactory extends GUIFactory {

    /**
     * Variable referring to the name of a not block.
     */
    private static final String name = "Not";

    /**
     * Create a new not block with given id and coordinates.
     *
     * @param id The id for the block to create.
     * @param x The x coordinate for the block to create.
     * @param y The y coordinate for the block to create.
     *
     * @return A new operator block with given id and coordinates and the correct name.
     */
    @Override
    public GUIBlock createBlock(String id, int x, int y) {
        return new GUIOperatorBlock(name, id, x,y);
    }
}
