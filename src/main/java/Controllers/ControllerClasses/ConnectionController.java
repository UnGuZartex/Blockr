package Controllers.ControllerClasses;

import Controllers.IGUI_System_BlockLink;
import GUI.Blocks.IGUIBlock;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.ProgramArea.PABlockHandler;

/**
 * A controller class for the connections between GUI and system.
 *
 * @invar A connection controller must have an effective block link database.
 *        | converter != null
 * @invar A connection controller must have an effective pa block handler.
 *        | blockHandler != null
 *
 * @author Alpha-team
 */
public class ConnectionController {

    /**
     * Variable referring to the block link database of this controller.
     */
    private final IGUI_System_BlockLink converter;
    /**
     * Variable referring to the pa block handler of this controller.
     */
    private final PABlockHandler blockHandler;

    /**
     * Initialse a new connection controller with given converter and block handler.
     *
     * @param converter The block link database for this controller.
     * @param blockHandler The pa block handler for this controller.
     *
     * @post The converter of this controller is set to the given converter.
     * @post The block handler of this controller is set to the given block handler.
     *
     * @throws IllegalArgumentException
     *         When the given converter is not effective.
     * @throws IllegalArgumentException
     *         When the given block handler is not effective.
     */
    public ConnectionController(IGUI_System_BlockLink converter, PABlockHandler blockHandler) throws IllegalArgumentException {
        if (converter == null) {
            throw new IllegalArgumentException("The given converter is not effective!");
        }
        if (blockHandler == null) {
            throw new IllegalArgumentException("The given block handler is not effective!");
        }
        this.converter = converter;
        this.blockHandler = blockHandler;
    }

    /**
     * Connect the block in the block handler equal to the given blocks.
     *
     * @param withMain The main gui block to connect.
     * @param withSub The sub gui block to connect.
     * @param subConnectorIndex The index of the sub connector to connect.
     *
     * @effect The counter blocks of the given gui blocks are connected in the block handler.
     */
    public void connectBlocks(IGUIBlock withMain, IGUIBlock withSub, int subConnectorIndex) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        SubConnector subConnector = converter.getBlockFromGUIBlock(withSub).getSubConnectorAt(subConnectorIndex);
        blockHandler.connectToExistingBlock(mainBlock, subConnector);
    }

    /**
     * Disconnects the block in the block handler equal to the given gui block.
     *
     * @param withMain The gui block of which its system block should be disconnected.
     *
     * @effect The block compatibel with the given gui block is disconnected in on
     *         its main connector.
     */
    public void disconnectBlock(IGUIBlock withMain) {
        Block mainBlock = converter.getBlockFromGUIBlock(withMain);
        blockHandler.disconnectInPA(mainBlock);
    }

    /**
     * Checks whether the given gui blocks can connect.
     *
     * @param withMain The block to connect through its main connector.
     * @param withSub The block to connect through a sub connector.
     * @param subConnectorIndex The index of the subconnector to check.
     *
     * @return True if and only if the main connector of the block compatible with
     *         'withMain' can have the sub connector of the given block at the given
     *         index as a sub connector.
     */
    public boolean isValidConnection(IGUIBlock withMain, IGUIBlock withSub, int subConnectorIndex) {
        MainConnector mainConnector = converter.getBlockFromGUIBlock(withMain).getMainConnector();
        SubConnector subConnector = converter.getBlockFromGUIBlock(withSub).getSubConnectorAt(subConnectorIndex);
        return mainConnector.canHaveAsSubConnector(subConnector);
    }
}