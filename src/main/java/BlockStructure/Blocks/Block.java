package BlockStructure.Blocks;

import BlockStructure.Functionality.Functionality;

public abstract class Block {

    private final Functionality functionality;

    public Block(Functionality functionality) {
        this.functionality = functionality;
    }

    public Functionality getFunctionality() {
        return functionality;
    }

    public abstract boolean hasNext();

    public abstract Block getNext();
}
