package System.Logic.ProgramArea;

import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.FunctionalBlock;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.Logic.ProgramArea.Handlers.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ConnectionHandlerTest {

    ConnectionHandler handler;
    Block block1, block2, block3, block4, block5;
    GameWorldType type;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        handler = new ConnectionHandler();

        block1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        block2 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        block3 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2)));
        block4 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1)));
        block5 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
    }

    @AfterEach
    void tearDown() {
        type = null;
        handler = null;
        block1 = null;
        block2 = null;
        block3 = null;
        block4 = null;
        block5 = null;
    }

    @Test
    void connect() {
        assertFalse(block2.getMainConnector().isConnected());
        handler.connect(block2, block1.getSubConnectorAt(0));
        assertEquals(block1, block2.getMainConnector().getConnectedBlock());
        assertEquals(block2, block1.getSubConnectorAt(0).getConnectedBlock());

        assertFalse(block4.getMainConnector().isConnected());
        handler.connect(block3, block4.getSubConnectorAt(0));
        handler.connect(block4, block1.getSubConnectorAt(0));
        assertEquals(block1, block4.getMainConnector().getConnectedBlock());
        assertEquals(block4, block1.getSubConnectorAt(0).getConnectedBlock());
        assertEquals(block3, block2.getMainConnector().getConnectedBlock());
        assertEquals(block2, block3.getSubConnectorAt(0).getConnectedBlock());
        assertEquals(block4, block3.getMainConnector().getConnectedBlock());
        assertEquals(block3, block4.getSubConnectorAt(0).getConnectedBlock());
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