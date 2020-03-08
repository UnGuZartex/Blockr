package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Connectors.SubConnector;

public class ConnectionHandler {


    public void connect(Block main, SubConnector toConnectTo) {

        if (toConnectTo.isConnected()) {
            Block previousConnected = toConnectTo.getConnectedBlock();
            previousConnected.getMainConnector().disconnect();
            Block last = getLastBlock(main);
            previousConnected.getMainConnector().connect(last.getSubConnectors()[0]);
        }
        main.getMainConnector().connect(toConnectTo);
    }

    private Block getLastBlock(Block main) {
        Block toReturn = main;
        while (toReturn.getNext() != null){
            toReturn = toReturn.getNext();
        }
        return toReturn;
    }

    public void disconnect(Block toDisconnect) {
        toDisconnect.getMainConnector().disconnect();
    }

}
