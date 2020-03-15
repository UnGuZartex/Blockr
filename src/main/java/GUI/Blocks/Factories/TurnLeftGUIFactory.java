package GUI.Blocks.Factories;

import GUI.Blocks.GUIFunctionalityBlock;

/**
 * A factory to create turn left GUI blocks.
 *
 * @author Alpha-team
 */
public class TurnLeftGUIFactory extends GUIFactory {

    /**
     * Variable referring to the name of an turn left block.
     */
    private static final String name = "Turn Left";

    /**
     * Create a new turn left block with given id and coordinates.
     *
     * @param id The id for the block to create.
     * @param x The x coordinate for the block to create.
     * @param y The y coordinate for the block to create.
     *
     * @return A new functional block with given id and coordinates and the correct name.
     */
    @Override
    public GUIFunctionalityBlock createBlock(String id, int x, int y) {
        return new GUIFunctionalityBlock(name, id, x, y);
    }
}
