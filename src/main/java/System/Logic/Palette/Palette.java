package System.Logic.Palette;

import System.BlockStructure.Blocks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class to keep track of the palette state.
 *
 * @author Alpha-team
 */
public class Palette {

    /**
     * Variable referring to the blocks in the palette.
     */
    private final List<Block> paletteBlocks = new ArrayList<>();

    /**
     * Initialise a new palette state
     *
     * @effect Add all different kind of factories the hashmap.
     */
    // TODO LIJST
    public Palette() {
        paletteBlocks.add(new IfBlock());
        paletteBlocks.add(new WhileBlock());
        paletteBlocks.add(new NotBlock());
        paletteBlocks.add(new WallInFrontBlock());
        paletteBlocks.add(new MoveForwardBlock());
        paletteBlocks.add(new TurnLeftBlock());
        paletteBlocks.add(new TurnLeftBlock());
    }

    /**
     * Get a new block with the given index.
     *
     * @param index The index of the block in the palette list to create and return.
     *
     * @return The block at the given index
     */
    public Block getNewBlock(int index) {

        if (index < 0 || index >= paletteBlocks.size()) {
            throw new IndexOutOfBoundsException("The given index for the block to choose is out of bounds!");
        }

        return paletteBlocks.get(index).clone();
    }
}