//package System.BlockStructure.Connectors;
//
//import System.BlockStructure.Blocks.Block;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
// TODO - main connector test
//class MainConnectorTest {
//
//    private MainConnector plugUp, plugDown, plugLeft, plugRight,
//            socketUp, socketDown, socketLeft, socketRight;
//    private SubConnector subPlugUp, subPlugDown, subPlugLeft,
//            subSocketLeft, subSocketRight, subSocketUp, sub;
//    private Block block1, block2;
//
//
//    @BeforeEach
//    void setUp() {
//        plugUp = new MainConnector(block1, Orientation.FACING_UP, Type.PLUG);
//        subPlugUp = new SubConnector(block2, Orientation.FACING_DOWN, Type.SOCKET);
//        plugUp.connect(subPlugUp);
//        plugDown = new MainConnector(block1, Orientation.FACING_DOWN, Type.PLUG);
//        subPlugDown = new SubConnector(block2, Orientation.FACING_UP, Type.SOCKET);
//        plugDown.connect(subPlugDown);
//        plugLeft = new MainConnector(block1, Orientation.FACING_LEFT, Type.PLUG);
//        subPlugLeft = new SubConnector(block2, Orientation.FACING_RIGHT, Type.SOCKET);
//        plugRight = new MainConnector(block1, Orientation.FACING_RIGHT, Type.PLUG);
//
//        socketUp = new MainConnector(block1, Orientation.FACING_UP, Type.SOCKET);
//        subSocketUp = new SubConnector(block2, Orientation.FACING_DOWN, Type.PLUG);
//        socketDown = new MainConnector(block1, Orientation.FACING_DOWN, Type.SOCKET);
//        socketLeft = new MainConnector(block1, Orientation.FACING_LEFT, Type.SOCKET);
//        subSocketLeft = new SubConnector(block2, Orientation.FACING_RIGHT, Type.PLUG);
//        socketLeft.connect(subSocketLeft);
//        socketRight = new MainConnector(block1, Orientation.FACING_RIGHT, Type.SOCKET);
//        subSocketRight = new SubConnector(block2, Orientation.FACING_LEFT, Type.PLUG);
//        socketRight.connect(subSocketRight);
//    }
//
//    @AfterEach
//    void tearDown() {
//        plugUp = null;
//        subPlugUp = null;
//        plugDown = null;
//        subPlugDown = null;
//        plugLeft = null;
//        subPlugLeft = null;
//        plugRight = null;
//
//        socketUp = null;
//        subSocketUp = null;
//        socketDown = null;
//        socketLeft = null;
//        subSocketLeft = null;
//        socketRight = null;
//        subSocketRight = null;
//    }
//
//    @Test
//    void hasProperConnector() {
//        assertTrue(plugUp.hasProperConnector());
//        assertTrue(subPlugUp.hasProperConnector());
//        assertTrue(plugDown.hasProperConnector());
//        assertTrue(subPlugDown.hasProperConnector());
//        assertTrue(plugLeft.hasProperConnector());
//        assertTrue(subPlugLeft.hasProperConnector());
//        assertTrue(plugRight.hasProperConnector());
//        assertTrue(socketUp.hasProperConnector());
//        assertTrue(subSocketUp.hasProperConnector());
//        assertTrue(socketDown.hasProperConnector());
//        assertTrue(socketLeft.hasProperConnector());
//        assertTrue(subSocketLeft.hasProperConnector());
//        assertTrue(socketRight.hasProperConnector());
//        assertTrue(subSocketRight.hasProperConnector());
//    }
//
//    @Test
//    void getOrientation() {
//        assertEquals(Orientation.FACING_UP, plugUp.getOrientation());
//        assertEquals(Orientation.FACING_DOWN, plugDown.getOrientation());
//        assertEquals(Orientation.FACING_LEFT, plugLeft.getOrientation());
//        assertEquals(Orientation.FACING_RIGHT, plugRight.getOrientation());
//
//        assertEquals(Orientation.FACING_UP, socketUp.getOrientation());
//        assertEquals(Orientation.FACING_LEFT, socketLeft.getOrientation());
//        assertEquals(Orientation.FACING_DOWN, socketDown.getOrientation());
//        assertEquals(Orientation.FACING_RIGHT, socketRight.getOrientation());
//    }
//
//    @Test
//    void getConnectedConnector() {
//        assertEquals(plugUp.getConnectedConnector(), subPlugUp);
//        assertEquals(plugDown.getConnectedConnector(), subPlugDown);
//        assertNull(plugLeft.getConnectedConnector());
//        assertNull(plugRight.getConnectedConnector());
//
//        assertNull(socketUp.getConnectedConnector());
//        assertNull(socketDown.getConnectedConnector());
//        assertEquals(socketLeft.getConnectedConnector(), subSocketLeft);
//        assertEquals(socketRight.getConnectedConnector(), subSocketRight);
//    }
//
//    @Test
//    void getType() {
//        assertEquals(Type.PLUG, plugUp.getType());
//        assertEquals(Type.PLUG, plugDown.getType());
//        assertEquals(Type.PLUG, plugLeft.getType());
//        assertEquals(Type.PLUG, plugRight.getType());
//
//        assertEquals(Type.SOCKET, socketUp.getType());
//        assertEquals(Type.SOCKET, socketLeft.getType());
//        assertEquals(Type.SOCKET, socketDown.getType());
//        assertEquals(Type.SOCKET, socketRight.getType());
//    }
//
//    @Test
//    void isConnected() {
//        assertTrue(plugUp.isConnected());
//        assertTrue(plugDown.isConnected());
//        assertFalse(plugLeft.isConnected());
//        assertFalse(plugRight.isConnected());
//
//        assertFalse(socketUp.isConnected());
//        assertFalse(socketDown.isConnected());
//        assertTrue(socketLeft.isConnected());
//        assertTrue(socketRight.isConnected());
//    }
//
//    @Test
//    void getBlock() {
//        assertEquals(block1, plugUp.getBlock());
//        assertEquals(block1, plugDown.getBlock());
//        assertEquals(block1, plugLeft.getBlock());
//        assertEquals(block1, plugRight.getBlock());
//
//        assertEquals(block1, socketLeft.getBlock());
//        assertEquals(block1, socketRight.getBlock());
//        assertEquals(block1, socketUp.getBlock());
//        assertEquals(block1, socketDown.getBlock());
//    }
//
//    @Test
//    void getConnectedBlock() {
//        assertEquals(block2, plugUp.getConnectedBlock());
//        assertEquals(block2, plugDown.getConnectedBlock());
//        assertNull(plugLeft.getConnectedBlock());
//        assertNull(plugRight.getConnectedBlock());
//
//        assertNull(socketLeft.getConnectedBlock());
//        assertNull(socketRight.getConnectedBlock());
//        assertEquals(block2, socketUp.getConnectedBlock());
//        assertEquals(block2, socketDown.getConnectedBlock());
//    }
//
//    @Test
//    void connect_MainAlreadyConnected() {
//        try {
//            plugUp.connect(sub);
//            assert false;
//        } catch (IllegalStateException ignore) {}
//
//        try {
//            plugDown.connect(sub);
//            assert false;
//        } catch (IllegalStateException ignore) {}
//
//        try {
//            socketLeft.connect(sub);
//            assert false;
//        } catch (IllegalStateException ignore) {}
//
//        try {
//            socketRight.connect(sub);
//            assert false;
//        } catch (IllegalStateException ignore) {}
//    }
//
//    @Test
//    void connect_SubAlreadyConnected() {
//        try {
//            (new MainConnector(block1, Orientation.FACING_RIGHT, Type.SOCKET)).connect(subSocketRight);
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//
//        try {
//            (new MainConnector(block1, Orientation.FACING_LEFT, Type.SOCKET)).connect(subSocketLeft);
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//
//        try {
//            (new MainConnector(block1, Orientation.FACING_UP, Type.PLUG)).connect(subPlugUp);
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//
//        try {
//            (new MainConnector(block1, Orientation.FACING_DOWN, Type.PLUG)).connect(subPlugDown);
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//    }
//
//    @Test
//    void connect_NotCanHaveAsSocket() {
//        try {
//            plugLeft.connect(null);
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//
//        try {
//            plugLeft.connect(new SubConnector(block2, Orientation.FACING_DOWN, Type.SOCKET));
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//
//        try {
//            plugLeft.connect(new SubConnector(block2, Orientation.FACING_UP, Type.SOCKET));
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//
//        try {
//            plugLeft.connect(new SubConnector(block2, Orientation.FACING_LEFT, Type.SOCKET));
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//
//        try {
//            plugLeft.connect(new SubConnector(block2, Orientation.FACING_RIGHT, Type.PLUG));
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//
//        try {
//            socketDown.connect(new SubConnector(block2, Orientation.FACING_UP, Type.SOCKET));
//            assert false;
//        } catch (IllegalArgumentException ignore) {}
//    }
//
//    @Test
//    void connect_Success() {
//        assertNull(plugLeft.getConnectedConnector());
//        assertNull(subPlugLeft.getConnectedConnector());
//        assertFalse(plugLeft.isConnected());
//        assertFalse(subPlugLeft.isConnected());
//        plugLeft.connect(subPlugLeft);
//        assertEquals(plugLeft.getConnectedConnector(), subPlugLeft);
//        assertEquals(subPlugLeft.getConnectedConnector(), plugLeft);
//        assertTrue(plugLeft.isConnected());
//        assertTrue(subPlugLeft.isConnected());
//
//        assertNull(socketUp.getConnectedConnector());
//        assertNull(subSocketUp.getConnectedConnector());
//        assertFalse(socketUp.isConnected());
//        assertFalse(subSocketUp.isConnected());
//        socketUp.connect(subSocketUp);
//        assertEquals(socketUp.getConnectedConnector(), subSocketUp);
//        assertEquals(subSocketUp.getConnectedConnector(), socketUp);
//        assertTrue(socketUp.isConnected());
//        assertTrue(subSocketUp.isConnected());
//    }
//
//    @Test
//    void disconnect_MainNotConnected() {
//        try {
//            plugLeft.disconnect();
//            assert false;
//        } catch (IllegalStateException ignore) {}
//
//        try {
//            plugRight.disconnect();
//            assert false;
//        } catch (IllegalStateException ignore) {}
//
//        try {
//            socketUp.disconnect();
//            assert false;
//        } catch (IllegalStateException ignore) {}
//
//        try {
//            socketDown.disconnect();
//            assert false;
//        } catch (IllegalStateException ignore) {}
//    }
//
//    @Test
//    void disconnect_Success() {
//        assertEquals(plugUp.getConnectedConnector(), subPlugUp);
//        assertEquals(subPlugUp.getConnectedConnector(), plugUp);
//        assertTrue(plugUp.isConnected());
//        assertTrue(subPlugUp.isConnected());
//        plugUp.disconnect();
//        assertNull(plugUp.getConnectedConnector());
//        assertNull(subPlugUp.getConnectedConnector());
//        assertFalse(plugUp.isConnected());
//        assertFalse(subPlugUp.isConnected());
//
//        assertEquals(socketLeft.getConnectedConnector(), subSocketLeft);
//        assertEquals(subSocketLeft.getConnectedConnector(), socketLeft);
//        assertTrue(socketLeft.isConnected());
//        assertTrue(subSocketLeft.isConnected());
//        socketLeft.disconnect();
//        assertNull(socketLeft.getConnectedConnector());
//        assertNull(subSocketLeft.getConnectedConnector());
//        assertFalse(socketLeft.isConnected());
//        assertFalse(subSocketLeft.isConnected());
//    }
//
//    @Test
//    void canHaveAsSocket() {
//        assertTrue(plugRight.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_LEFT, Type.SOCKET)));
//        assertTrue(plugDown.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_UP, Type.SOCKET)));
//        assertTrue(plugLeft.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_RIGHT, Type.SOCKET)));
//        assertTrue(plugUp.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_DOWN, Type.SOCKET)));
//
//        assertFalse(plugUp.canHaveAsSubConnector(null));
//
//        assertFalse(plugUp.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_LEFT, Type.SOCKET)));
//        assertFalse(plugUp.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_RIGHT, Type.SOCKET)));
//        assertFalse(plugUp.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_UP, Type.SOCKET)));
//
//        assertFalse(plugLeft.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_LEFT, Type.SOCKET)));
//        assertFalse(plugLeft.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_DOWN, Type.SOCKET)));
//        assertFalse(plugLeft.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_UP, Type.SOCKET)));
//
//        assertFalse(plugDown.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_LEFT, Type.SOCKET)));
//        assertFalse(plugDown.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_DOWN, Type.SOCKET)));
//        assertFalse(plugDown.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_RIGHT, Type.SOCKET)));
//
//        assertFalse(plugRight.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_DOWN, Type.SOCKET)));
//        assertFalse(plugRight.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_RIGHT, Type.SOCKET)));
//        assertFalse(plugRight.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_UP, Type.SOCKET)));
//
//        assertFalse(plugUp.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_DOWN, Type.PLUG)));
//        assertFalse(plugDown.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_UP, Type.PLUG)));
//        assertFalse(plugLeft.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_RIGHT, Type.PLUG)));
//        assertFalse(plugRight.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_LEFT, Type.PLUG)));
//        assertFalse(socketUp.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_DOWN, Type.SOCKET)));
//        assertFalse(socketDown.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_UP, Type.SOCKET)));
//        assertFalse(socketLeft.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_RIGHT, Type.SOCKET)));
//        assertFalse(socketRight.canHaveAsSubConnector(new SubConnector(block1, Orientation.FACING_LEFT, Type.SOCKET)));
//    }
//}