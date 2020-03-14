package Controllers;


import GUI.Blocks.GUIBlock;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.ProgramArea.ConnectionHandler;
import System.Logic.ProgramArea.PABlockHandler;

public class ConnectionController {

    private final GUItoSystemInterface converter;
    private final PABlockHandler handler;

    public ConnectionController(GUItoSystemInterface converter, PABlockHandler handler) {
        this.converter = converter;
        this.handler = handler;
    }

    public void connectBlocks(GUIBlock withMain, GUIBlock withSub, String connectionID) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        SubConnector subConnector = converter.getSubConnectorFromGUIBlockWithID(withSub, connectionID);
        handler.connectToExistingBlock(mainBlock, subConnector);
    }

    public void disconnectBlock(GUIBlock withMain) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        if (mainBlock.getMainConnector().isConnected()) {
            handler.disconnectInPA(mainBlock);
        }
    }

    public boolean isValidConnection(GUIBlock withMain, GUIBlock withSub, String connectionID) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        SubConnector subConnector = converter.getSubConnectorFromGUIBlockWithID(withSub, connectionID);
        MainConnector mainConnector= mainBlock.getMainConnector();
        return mainConnector.canHaveAsSubConnector(subConnector);
    }
}
