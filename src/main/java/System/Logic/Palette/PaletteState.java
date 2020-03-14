package System.Logic.Palette;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.Factory.*;
import java.util.HashMap;

/**
 * A class to keep track of the palette state.
 *
 * @author Alpha-team
 */
public class PaletteState {

    /**
     * Variable referring to the all block ID's and their factory.
     */
    private final HashMap<String, BlockFactory> allBlocksFactory = new HashMap<>();

    /**
     * Initialise a new palette state and add all different blocks to the
     */
    public PaletteState() {
        allBlocksFactory.put("IF", new IfBlockFactory());
        allBlocksFactory.put("WHILE", new WhileBlockFactory());
        allBlocksFactory.put("NOT", new NotBlockFactory());
        allBlocksFactory.put("WALL IN FRONT", new WallInFrontBlockFactory());
        allBlocksFactory.put("MOVE FORWARD", new MoveForwardBlockFactory());
        allBlocksFactory.put("TURN LEFT", new TurnLeftBlockFactory());
        allBlocksFactory.put("TURN RIGHT", new TurnRightBlockFactory());
    }

    /**
     * Get a new block with the given ID.
     *
     * @param ID The id of the block to create and return.
     *
     * @return A new block of the given id type.
     */
    public Block getNewBlockWithID(String ID) {
        if (allBlocksFactory.containsKey(ID)) {
            return allBlocksFactory.get(ID).createBlock();
        }
        else {
            throw new IllegalArgumentException("Invalid ID, choose between: " + allBlocksFactory.keySet());
        }
    }
}
