package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;

public class ConnectionHandler {


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

    private Block getLastBlock(Block main) {
        Block toReturn = main;
        while (toReturn.hasNext()){
            toReturn = toReturn.getNext();
        }
        return toReturn;
    }

    public void disconnect(Block toDisconnect) {
        toDisconnect.getMainConnector().disconnect();
    }

}
