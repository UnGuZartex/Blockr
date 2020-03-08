package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;

public class ConnectionHandler {


    public void disconnect(Block toDisconnect) {
        toDisconnect.getMainConnector().disconnect();
    }

}
