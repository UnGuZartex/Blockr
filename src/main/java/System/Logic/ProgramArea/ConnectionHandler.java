package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;

/**
 * A class to handle connections between different blocks.
 *
 * @author ALpha-team
 */
public class ConnectionHandler {

    /**
     * Connect a given block on it's main connector to a given sub connector.
     *
     * @param main The block to connect on it's main connector.
     * @param toConnectTo The sub connector to connect to the main connector of the given block.
     *
     * @pre The given block may not have any connection on it's main connector.
     */
    public void connect(Block main, SubConnector toConnectTo) {
        if (toConnectTo.isConnected()) {
            Block previousConnected = toConnectTo.getConnectedBlock();
            previousConnected.getMainConnector().disconnect();
            Block last = getLastBlock(main);
            if (last.getNbSubConnectors() != 0) {
                previousConnected.getMainConnector().connect(last.getSubConnectorAt(0));
            }
        }
        main.getMainConnector().connect(toConnectTo);
    }

    /**
     * Get the last connected block to the given block.
     *
     * @param main The block to get the last connected block from.
     *
     * @return The block which is connected to the given block, but
     *         has no more blocks connected to it.
     */
    private Block getLastBlock(Block main) {
        Block toReturn = main;
        while (toReturn.hasNext()) {
            toReturn = toReturn.getNext();
        }
        return toReturn;
    }

    /**
     * Disconnect the given block on it's main connector.
     *
     * @param toDisconnect The block to disconnect.
     */
    public void disconnect(Block toDisconnect) {
        toDisconnect.getMainConnector().disconnect();
    }
}
