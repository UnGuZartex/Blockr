package Controllers;


import GUI.Blocks.GUIBlock;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.ProgramArea.ConnectionHandler;
import System.Logic.ProgramArea.PABlockHandler;

public class ConnectionController {

    private final GUItoSystemInterface converter;
    private final PABlockHandler blockHandler;

    public ConnectionController(GUItoSystemInterface converter, PABlockHandler blockHandler) {
        this.converter = converter;
        this.blockHandler = blockHandler;
    }

    public void connectBlocks(GUIBlock withMain, GUIBlock withSub, String connectionID) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        SubConnector subConnector = converter.getSubConnectorFromGUIBlockWithID(withSub, connectionID);
        blockHandler.connectToExistingBlock(mainBlock, subConnector);
    }

    public void disconnectBlock(GUIBlock withMain) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        blockHandler.disconnectInPA(mainBlock);
    }

    public boolean isValidConnection(GUIBlock withMain, GUIBlock withSub, String connectionID) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        SubConnector subConnector = converter.getSubConnectorFromGUIBlockWithID(withSub, connectionID);
        MainConnector mainConnector= mainBlock.getMainConnector();
        return mainConnector.canHaveAsSubConnector(subConnector);
    }
}
