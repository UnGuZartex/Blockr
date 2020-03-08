package System.GameWorld;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class DirectionTest {

    private Direction directionUp;
    private Direction directionDown;
    private Direction directionLeft;
    private Direction directionRight;

    @BeforeEach
    void setUp() {

        directionUp = Direction.UP;
        directionDown = Direction.DOWN;
        directionLeft = Direction.LEFT;
        directionRight = Direction.RIGHT;
    }

    @AfterEach
    void tearDown() {
        directionUp = null;
        directionDown = null;
        directionLeft = null;
        directionRight = null;
    }

    @Test
    void turnLeft() {
        assertEquals(Direction.LEFT, directionUp.turnLeft());
        assertEquals(Direction.RIGHT, directionDown.turnLeft());
        assertEquals(Direction.DOWN, directionLeft.turnLeft());
        assertEquals(Direction.UP, directionRight.turnLeft());

    }

    @Test
    void turnRight() {
        assertEquals(Direction.RIGHT, directionUp.turnRight());
        assertEquals(Direction.LEFT, directionDown.turnRight());
        assertEquals(Direction.UP, directionLeft.turnRight());
        assertEquals(Direction.DOWN, directionRight.turnRight());
    }
}