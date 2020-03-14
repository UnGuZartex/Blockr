package Controllers;


import GUI.Blocks.GUIBlock;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.ProgramArea.ConnectionHandler;

public class ConnectionController {

    private final GUItoSystemInterface converter;
    private final ConnectionHandler connector = new ConnectionHandler();

    public ConnectionController(GUItoSystemInterface converter) {
        this.converter = converter;
    }

    public void connectBlocks(GUIBlock withMain, GUIBlock withSub, String connectionID) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        SubConnector subConnector = converter.getSubConnectorFromGUIBlockWithID(withSub, connectionID);
        connector.connect(mainBlock, subConnector);
    }

    public void disconnectBlock(GUIBlock withMain) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        if (mainBlock.getMainConnector().isConnected()) {
            connector.disconnect(mainBlock);
        }
    }

    public boolean isValidConnection(GUIBlock withMain, GUIBlock withSub, String connectionID) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        SubConnector subConnector = converter.getSubConnectorFromGUIBlockWithID(withSub, connectionID);
        MainConnector mainConnector= mainBlock.getMainConnector();
        return mainConnector.canHaveAsSubConnector(subConnector);
    }
}
