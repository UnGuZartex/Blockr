package Controllers;

/**
 * An interface used as a listener for receiving program area state updates.
 *
 * @author Alpha-team
 */
public interface ProgramAreaListener {

    /**
     * This event is called when the program area has either reached its max blocks, or
     * the current amount of blocks is under the max blocks again.
     *
     * @param reachedMaxBlocks the given boolean indicating if the number of max blocks in the program area is reached.
     */
    void onMaxBlocksReached(boolean reachedMaxBlocks);

    void onProcedureCreated();

    void onProcedureDeleted(int index);
}
