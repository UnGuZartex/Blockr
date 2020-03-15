package GUI.Blocks.Factories;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;

/**
 * A factory to create if GUI blocks.
 *
 * @author Alpha-team
 */
public class IfGUIFactory extends GUIFactory {

    /**
     * Variable referring to the name of an if block.
     */
    private static final String name = "If";

    /**
     * Create a new if block with given id and coordinates.
     *
     * @param id The id for the block to create.
     * @param x The x coordinate for the block to create.
     * @param y The y coordinate for the block to create.
     *
     * @return A new cavity block block with given id and coordinates and the correct name.
     */
    @Override
    public GUIBlock createBlock(String id, int x, int y) {
        return new GUICavityBlock(name, id, x, y);
    }
}
