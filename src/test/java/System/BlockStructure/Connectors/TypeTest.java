package System.BlockStructure.Connectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeTest {

    Type plug, socket;

    @BeforeEach
    void setUp() {
        plug = Type.PLUG;
        socket = Type.SOCKET;
    }

    @AfterEach
    void tearDown() {
        plug = null;
        socket = null;
    }

    @Test
    void canConnectWith() {
        assertTrue(plug.canConnectWith(Type.SOCKET));
        assertTrue(socket.canConnectWith(Type.PLUG));
        assertFalse(plug.canConnectWith(Type.PLUG));
        assertFalse(socket.canConnectWith(Type.SOCKET));
    }
}