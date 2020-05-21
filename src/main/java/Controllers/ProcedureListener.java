package Controllers;

import System.BlockStructure.Blocks.ProcedureBlock;

public interface ProcedureListener {
    void onProcedureDeleted(ProcedureBlock block);

    void onProcedureCreated(ProcedureBlock block);
}
