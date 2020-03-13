package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;
import System.GameState.GameState;
import System.Logic.Palette.PaletteState;

public class PABlockHandler {
    private PaletteState palette = new PaletteState();
    private ProgramArea PA = new ProgramArea();
    private ConnectionHandler connectionHandler = new ConnectionHandler();
    private int amountOfBlocks = 0;

    public Block getFromPalette(String ID) {
        if (!getMaxReached()) {
            return palette.getBlockAt(ID);
        }
        else {
            return null;
        }
    }

    public void addToPA(Block block) {
        PA.addProgram(block);
        Update();
    }

    public void connectToExistingBlock(Block block, SubConnector subConnector) {
        connectionHandler.connect(block, subConnector);
        Update();
    }

    public void disconnectInPA(Block block) {
        connectionHandler.disconnect(block);
        Update();
    }

    public void deleteBlock(Block block) {
        PA.deleteProgram(block);
        Update();
    }

    private void Update() {
        amountOfBlocks = PA.getAllBlocksCount();
    }

    public ProgramArea getPA() {
        return PA;
    }

    public boolean getMaxReached() {
        return amountOfBlocks >= GameState.getMaxAmountOfBlocks();
    }
}
