package BlockStructure.Blocks;


import BlockStructure.Functionality.Functionality;

public abstract class Block<F extends Functionality> {

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
