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
    private final List<Block> paletteBlocks;

    /**
     * TODO descr
     */
    public Palette (List<Block> paletteBlocks) {
        this.paletteBlocks = paletteBlocks;
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