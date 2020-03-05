package BlockStructure.Blocks;

import BlockStructure.Connectors.Orientation;
import BlockStructure.Connectors.Plug;
import BlockStructure.Connectors.Socket;
import BlockStructure.Functionality.ConditionalFunctionality;

public class Cavoc extends FunctionalBlock {

    private final Plug cavityPlug;
    private final Socket cavitySocket;
    private final Socket conditionalSocket;

    public Cavoc(int id, ConditionalFunctionality functionality) {
        super(id, functionality);
        cavityPlug = new Plug(this, Orientation.FACING_DOWN);
        cavitySocket = new Socket(this, Orientation.FACING_UP);
        conditionalSocket = new Socket(this, Orientation.FACING_RIGHT);
    }


    public Plug getCavityPlug() {
        return cavityPlug;
    }

    public Socket getCavitySocket() {
        return cavitySocket;
    }

    public Socket getConditionalSocket() {
        return conditionalSocket;
    }

    public ConditionalBlock getCondition() {
        return (ConditionalBlock) conditionalSocket.getPlug().getBlock();
    }

    @Override
    public boolean hasNext() {
        ConditionalFunctionality func = (ConditionalFunctionality) getFunctionality();
        if(func.getEvaluation()){
            return cavitySocket.getPlug().getBlock() != null;
        }
        else{
            return getPlug().getSocket().getBlock() != null;
        }
    }

    @Override
    public FunctionalBlock getNext() {
        ConditionalFunctionality func = (ConditionalFunctionality) getFunctionality();
        if(func.getEvaluation()){
            return (FunctionalBlock) cavitySocket.getPlug().getBlock();
        }
        else{
            return (FunctionalBlock) getPlug().getSocket().getBlock();
        }
    }


}
