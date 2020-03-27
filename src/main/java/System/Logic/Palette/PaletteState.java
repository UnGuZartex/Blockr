package System.Logic.Palette;

import System.BlockStructure.Blocks.*;

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
    private final HashMap<String, Block> allBlocksFactory = new HashMap<>();

    /**
     * Initialise a new palette state and add all different blocks to the
     *
     * @effect Add all different kind of factories the hashmap.
     */
    public PaletteState() {
        allBlocksFactory.put("IF", new IfBlock());
        allBlocksFactory.put("WHILE", new WhileBlock());
        allBlocksFactory.put("NOT", new NotBlock());
        allBlocksFactory.put("WALL IN FRONT", new WallInFrontBlock());
        allBlocksFactory.put("MOVE FORWARD", new MoveForwardBlock());
        allBlocksFactory.put("TURN LEFT", new TurnLeftBlock());
        allBlocksFactory.put("TURN RIGHT", new TurnLeftBlock());
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
            // TODO FIX LELIJKE SWITCH
            switch(ID){
                case "IF": return new IfBlock();
                case "WHILE": return new WhileBlock();
                case "NOT": return new NotBlock();
                case "WALL IN FRONT": return new WallInFrontBlock();
                case "MOVE FORWARD": return new MoveForwardBlock();
                case "TURN LEFT": return new TurnLeftBlock();
                case "TURN RIGHT": return new TurnRightBlock();
                default:  throw new IllegalArgumentException("Invalid ID, choose between: " + allBlocksFactory.keySet());
            }
        }
        return null;
    }
}
