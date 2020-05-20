package Controllers;

import System.BlockStructure.Blocks.ProcedureBlock;

public interface ProcedureListener {
    void procedureDeleted(ProcedureBlock block);

    void procedureCreated(ProcedureBlock block);
}
