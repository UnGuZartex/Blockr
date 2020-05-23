package Controllers;

public interface PaletteListener {
    void procedureDeleted(int index);

    void procedureCreated();

    /**
     * This event is called when the program area has either reached its max blocks, or
     * the current amount of blocks is under the max blocks again.
     *
     * @param reachedMaxBlocks the given boolean indicating if the number of max blocks in the program area is reached.
     */
    void onMaxBlocksReached(boolean reachedMaxBlocks);
}
