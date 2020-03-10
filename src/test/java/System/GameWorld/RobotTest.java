package System.GameWorld;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

    private int xUp, yUp, xDown, yDown, xLeft, yLeft, xRight, yRight;
    private Robot robotUp, robotDown, robotLeft, robotRight;
    private Random random;
    private static final int MIN_X = 1;
    private static final int MAX_X = 20;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 20;

    @BeforeEach
    void setUp() {
        random = new Random();
        xUp = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        yUp = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        xDown = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        yDown = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        xLeft = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        yLeft = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;
        xRight = random.nextInt(MAX_X + 1 - MIN_X) + MIN_X;
        yRight = random.nextInt(MAX_Y + 1 - MIN_Y) + MIN_Y;

        robotUp = new Robot(xUp, yUp, Direction.UP);
        robotDown = new Robot(xDown, yDown, Direction.DOWN);
        robotLeft = new Robot(xLeft, yLeft, Direction.LEFT);
        robotRight = new Robot(xRight, yRight, Direction.RIGHT);
    }

    @AfterEach
    void tearDown() {
        random = null;

        robotUp = null;
        robotDown = null;
        robotLeft = null;
        robotRight = null;
    }

    @Test
    void isValidCoordinates() {
        assertTrue(Robot.isValidCoordinates(1,1));
        assertTrue(Robot.isValidCoordinates(0,1));
        assertTrue(Robot.isValidCoordinates(1,0));
        assertTrue(Robot.isValidCoordinates(0,0));

        assertFalse(Robot.isValidCoordinates(-1,1));
        assertFalse(Robot.isValidCoordinates(1,-1));
        assertFalse(Robot.isValidCoordinates(-1,-1));
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
        assertEquals(xRight, robotRight.getX());
    }

    @Test
    void getXForward() {
        assertEquals(xUp, robotUp.getXForward());
        assertEquals(xDown, robotDown.getXForward());
        assertEquals(xLeft - 1, robotLeft.getXForward());
        assertEquals(xRight + 1, robotRight.getXForward());
    }

    @Test
    void getY() {
        assertEquals(yUp, robotUp.getY());
        assertEquals(yDown, robotDown.getY());
        assertEquals(yLeft, robotLeft.getY());
        assertEquals(yRight, robotRight.getY());
    }

    @Test
    void getYForward(){
        assertEquals(yUp - 1, robotUp.getYForward());
        assertEquals(yDown + 1, robotDown.getYForward());
        assertEquals(yLeft, robotLeft.getYForward());
        assertEquals(yRight, robotRight.getYForward());
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