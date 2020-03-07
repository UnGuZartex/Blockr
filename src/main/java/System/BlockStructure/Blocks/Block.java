package System.BlockStructure.Blocks;

<<<<<<< HEAD:src/main/java/BlockStructure/Blocks/Block.java

import BlockStructure.Functionality.Functionality;
=======
import System.BlockStructure.Functionality.IFunctionality;
>>>>>>> 8c3ed1b8bf3fdb92c75eaf46bcd0cac655e2609f:src/main/java/System/BlockStructure/Blocks/Block.java

public abstract class Block<F extends IFunctionality> {

    private final int id;
    private final F functionality;

    protected Block(int id, F functionality) {
        this.id = id;
        this.functionality = functionality;
    }

    public F getFunctionality() {
        return functionality;
    }

    public abstract boolean hasNext();

    public abstract Block<?> getNext();
}
