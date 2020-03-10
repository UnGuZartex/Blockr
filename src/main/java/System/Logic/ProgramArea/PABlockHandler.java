package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.Palette.PaletteState;
import System.Logic.ProgramArea.ConnectionHandler;
import System.Logic.ProgramArea.ProgramArea;

public class PABlockHandler {
    private PaletteState palette;
    private ProgramArea PA;
    private ConnectionHandler connectionHandler = new ConnectionHandler();

    public void addToPA(Block block) {
        PA.addProgram(block);
    }

    public void connectToExistingBlock(Block block, SubConnector subConnector) {
        connectionHandler.connect(block, subConnector);
    }



    public void deleteBlock(Block block) {
        PA.deleteProgram(block);
    }
}
