package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUIConditionalBlock;

/**
 * Factory to create wall in front gui blocks.
 *
 * @author Alpha-team
 */
public class WallInFrontGUIFactory extends GUIFactory{

    /**
     * Variable referring to the name of an wall in front block.
     */
    private static final String name = "Wall In Front";

    /**
     * Create a new wall in front block with given id and coordinates.
     *
     * @param id The id for the block to create.
     * @param x The x coordinate for the block to create.
     * @param y The y coordinate for the block to create.
     *
     * @return A new conditional block with given coordinates and the correct name.
     */
    @Override
    public GUIBlock createBlock(String id, int x, int y) {
        return new GUIConditionalBlock(name, id, x,y);
    }
}
