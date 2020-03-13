package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.Factory.MoveForwardBlockFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionHandlerTest {

    ConnectionHandler handler;
    Block block1, block2, block3, block4, block5;


    @BeforeEach
    void setUp() {
        handler = new ConnectionHandler();

        MoveForwardBlockFactory factory = new MoveForwardBlockFactory();
        block1 = factory.createBlock();
        block2 = factory.createBlock();
        block3 = factory.createBlock();
        block4 = factory.createBlock();
        block5 = factory.createBlock();
    }

    @AfterEach
    void tearDown() {
        handler = null;
        block1 = null;
        block2 = null;
        block3 = null;
        block4 = null;
        block5 = null;
    }

    @Test
    void connect() {
        assertFalse(block1.getSubConnectorAt(0).isConnected());
        assertFalse(block3.getMainConnector().isConnected());
        handler.connect(block3, block1.getSubConnectorAt(0));
        assertEquals(block1, block3.getMainConnector().getConnectedBlock());
        assertEquals(block3, block1.getSubConnectorAt(0).getConnectedBlock());

        assertFalse(block2.getMainConnector().isConnected());
        handler.connect(block2, block1.getSubConnectorAt(0));
        assertEquals(block1, block2.getMainConnector().getConnectedBlock());
        assertEquals(block2, block1.getSubConnectorAt(0).getConnectedBlock());
        assertEquals(block2, block3.getMainConnector().getConnectedBlock());
        assertEquals(block3, block2.getSubConnectorAt(0).getConnectedBlock());

        assertFalse(block4.getMainConnector().isConnected());
        handler.connect(block4, block1.getSubConnectorAt(0));
        assertEquals(block1, block4.getMainConnector().getConnectedBlock());
        assertEquals(block4, block1.getSubConnectorAt(0).getConnectedBlock());
        assertEquals(block4, block2.getMainConnector().getConnectedBlock());
        assertEquals(block2, block4.getSubConnectorAt(0).getConnectedBlock());
        assertEquals(block2, block3.getMainConnector().getConnectedBlock());
        assertEquals(block3, block2.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void disconnect() {
        handler.connect(block2, block1.getSubConnectorAt(0));
        handler.connect(block3, block2.getSubConnectorAt(0));
        assertEquals(block1, block2.getMainConnector().getConnectedBlock());
        assertEquals(block2, block1.getSubConnectorAt(0).getConnectedBlock());
        assertEquals(block2, block3.getMainConnector().getConnectedBlock());
        assertEquals(block3, block2.getSubConnectorAt(0).getConnectedBlock());
        handler.disconnect(block2);
        assertFalse(block1.getSubConnectorAt(0).isConnected());
        assertFalse(block2.getMainConnector().isConnected());
        assertEquals(block2, block3.getMainConnector().getConnectedBlock());
        assertEquals(block3, block2.getSubConnectorAt(0).getConnectedBlock());

        handler.disconnect(block3);
        assertFalse(block2.getSubConnectorAt(0).isConnected());
        assertFalse(block3.getMainConnector().isConnected());
    }
}