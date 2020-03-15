package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIFunctionalityBlock;

/**
 * Factory to create new turn right GUI block.
 *
 * @author Alpha-team
 */
public class TurnRightGUIFactory extends GUIFactory{

    /**
     * Variable referring to the name of a turn right block.
     */
    private static final String name = "Turn Right";

    /**
     * Create a new turn right block with given id and coordinates.
     *
     * @param id The id for the block to create.
     * @param x The x coordinate for the block to create.
     * @param y The y coordinate for the block to create.
     *
     * @return A new functional block with given id and coordinates and the correct name.
     */
    @Override
    public GUIBlock createBlock(String id, int x, int y) {
        return new GUIFunctionalityBlock(name, id, x,y);
    }
}
