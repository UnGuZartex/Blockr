package Controllers;

/**
 * An interface for listeners to a palette.
 *
 * @author Alpha-team
 */
public interface PaletteListener {

    /**
     * Event to call when a procedure is deleted.
     *
     * @param index The index of the call block in the palette of the deleted procedure.
     */
    void procedureDeleted(int index);

    /**
     * Event to call when a procedure is created.
     */
    void procedureCreated();

    /**
     * This event is called when the program area has either reached its max blocks, or
     * the current amount of blocks is under the max blocks again.
     *
     * @param reachedMaxBlocks the given boolean indicating if the number of max blocks in the program area is reached.
     */
    void onMaxBlocksReached(boolean reachedMaxBlocks);
}
