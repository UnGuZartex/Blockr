package BlockStructure.Blocks;

import BlockStructure.Functionality.Functionality;

public class Statement extends ConditionalBlock {

    public Statement(Functionality functionality) {
        super(functionality);
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public Block getNext() {
        return null;
    }
}
