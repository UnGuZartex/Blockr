package System.Logic.Palette;

import System.BlockStructure.Blocks.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to keep track of the palette.
 *
 * @invar The blocks in the palette must be valid at all time.
 *        | isValidBlocks(paletteBlocks)
 *
 * @author Alpha-team
 */
public class Palette {

    /**
     * Variable referring to the blocks in the palette.
     */
    private final List<Block> paletteBlocks;

    private final List<ProcedureCall> procedureCallList = new ArrayList<>();

    /**
     * Create a new palette with the given blocks as available palette blocks.
     *
     * @param paletteBlocks The given list of blocks.
     *
     * @post the palette blocks are set to the given list of blocks.
     *
     * @throws IllegalArgumentException
     *         If the given blocks are not valid.
     */
    public Palette(List<Block> paletteBlocks) throws IllegalArgumentException {
        if (!isValidBlockList(paletteBlocks)) {
            throw new IllegalArgumentException("The given palette blocks are invalid!");
        }
        this.paletteBlocks = paletteBlocks;
    }

    /**
     * Checks whether the given blocks are valid for a palette.
     *
     * @param blocks The blocks to check.
     *
     * @return True if and only if the given blocks are effective, there is at
     *         least one block in the list and it doesn't contain null.
     */
    public static boolean isValidBlockList(List<Block> blocks) {
        return blocks != null && blocks.size() > 0 && !blocks.contains(null);
    }

    /**
     * Get a new block of the same type as the palette block at the given index in the palette.
     *
     * @param index The index of the block in the palette list to create and return.
     *
     * @return A clone of the block at the given index
     *
     * @throws IndexOutOfBoundsException
     *         when the given index for the block to choose is out of bounds.
     */
    public Block getNewBlock(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= (paletteBlocks.size() + procedureCallList.size())) {
            throw new IndexOutOfBoundsException("The given index for the block to choose is out of bounds!");
        }
        if (index >= paletteBlocks.size()) {
            index %= paletteBlocks.size();
            return procedureCallList.get(index);
        }
        return paletteBlocks.get(index).clone();
    }

    public void createCaller(ProcedureBlock lastProcedure) {
        procedureCallList.add(new ProcedureCall(lastProcedure));
    }

    public int deleteCaller(ProcedureBlock lastProcedure) {
        int index = 0;
        for (ProcedureCall call : procedureCallList) {
            if (call.getProcedure() == lastProcedure) {
                break;
            }
            index++;
        }
        procedureCallList.remove(index);
        return index + paletteBlocks.size();
    }
}