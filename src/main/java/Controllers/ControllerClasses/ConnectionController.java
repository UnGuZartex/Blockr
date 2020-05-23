package Controllers.ControllerClasses;

import Controllers.IGUISystemBlockLink;
import GUI.Blocks.IGUIBlock;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.MainConnector;
import System.BlockStructure.Connectors.SubConnector;
import System.Logic.ProgramArea.PABlockHandler;

/**
 * A controller class for the connections between GUI and system.
 *
 * @invar A connection controller must have a valid block link database.
 *        | isValidIGUISystemBlockLink(converter)
 * @invar A connection controller must have a valid pa block handler.
 *        | isValidPABlockHandler(blockHandler)
 *
 * @author Alpha-team
 */
public class ConnectionController {

    /**
     * Variable referring to the block link database of this controller.
     */
    private final IGUISystemBlockLink converter;
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
     *         When the given converter is not valid.
     * @throws IllegalArgumentException
     *         When the given block handler is not valid.
     */
    public ConnectionController(IGUISystemBlockLink converter, PABlockHandler blockHandler) throws IllegalArgumentException {
        if (!isValidIGUISystemBlockLink(converter)) {
            throw new IllegalArgumentException("The given converter is not valid!");
        }
        if (!isValidPABlockHandler(blockHandler)) {
            throw new IllegalArgumentException("The given block handler is not valid!");
        }
        this.converter = converter;
        this.blockHandler = blockHandler;
    }

    /**
     * Checks whether or not the given block link is valid.
     *
     * @param blockLink The block link to check.
     *
     * @return True if and only if the given block link is effective.
     */
    public static boolean isValidIGUISystemBlockLink(IGUISystemBlockLink blockLink) {
        return blockLink != null;
    }

    /**
     * Checks whether or not the given pa block handler is valid.
     *
     * @param blockHandler The pa block handler to check.
     *
     * @return True if and only if the given pa block handler is effective.
     */
    public static boolean isValidPABlockHandler(PABlockHandler blockHandler) {
        return blockHandler != null;
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
     * @effect The block compatible with the given gui block is disconnected in on
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