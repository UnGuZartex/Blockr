package System.GameWorld;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RobotTest {

    private int xUp, yUp, xDown, yDown, xLeft, yLeft, xRight, yRight;
    private Robot robotUp, robotDown, robotLeft, robotRight;

    @BeforeEach
    void setUp() {
        xUp = 0; yUp = 0;
        xDown = 0; yDown = 0;
        xLeft = 0; yLeft = 0;
        xRight = 0; yRight = 0;

        robotUp = new Robot(xUp, yUp, Direction.UP);
        robotDown = new Robot(xDown, yDown, Direction.DOWN);
        robotLeft = new Robot(xLeft, yLeft, Direction.LEFT);
        robotRight = new Robot(xRight, yRight, Direction.RIGHT);
    }

    @AfterEach
    void tearDown() {
        robotUp = null;
        robotDown = null;
        robotLeft = null;
        robotRight = null;
    }

    @Test
    void getDirection() {
        assertEquals(Direction.UP, robotUp.getDirection());
        assertEquals(Direction.DOWN, robotDown.getDirection());
        assertEquals(Direction.LEFT, robotLeft.getDirection());
        assertEquals(Direction.RIGHT, robotRight.getDirection());
    }

    @Test
    void getX() {
        assertEquals(xUp, robotUp.getX());
        assertEquals(xDown, robotDown.getX());
        assertEquals(xLeft, robotLeft.getX());
        assertEquals(xRight, robotDown.getX());
    }

    @Test
    void getY() {
        assertEquals(yUp, robotUp.getY());
        assertEquals(yDown, robotDown.getY());
        assertEquals(yLeft, robotLeft.getY());
        assertEquals(yRight, robotDown.getY());
    }

    @Test
    void moveForward() {
        robotUp.moveForward();
        assertEquals(xUp, robotUp.getX());
        assertEquals(yUp - 1, robotUp.getY());

        robotDown.moveForward();
        assertEquals(xDown, robotDown.getX());
        assertEquals(yDown + 1, robotDown.getY());

        robotLeft.moveForward();
        assertEquals(xLeft - 1, robotLeft.getX());
        assertEquals(yLeft, robotLeft.getY());

        robotRight.moveForward();
        assertEquals(xRight + 1, robotRight.getX());
        assertEquals(yRight, robotRight.getY());
    }

    @Test
    void turnLeft() {
        assertEquals(Direction.UP, robotUp.getDirection());
        robotUp.turnLeft();
        assertEquals(Direction.LEFT, robotUp.getDirection());

        assertEquals(Direction.DOWN, robotDown.getDirection());
        robotDown.turnLeft();
        assertEquals(Direction.RIGHT, robotDown.getDirection());

        assertEquals(Direction.LEFT, robotLeft.getDirection());
        robotLeft.turnLeft();
        assertEquals(Direction.DOWN, robotLeft.getDirection());

        assertEquals(Direction.RIGHT, robotRight.getDirection());
        robotRight.turnLeft();
        assertEquals(Direction.UP, robotRight.getDirection());
    }

    @Test
    void turnRight() {
        assertEquals(Direction.UP, robotUp.getDirection());
        robotUp.turnRight();
        assertEquals(Direction.RIGHT, robotUp.getDirection());

        assertEquals(Direction.DOWN, robotDown.getDirection());
        robotDown.turnRight();
        assertEquals(Direction.LEFT, robotDown.getDirection());

        assertEquals(Direction.LEFT, robotLeft.getDirection());
        robotLeft.turnRight();
        assertEquals(Direction.UP, robotLeft.getDirection());

        assertEquals(Direction.RIGHT, robotRight.getDirection());
        robotRight.turnRight();
        assertEquals(Direction.DOWN, robotRight.getDirection());
    }
}