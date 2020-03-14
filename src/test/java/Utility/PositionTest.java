package Utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    Position pos1, pos2, pos3;
    int y1, y2, y3;
    int x1, x2, x3;
    private static final int MAX_X = 20;
    private static final int MAX_Y = 20;
    private Random random;

    @BeforeEach
    void setUp() {
        random = new Random();
        x1 = random.nextInt(MAX_X) + 1;
        y1 = random.nextInt(MAX_Y) + 1;
        x2 = random.nextInt(MAX_X) + 1;
        y2 = random.nextInt(MAX_Y) + 1;
        x3 = random.nextInt(MAX_X) + 1;
        y3 = random.nextInt(MAX_Y) + 1;

        pos1 = new Position(x1, y1);
        pos2 = new Position(x2, y2);
        pos3 = new Position(x3, y3);
    }

    @AfterEach
    void tearDown() {
        random = null;

        pos1 = null;
        pos2 = null;
        pos3 = null;
    }

    @Test
    void getX() {
        assertEquals(x1, pos1.getX());
        assertEquals(x2, pos2.getX());
        assertEquals(x3, pos3.getX());
    }

    @Test
    void getY() {
        assertEquals(y1, pos1.getY());
        assertEquals(y2, pos2.getY());
        assertEquals(y3, pos3.getY());
    }

    @Test
    void getDistance() {
        double dist12 = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        assertEquals(dist12, pos1.getDistance(pos2), 0.001);
        assertEquals(dist12, pos2.getDistance(pos1), 0.001);

        double dist13 = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        assertEquals(dist13, pos1.getDistance(pos3), 0.001);
        assertEquals(dist13, pos3.getDistance(pos1), 0.001);

        double dist23 = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));
        assertEquals(dist23, pos3.getDistance(pos2), 0.001);
        assertEquals(dist23, pos2.getDistance(pos3), 0.001);
    }
}