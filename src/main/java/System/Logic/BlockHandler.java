package System.Logic;

import System.BlockStructure.Blocks.Block;
import System.Logic.Palette.PaletteState;
import System.Logic.ProgramArea.ConnectionHandler;
import System.Logic.ProgramArea.ProgramArea;

public class BlockHandler {
    private PaletteState palette;
    private ProgramArea PA;
    private ConnectionHandler connectionHandler = new ConnectionHandler();

    public void addToPA(int index) {
        Block toAdd = palette.getBlockAt(index);
        PA.addProgram(toAdd);
    }

    public void connectToExistingBlock(int index, Block blockToConnect) {
        Block toAdd = palette.getBlockAt(index);
        //TODO DIT FIXEN HIERZO: connectionHandler.connect(toAdd, blockToConnect);
    }
//
//    public void deleteBlock(Block<?> block) {
//        connectionHandler.disconnect(block);
//        PA.deleteProgram(block);
//
//    }


}
