package System.BlockStructure.Blocks.BlockTest.ConnectionHandler;

import System.BlockStructure.Blocks.BlockTest.Blocks.IBlock;

public interface BaseConnectionHandler {
    void connect(IBlock block);
    void disconnect();
}
