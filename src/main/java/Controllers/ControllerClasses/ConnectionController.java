package Controllers.ControllerClasses;

import Controllers.BlockLinkDatabase;
import GUI.Blocks.IGUIBlock;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.ProgramArea.PABlockHandler;

public class ConnectionController {

    private final BlockLinkDatabase converter;
    private final PABlockHandler blockHandler;

    public ConnectionController(BlockLinkDatabase converter, PABlockHandler blockHandler) {
        this.converter = converter;
        this.blockHandler = blockHandler;
    }

    public void connectBlocks(IGUIBlock withMain, IGUIBlock withSub, int subConnectorIndex) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        SubConnector subConnector = converter.getBlockFromGUIBlock(withSub).getSubConnectorAt(subConnectorIndex);
        blockHandler.connectToExistingBlock(mainBlock, subConnector);
    }

    public void disconnectBlock(IGUIBlock withMain) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        blockHandler.disconnectInPA(mainBlock);
    }

    public boolean isValidConnection(IGUIBlock withMain, IGUIBlock withSub, int subConnectorIndex) {
        MainConnector mainConnector = converter.getBlockFromGUIBlock(withMain).getMainConnector();
        SubConnector subConnector = converter.getBlockFromGUIBlock(withSub).getSubConnectorAt(subConnectorIndex);
        return mainConnector.canHaveAsSubConnector(subConnector);
    }
}
