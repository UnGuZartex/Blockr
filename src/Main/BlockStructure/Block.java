package Main.BlockStructure;

import Main.BlockStructure.Connections.BlockConnectors;
import Main.BlockStructure.Functionality.Functionality;

public class Block {

    private int id;
    private BlockConnectors connection;
    private Functionality functionality;

    public Block(int id, BlockConnectors connection, Functionality functionality) {
        this.id = id;
        this.connection = connection;
        this.functionality = functionality;
    }

    public int getId() {
        return id;
    }

    public BlockConnectors getConnection() {
        return connection;
    }

    public Functionality getFunctionality() {
        return functionality;
    }
}
