package System.Logic.Palette;

import System.BlockStructure.Blocks.*;
import java.util.List;

/**
 * A class to keep track of the palette.
 *
 * @author Alpha-team
 */
public class Palette {

    /**
     * Variable referring to the blocks in the palette.
     */
    private final List<Block> paletteBlocks;

    /**
     * Create a new palette with the given blocks as available palette blocks.
     *
     * @param paletteBlocks The given list of blocks.
     *
     * @post the palette blocks are set to the given list of blocks.
     *
     * @throws IllegalArgumentException
     *         when the given palette block list is null.
     */
    public Palette (List<Block> paletteBlocks) throws IllegalArgumentException {

        if (paletteBlocks == null) {
            throw new IllegalArgumentException("The given palette block list is null.");
        }

        this.paletteBlocks = paletteBlocks;
    }

    /**
     * Get a new block of the same type as the palette block at the given index in the palette.
     *
     * @param index The index of the block in the palette list to create and return.
     *
     * @return The block at the given index
     *
     * @throws IndexOutOfBoundsException
     *         when the given index for the block to choose is out of bounds.
     */
    public Block getNewBlock(int index) throws IndexOutOfBoundsException {

        if (index < 0 || index >= paletteBlocks.size()) {
            throw new IndexOutOfBoundsException("The given index for the block to choose is out of bounds!");
        }

        return paletteBlocks.get(index).clone();
    }
}