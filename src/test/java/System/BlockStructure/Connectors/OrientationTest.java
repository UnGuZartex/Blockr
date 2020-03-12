package System.BlockStructure.Connectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrientationTest {

    private Orientation facingUp, facingDown, facingLeft, facingRight;

    @BeforeEach
    void setUp() {
        facingUp = Orientation.FACING_UP;
        facingDown = Orientation.FACING_DOWN;
        facingLeft = Orientation.FACING_LEFT;
        facingRight = Orientation.FACING_RIGHT;
    }

    @AfterEach
    void tearDown() {
        facingUp = null;
        facingDown = null;
        facingLeft = null;
        facingRight = null;
    }

    @Test
    void isOpposite() {
        assertFalse(facingUp.isOpposite(Orientation.FACING_UP));
        assertTrue(facingUp.isOpposite(Orientation.FACING_DOWN));
        assertFalse(facingUp.isOpposite(Orientation.FACING_LEFT));
        assertFalse(facingUp.isOpposite(Orientation.FACING_RIGHT));

        assertTrue(facingDown.isOpposite(Orientation.FACING_UP));
        assertFalse(facingDown.isOpposite(Orientation.FACING_DOWN));
        assertFalse(facingDown.isOpposite(Orientation.FACING_LEFT));
        assertFalse(facingDown.isOpposite(Orientation.FACING_RIGHT));

        assertFalse(facingLeft.isOpposite(Orientation.FACING_UP));
        assertFalse(facingLeft.isOpposite(Orientation.FACING_DOWN));
        assertFalse(facingLeft.isOpposite(Orientation.FACING_LEFT));
        assertTrue(facingLeft.isOpposite(Orientation.FACING_RIGHT));

        assertFalse(facingRight.isOpposite(Orientation.FACING_UP));
        assertFalse(facingRight.isOpposite(Orientation.FACING_DOWN));
        assertTrue(facingRight.isOpposite(Orientation.FACING_LEFT));
        assertFalse(facingRight.isOpposite(Orientation.FACING_RIGHT));
    }
}