package System.BlockStructure.Connectors;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrientationTest {

    Orientation facingUp;
    Orientation facingDown;
    Orientation facingLeft;
    Orientation facingRight;

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
        Assert.assertFalse(facingUp.isOpposite(Orientation.FACING_UP));
        Assert.assertTrue(facingUp.isOpposite(Orientation.FACING_DOWN));
        Assert.assertFalse(facingUp.isOpposite(Orientation.FACING_LEFT));
        Assert.assertFalse(facingUp.isOpposite(Orientation.FACING_RIGHT));

        Assert.assertTrue(facingDown.isOpposite(Orientation.FACING_UP));
        Assert.assertFalse(facingDown.isOpposite(Orientation.FACING_DOWN));
        Assert.assertFalse(facingDown.isOpposite(Orientation.FACING_LEFT));
        Assert.assertFalse(facingDown.isOpposite(Orientation.FACING_RIGHT));

        Assert.assertFalse(facingLeft.isOpposite(Orientation.FACING_UP));
        Assert.assertFalse(facingLeft.isOpposite(Orientation.FACING_DOWN));
        Assert.assertFalse(facingLeft.isOpposite(Orientation.FACING_LEFT));
        Assert.assertTrue(facingLeft.isOpposite(Orientation.FACING_RIGHT));

        Assert.assertFalse(facingRight.isOpposite(Orientation.FACING_UP));
        Assert.assertFalse(facingRight.isOpposite(Orientation.FACING_DOWN));
        Assert.assertTrue(facingRight.isOpposite(Orientation.FACING_LEFT));
        Assert.assertFalse(facingRight.isOpposite(Orientation.FACING_RIGHT));
    }
}