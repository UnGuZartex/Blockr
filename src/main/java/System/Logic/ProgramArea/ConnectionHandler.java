package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.ConditionalBlock;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Connectors.Plug;
import System.BlockStructure.Connectors.Socket;

public class ConnectionHandler {
    public void connect(FunctionalBlock<?> upperBlock, FunctionalBlock<?> lowerBlock) throws Exception {
        Plug<FunctionalBlock<?>, FunctionalBlock<?>> upperBlockPlug = upperBlock.getBottomPlug();
        Socket<FunctionalBlock<?>, FunctionalBlock<?>> lowerBlockSocket = lowerBlock.getTopSocket();
        Plug<FunctionalBlock<?>, FunctionalBlock<?>> lowerBlockPlug = lowerBlock.getBottomPlug();
        if (upperBlock.hasNext()) {
            FunctionalBlock<?> connectedBlock = upperBlock.getNext();
            Socket<FunctionalBlock<?>, FunctionalBlock<?>> connectedBlockSocket = connectedBlock.getTopSocket();
            upperBlockPlug.disconnect();
            upperBlockPlug.connect(lowerBlockSocket);
            lowerBlockPlug.connect(connectedBlockSocket);

        }
        else {
            upperBlockPlug.connect(lowerBlockSocket);
        }
    }
    
    /*public void test() {
        Plug<FunctionalBlock<?>, Block<?>> plug = null;
        Socket<ConditionalBlock, Block<?>> socket = null;
        
        connect(plug, socket);
    }*/

    public <A extends  Block<?>, B extends Block<?>> void connect(Socket<A, B> socket, Plug<B, A> plug) {
        plug.connect(socket);
    }

//    public void connect(ConditionalBlock leftBlock, ConditionalBlock rightBlock) throws Exception {
//
//    }
//
//    public void connect(CavityBlock leftBlock, ConditionalBlock rightBlock) throws Exception {
//        Socket<CavityBlock, ConditionalBlock> leftBlockSocket = leftBlock.getConditionalSocket();
//        Plug<ConditionalBlock, Block<?>> rightBlockPlug = rightBlock.getLeftPlug();
//        rightBlockPlug.connect(leftBlockSocket);
//
//    }
}
