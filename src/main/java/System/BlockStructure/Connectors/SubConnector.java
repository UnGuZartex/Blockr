package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;

/**
 * A class for sub connectors. This is a type of connector which
 * can be connected to a main connector.
 *
 * @author Alpha-team
 */
public class SubConnector extends Connector {


    private final String id;
    /**
     * Initialise a new sub connector with given block, orientation and type.
     *
     * @param ID The id of the block
     * @param block The block to which this connector belongs.
     * @param orientation The orientation of this connector.
     * @param type The type of this connector.
     *
     * @effect calls the super constructor with given block, orientation and type.
     *
     * @post the id of this subconnector is set to the given id.
     */
    public SubConnector(String ID, Block block, Orientation orientation, Type type) {
        super(block, orientation, type);
        this.id = ID;
    }

    /**
     * Get the id of this sub connector.
     *
     * @return The id of this sub connector.
     */
    public String getID() {
        return id;
    }

    /**
     * Connect this sub connector to the given main connector.
     *
     * @param mainConnector The main connector to connect to.
     *
     *
     * @post This sub connector is connected to the given main connector.
     *
     * @throws IllegalStateException
     *         This connector is already connected.
     * @throws IllegalArgumentException
     *         The given connector is not connected.
     * @throws IllegalArgumentException
     *         The given connector is already connected.
     */
    protected void connect (MainConnector mainConnector) throws IllegalStateException, IllegalArgumentException {
        if (this.isConnected()) {
            throw new IllegalStateException("This sub connector is already connected to another main connector!");
        }

        if (!mainConnector.isConnected()) {
            throw new IllegalArgumentException("The given main connector should already be connected!");
        }

        if (mainConnector.getConnectedConnector() != this) {
            throw new IllegalArgumentException("The given main connector is already connected to another sub connector!");
        }

        connectedConnector = mainConnector;
    }

    /**
     * Disconnect this sub connector from its sub connector.
     *
     * @post This sub connector isn't connected to any sub connector anymore.
     *
     * @throws IllegalStateException
     *         This connector is already connected to a main connector.
     */
    @Override
    protected void disconnect() throws IllegalStateException {

        if (connectedConnector.isConnected()) {
            System.out.println("Reached");
            throw new IllegalStateException("This sub connector is not connected!");
        }

        connectedConnector = null;
    }
}
