package Controllers.ControllerClasses;


import Controllers.blockLinkDatabase;
import GUI.Blocks.GUIBlock;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.ProgramArea.PABlockHandler;

public class ConnectionController {

    private final blockLinkDatabase converter;
    private final PABlockHandler blockHandler;

    public ConnectionController(blockLinkDatabase converter, PABlockHandler blockHandler) {
        this.converter = converter;
        this.blockHandler = blockHandler;
    }

    public void connectBlocks(GUIBlock withMain, GUIBlock withSub, int subConnectorIndex) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        SubConnector subConnector = converter.getBlockFromGUIBlock(withSub).getSubConnectorAt(subConnectorIndex);
        blockHandler.connectToExistingBlock(mainBlock, subConnector);
    }

    public void disconnectBlock(GUIBlock withMain) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        blockHandler.disconnectInPA(mainBlock);
    }

    // TODO wegkrijgen met observers
    /*public boolean isValidConnection(GUIBlock withMain, GUIBlock withSub, int subConnectorIndex) {
        MainConnector mainConnector = converter.getBlockFromGUIBlock(withMain).getMainConnector();
        SubConnector subConnector = converter.getBlockFromGUIBlock(withSub).getSubConnectorAt(subConnectorIndex);
        return mainConnector.canHaveAsSubConnector(subConnector);
    }*/
}
