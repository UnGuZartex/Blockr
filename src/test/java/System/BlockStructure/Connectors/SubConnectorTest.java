package System.BlockStructure.Connectors;

import System.BlockStructure.Blocks.Block;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubConnectorTest {

    private MainConnector mainPlugUp, mainPlugDown, mainPlugLeft, mainPlugRight,
            mainSocketUp, mainSocketDown, mainSocketLeft, mainSocketRight, main;
    private SubConnector plugUp, plugDown, plugLeft, plugRight,
            socketUp, socketDown, socketLeft, socketRight;
    private Block block1, block2;

    @BeforeEach
    void setUp() {
        plugUp = new SubConnector("CheckCheck",block1, Orientation.FACING_UP, Type.PLUG);
        mainPlugUp = new MainConnector(block2, Orientation.FACING_DOWN, Type.SOCKET);
        mainPlugUp.connect(plugUp);
        plugDown = new SubConnector("CheckCheck",block1, Orientation.FACING_DOWN, Type.PLUG);
        mainPlugDown = new MainConnector(block2, Orientation.FACING_UP, Type.SOCKET);
        mainPlugDown.connect(plugDown);
        plugLeft = new SubConnector("CheckCheck",block1, Orientation.FACING_LEFT, Type.PLUG);
        mainPlugLeft = new MainConnector(block2, Orientation.FACING_LEFT, Type.SOCKET);
        plugRight = new SubConnector("CheckCheck",block1, Orientation.FACING_RIGHT, Type.PLUG);
        mainPlugRight = new MainConnector(block2, Orientation.FACING_LEFT, Type.SOCKET);

        socketUp = new SubConnector("CheckCheck",block1, Orientation.FACING_UP, Type.SOCKET);
        mainSocketUp = new MainConnector(block2, Orientation.FACING_DOWN, Type.PLUG);
        socketDown = new SubConnector("CheckCheck",block1, Orientation.FACING_DOWN, Type.SOCKET);
        mainSocketDown = new MainConnector(block2, Orientation.FACING_UP, Type.PLUG);
        socketLeft = new SubConnector("CheckCheck",block1, Orientation.FACING_LEFT, Type.SOCKET);
        mainSocketLeft = new MainConnector(block2, Orientation.FACING_RIGHT, Type.PLUG);
        mainSocketLeft.connect(socketLeft);
        socketRight = new SubConnector("CheckCheck",block1, Orientation.FACING_RIGHT, Type.SOCKET);
        mainSocketRight = new MainConnector(block2, Orientation.FACING_LEFT, Type.PLUG);
        mainSocketRight.connect(socketRight);
    }

    @AfterEach
    void tearDown() {
        plugUp = null;
        mainPlugUp = null;
        plugDown = null;
        mainPlugDown = null;
        plugLeft = null;
        mainPlugLeft = null;
        plugRight = null;
        mainPlugRight = null;

        socketUp = null;
        mainSocketUp = null;
        socketDown = null;
        mainSocketDown = null;
        socketLeft = null;
        mainSocketLeft = null;
        socketRight = null;
        mainSocketRight = null;
    }

    @Test
    void getOrientation() {
        assertEquals(Orientation.FACING_UP, plugUp.getOrientation());
        assertEquals(Orientation.FACING_DOWN, plugDown.getOrientation());
        assertEquals(Orientation.FACING_LEFT, plugLeft.getOrientation());
        assertEquals(Orientation.FACING_RIGHT, plugRight.getOrientation());

        assertEquals(Orientation.FACING_UP, socketUp.getOrientation());
        assertEquals(Orientation.FACING_LEFT, socketLeft.getOrientation());
        assertEquals(Orientation.FACING_DOWN, socketDown.getOrientation());
        assertEquals(Orientation.FACING_RIGHT, socketRight.getOrientation());
    }

    @Test
    void getConnectedConnector() {
        assertEquals(plugUp.getConnectedConnector(), mainPlugUp);
        assertEquals(plugDown.getConnectedConnector(), mainPlugDown);
        assertNull(plugLeft.getConnectedConnector());
        assertNull(plugRight.getConnectedConnector());

        assertNull(socketUp.getConnectedConnector());
        assertNull(socketDown.getConnectedConnector());
        assertEquals(socketLeft.getConnectedConnector(), mainSocketLeft);
        assertEquals(socketRight.getConnectedConnector(), mainSocketRight);
    }

    @Test
    void getType() {
        assertEquals(Type.PLUG, plugUp.getType());
        assertEquals(Type.PLUG, plugDown.getType());
        assertEquals(Type.PLUG, plugLeft.getType());
        assertEquals(Type.PLUG, plugRight.getType());

        assertEquals(Type.SOCKET, socketUp.getType());
        assertEquals(Type.SOCKET, socketLeft.getType());
        assertEquals(Type.SOCKET, socketDown.getType());
        assertEquals(Type.SOCKET, socketRight.getType());
    }

    @Test
    void isConnected() {
        assertTrue(plugUp.isConnected());
        assertTrue(plugDown.isConnected());
        assertFalse(plugLeft.isConnected());
        assertFalse(plugRight.isConnected());

        assertFalse(socketUp.isConnected());
        assertFalse(socketDown.isConnected());
        assertTrue(socketLeft.isConnected());
        assertTrue(socketRight.isConnected());
    }

    @Test
    void getBlock() {
        assertEquals(block1, plugUp.getBlock());
        assertEquals(block1, plugDown.getBlock());
        assertEquals(block1, plugLeft.getBlock());
        assertEquals(block1, plugRight.getBlock());

        assertEquals(block1, socketLeft.getBlock());
        assertEquals(block1, socketRight.getBlock());
        assertEquals(block1, socketUp.getBlock());
        assertEquals(block1, socketDown.getBlock());
    }

    @Test
    void getConnectedBlock() {
        assertEquals(block2, plugUp.getConnectedBlock());
        assertEquals(block2, plugDown.getConnectedBlock());
        assertNull(plugLeft.getConnectedBlock());
        assertNull(plugRight.getConnectedBlock());

        assertNull(socketLeft.getConnectedBlock());
        assertNull(socketRight.getConnectedBlock());
        assertEquals(block2, socketUp.getConnectedBlock());
        assertEquals(block2, socketDown.getConnectedBlock());
    }

    @Test
    void connect_SubAlreadyConnected() {
        assertThrows(IllegalStateException.class, () -> { plugUp.connect(main); });
        assertThrows(IllegalStateException.class, () -> { plugDown.connect(main); });
        assertThrows(IllegalStateException.class, () -> { socketRight.connect(main); });
        assertThrows(IllegalStateException.class, () -> { socketLeft.connect(main); });
    }

    @Test
    void connect_MainNotConnected() {
        assertThrows(IllegalArgumentException.class, () -> { socketUp.connect(mainSocketUp); });
        assertThrows(IllegalArgumentException.class, () -> { socketUp.connect(mainSocketDown); });
        assertThrows(IllegalArgumentException.class, () -> { socketUp.connect(mainPlugLeft); });
        assertThrows(IllegalArgumentException.class, () -> { socketUp.connect(mainPlugRight); });
    }

    @Test
    void connect_MainConnectedToOther() {
        assertThrows(IllegalArgumentException.class, () -> { plugLeft.connect(mainPlugDown); });
        assertThrows(IllegalArgumentException.class, () -> { plugLeft.connect(mainPlugUp); });
        assertThrows(IllegalArgumentException.class, () -> { plugLeft.connect(mainSocketRight); });
        assertThrows(IllegalArgumentException.class, () -> { plugLeft.connect(mainSocketLeft); });
    }

    @Test
    void disconnect_AlreadyConnected() {
        assertThrows(IllegalStateException.class, () -> { plugUp.disconnect(); });
        assertThrows(IllegalStateException.class, () -> { plugDown.disconnect(); });
        assertThrows(IllegalStateException.class, () -> { socketLeft.disconnect(); });
        assertThrows(IllegalStateException.class, () -> { socketRight.disconnect(); });
    }
}