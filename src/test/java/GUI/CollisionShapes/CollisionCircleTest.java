package GUI.CollisionShapes;

import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CollisionCircleTest {

    CollisionCircle circle1, circle2, circle3;
    int x1, y1, x2, y2, x3, y3;
    int r1, r2, r3;
    Color color1, color2, color3;
    Random random;
    final static int MAX_X = 10, MAX_Y = 10, MAX_R = 10;

    @BeforeEach
    void setUp() {
        random = new Random();
        x1 = random.nextInt(MAX_X) + 1;
        x2 = random.nextInt(MAX_X) + 1;
        x3 = random.nextInt(MAX_X) + 1;
        y1 = random.nextInt(MAX_Y) + 1;
        y2 = random.nextInt(MAX_Y) + 1;
        y3 = random.nextInt(MAX_Y) + 1;
        r1 = random.nextInt(MAX_R) + 1;
        r2 = random.nextInt(MAX_R) + 1;
        r3 = random.nextInt(MAX_R) + 1;

        color1 = Color.RED;
        color2 = Color.GREEN;
        color3 = Color.BLUE;

        circle1 = new CollisionCircle(x1, y1, r1, color1);
        circle2 = new CollisionCircle(x2, y2, r2, color2);
        circle3 = new CollisionCircle(x3, y3, r3, color3);
    }

    @AfterEach
    void tearDown() {
        random = null;

        color1 = null;
        color2 = null;
        color3 = null;

        circle1 = null;
        circle2 = null;
        circle3 = null;
    }

    @Test
    void getX() {
        assertEquals(x1, circle1.getX());
        assertEquals(x2, circle2.getX());
        assertEquals(x3, circle3.getX());
    }

    @Test
    void getY() {
        assertEquals(y1, circle1.getY());
        assertEquals(y2, circle2.getY());
        assertEquals(y3, circle3.getY());
    }

    @Test
    void setX() {
        int newX1 = random.nextInt(MAX_X) + 1;
        int newX2 = random.nextInt(MAX_X) + 1;
        int newX3 = random.nextInt(MAX_X) + 1;
        circle1.setX(newX1);
        circle2.setX(newX2);
        circle3.setX(newX3);
        assertEquals(newX1, circle1.getX());
        assertEquals(newX2, circle2.getX());
        assertEquals(newX3, circle3.getX());
    }

    @Test
    void setY() {
        int newY1 = random.nextInt(MAX_Y) + 1;
        int newY2 = random.nextInt(MAX_Y) + 1;
        int newY3 = random.nextInt(MAX_Y) + 1;
        circle1.setY(newY1);
        circle2.setY(newY2);
        circle3.setY(newY3);
        assertEquals(newY1, circle1.getY());
        assertEquals(newY2, circle2.getY());
        assertEquals(newY3, circle3.getY());
    }

    @Test
    void setPosition() {
        int newX1 = random.nextInt(MAX_X) + 1;
        int newX2 = random.nextInt(MAX_X) + 1;
        int newX3 = random.nextInt(MAX_X) + 1;
        int newY1 = random.nextInt(MAX_Y) + 1;
        int newY2 = random.nextInt(MAX_Y) + 1;
        int newY3 = random.nextInt(MAX_Y) + 1;
        circle1.setPosition(newX1, newY1);
        circle2.setPosition(newX2, newY2);
        circle3.setPosition(newX3, newY3);
        assertEquals(newX1, circle1.getX());
        assertEquals(newX2, circle2.getX());
        assertEquals(newX3, circle3.getX());
        assertEquals(newY1, circle1.getY());
        assertEquals(newY2, circle2.getY());
        assertEquals(newY3, circle3.getY());
    }

    @Test
    void translate() {
        int extraX1 = random.nextInt(MAX_X) + 1;
        int extraX2 = random.nextInt(MAX_X) + 1;
        int extraX3 = random.nextInt(MAX_X) + 1;
        int extraY1 = random.nextInt(MAX_Y) + 1;
        int extraY2 = random.nextInt(MAX_Y) + 1;
        int extraY3 = random.nextInt(MAX_Y) + 1;
        circle1.translate(extraX1, extraY1);
        circle2.translate(extraX2, extraY2);
        circle3.translate(extraX3, extraY3);
        assertEquals(x1 + extraX1, circle1.getX());
        assertEquals(x2 + extraX2, circle2.getX());
        assertEquals(x3 + extraX3, circle3.getX());
        assertEquals(y1 + extraY1, circle1.getY());
        assertEquals(y2 + extraY2, circle2.getY());
        assertEquals(y3 + extraY3, circle3.getY());
    }

    @Test
    void getPosition() {
        Position pos1 = circle1.getPosition();
        Position pos2 = circle2.getPosition();
        Position pos3 = circle3.getPosition();
        assertEquals(x1, pos1.getX());
        assertEquals(x2, pos2.getX());
        assertEquals(x3, pos3.getX());
        assertEquals(y1, pos1.getY());
        assertEquals(y2, pos2.getY());
        assertEquals(y3, pos3.getY());
    }

    @Test
    void getRadius() {
        assertEquals(r1, circle1.getRadius());
        assertEquals(r2, circle2.getRadius());
        assertEquals(r3, circle3.getRadius());
    }

    @Test
    void setRadius() {
        int newR1 = random.nextInt(MAX_R) + 1;
        int newR2 = random.nextInt(MAX_R) + 1;
        int newR3 = random.nextInt(MAX_R) + 1;
        circle1.setRadius(newR1);
        circle2.setRadius(newR2);
        circle3.setRadius(newR3);
        assertEquals(newR1, circle1.getRadius());
        assertEquals(newR2, circle2.getRadius());
        assertEquals(newR3, circle3.getRadius());
    }

    @Test
    void intersects() {
        assertTrue(circle1.intersects(circle1)); // Same circle

        circle2.setPosition(x1 + r1, y1);
        assertTrue(circle1.intersects(circle2)); // Kissing circle

        double angle = (2*Math.PI) * random.nextDouble();
        double r = (r1) * random.nextDouble();
        circle2.setPosition(x1, y1);
        circle2.setRadius(r1*3);
        circle2.translate((int)Math.floor(r*Math.cos(angle)), (int)Math.floor( r*Math.sin(angle)));
        assertTrue(circle1.intersects(circle2));
    }

    @Test
    void contains() {
        double angle = (2*Math.PI) * random.nextDouble();
        double r = (r1) * random.nextDouble();
        assertTrue(circle1.contains(x1, y1));
        assertTrue(circle1.contains(x1 + r1, y1));
        assertTrue(circle1.contains(x1, y1 + r1));
        assertTrue(circle1.contains((int)Math.floor(x1 + r*Math.cos(angle)), (int)Math.floor(y1 + r*Math.sin(angle))));

        r = 2*r1 + (r1) * random.nextDouble();
        assertFalse(circle1.contains(x1 + r1 + 1, y1));
        assertFalse(circle1.contains(x1, y1 + r1 + 1));
        assertFalse(circle1.contains((int)Math.ceil(x1 + r*Math.cos(angle)), (int)Math.ceil(y1 + r*Math.sin(angle))));
    }
}