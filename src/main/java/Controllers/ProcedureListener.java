package Controllers;

import System.BlockStructure.Blocks.ProcedureBlock;

/**
 * An interface for listeners to a procedure.
 *
 * @author Alpha-team
 */
public interface ProcedureListener {

    /**
     * Event to call when a procedure is deleted.
     *
     * @param block The procedure which is deleted.
     */
    void onProcedureDeleted(ProcedureBlock block);

    /**
     * Event to call when a procedure is created.
     *
     * @param block The procedure which is created.
     */
    void onProcedureCreated(ProcedureBlock block);
}
