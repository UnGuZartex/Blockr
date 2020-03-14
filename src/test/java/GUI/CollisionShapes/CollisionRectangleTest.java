package GUI.CollisionShapes;

import Utility.Position;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CollisionRectangleTest {

    CollisionRectangle rect1, rect2, rect3;
    int x1, y1, x2, y2, x3, y3;
    int height1, height2, height3;
    int width1, width2, width3;
    Color color1, color2, color3;
    Random random;
    final static int MAX_X = 10, MAX_Y = 10, MAX_WIDTH = 10, MAX_HEIGHT = 10;

    @BeforeEach
    void setUp() {
        random = new Random();
        x1 = random.nextInt(MAX_X) + 1;
        x2 = random.nextInt(MAX_X) + 1;
        x3 = random.nextInt(MAX_X) + 1;
        y1 = random.nextInt(MAX_Y) + 1;
        y2 = random.nextInt(MAX_Y) + 1;
        y3 = random.nextInt(MAX_Y) + 1;
        width1 = random.nextInt(MAX_WIDTH) + 1;
        height1 = random.nextInt(MAX_HEIGHT) + 1;
        width2 = random.nextInt(MAX_WIDTH) + 1;
        height2 = random.nextInt(MAX_HEIGHT) + 1;
        width3 = random.nextInt(MAX_WIDTH) + 1;
        height3 = random.nextInt(MAX_HEIGHT) + 1;

        color1 = Color.RED;
        color2 = Color.GREEN;
        color3 = Color.BLUE;

        rect1 = new CollisionRectangle(x1, y1, width1, height1, color1);
        rect2 = new CollisionRectangle(x2, y2, width2, height2, color2);
        rect3 = new CollisionRectangle(x3, y3, width3, height3, color3);
    }

    @AfterEach
    void tearDown() {
        random = null;

        color1 = null;
        color2 = null;
        color3 = null;

        rect1 = null;
        rect2 = null;
        rect3 = null;
    }

    @Test
    void getX() {
        assertEquals(x1, rect1.getX());
        assertEquals(x2, rect2.getX());
        assertEquals(x3, rect3.getX());
    }

    @Test
    void getY() {
        assertEquals(y1, rect1.getY());
        assertEquals(y2, rect2.getY());
        assertEquals(y3, rect3.getY());
    }

    @Test
    void setX() {
        int newX1 = random.nextInt(MAX_X) + 1;
        int newX2 = random.nextInt(MAX_X) + 1;
        int newX3 = random.nextInt(MAX_X) + 1;
        rect1.setX(newX1);
        rect2.setX(newX2);
        rect3.setX(newX3);
        assertEquals(newX1, rect1.getX());
        assertEquals(newX2, rect2.getX());
        assertEquals(newX3, rect3.getX());
    }

    @Test
    void setY() {
        int newY1 = random.nextInt(MAX_Y) + 1;
        int newY2 = random.nextInt(MAX_Y) + 1;
        int newY3 = random.nextInt(MAX_Y) + 1;
        rect1.setY(newY1);
        rect2.setY(newY2);
        rect3.setY(newY3);
        assertEquals(newY1, rect1.getY());
        assertEquals(newY2, rect2.getY());
        assertEquals(newY3, rect3.getY());
    }

    @Test
    void setPosition() {
        int newX1 = random.nextInt(MAX_X) + 1;
        int newX2 = random.nextInt(MAX_X) + 1;
        int newX3 = random.nextInt(MAX_X) + 1;
        int newY1 = random.nextInt(MAX_Y) + 1;
        int newY2 = random.nextInt(MAX_Y) + 1;
        int newY3 = random.nextInt(MAX_Y) + 1;
        rect1.setPosition(newX1, newY1);
        rect2.setPosition(newX2, newY2);
        rect3.setPosition(newX3, newY3);
        assertEquals(newX1, rect1.getX());
        assertEquals(newX2, rect2.getX());
        assertEquals(newX3, rect3.getX());
        assertEquals(newY1, rect1.getY());
        assertEquals(newY2, rect2.getY());
        assertEquals(newY3, rect3.getY());
    }

    @Test
    void getColor() {
        assertEquals(color1, rect1.getColor());
        assertEquals(color2, rect2.getColor());
        assertEquals(color3, rect3.getColor());
    }

    @Test
    void setColor() {
        assertEquals(color1, rect1.getColor());
        rect1.setColor(Color.BLACK);
        assertEquals(Color.BLACK, rect1.getColor());
    }

    @Test
    void translate() {
        int extraX1 = random.nextInt(MAX_X) + 1;
        int extraX2 = random.nextInt(MAX_X) + 1;
        int extraX3 = random.nextInt(MAX_X) + 1;
        int extraY1 = random.nextInt(MAX_Y) + 1;
        int extraY2 = random.nextInt(MAX_Y) + 1;
        int extraY3 = random.nextInt(MAX_Y) + 1;
        rect1.translate(extraX1, extraY1);
        rect2.translate(extraX2, extraY2);
        rect3.translate(extraX3, extraY3);
        assertEquals(x1 + extraX1, rect1.getX());
        assertEquals(x2 + extraX2, rect2.getX());
        assertEquals(x3 + extraX3, rect3.getX());
        assertEquals(y1 + extraY1, rect1.getY());
        assertEquals(y2 + extraY2, rect2.getY());
        assertEquals(y3 + extraY3, rect3.getY());
    }

    @Test
    void getPosition() {
        Position pos1 = rect1.getPosition();
        Position pos2 = rect2.getPosition();
        Position pos3 = rect3.getPosition();
        assertEquals(x1, pos1.getX());
        assertEquals(x2, pos2.getX());
        assertEquals(x3, pos3.getX());
        assertEquals(y1, pos1.getY());
        assertEquals(y2, pos2.getY());
        assertEquals(y3, pos3.getY());
    }

    @Test
    void getWidth() {
        assertEquals(width1, rect1.getWidth());
        assertEquals(width2, rect2.getWidth());
        assertEquals(width3, rect3.getWidth());
    }

    @Test
    void getHeight() {
        assertEquals(height1, rect1.getHeight());
        assertEquals(height2, rect2.getHeight());
        assertEquals(height3, rect3.getHeight());
    }

    @Test
    void setWidth() {
        int newWidth1 = random.nextInt(MAX_WIDTH) + 1;
        int newWidth2 = random.nextInt(MAX_WIDTH) + 1;
        int newWidth3 = random.nextInt(MAX_WIDTH) + 1;
        rect1.setWidth(newWidth1);
        rect2.setWidth(newWidth2);
        rect3.setWidth(newWidth3);
        assertEquals(newWidth1, rect1.getWidth());
        assertEquals(newWidth2, rect2.getWidth());
        assertEquals(newWidth3, rect3.getWidth());
    }

    @Test
    void setHeight() {
        int newHeight1 = random.nextInt(MAX_HEIGHT) + 1;
        int newHeight2 = random.nextInt(MAX_HEIGHT) + 1;
        int newHeight3 = random.nextInt(MAX_HEIGHT) + 1;
        rect1.setHeight(newHeight1);
        rect2.setHeight(newHeight2);
        rect3.setHeight(newHeight3);
        assertEquals(newHeight1, rect1.getHeight());
        assertEquals(newHeight2, rect2.getHeight());
        assertEquals(newHeight3, rect3.getHeight());
    }

    @Test
    void contains_rect() {
        CollisionRectangle rect1 = new CollisionRectangle(0,0,3,3, Color.RED);
        CollisionRectangle rect2 = new CollisionRectangle(1,1,2,2, Color.RED);
        assertTrue(rect1.contains(rect2));

        rect2 = new CollisionRectangle(0,1,2,2, Color.RED);
        assertTrue(rect1.contains(rect2));

        rect2 = new CollisionRectangle(0,1,3,2, Color.RED);
        assertTrue(rect1.contains(rect2));

        rect2 = new CollisionRectangle(5,5,1,1, Color.RED);
        assertFalse(rect1.contains(rect2));

        rect2 = new CollisionRectangle(4,1,5,3, Color.RED);
        assertFalse(rect1.contains(rect2));
    }

    @Test
    void contains_coordinates() {
        int x = random.nextInt(width1 + 1) + x1;
        int y = random.nextInt(height1 + 1) + y1;
        assertTrue(rect1.contains(x,y));
        assertTrue(rect1.contains(x1,y1));
        assertTrue(rect1.contains(x1 + width1,y1));
        assertTrue(rect1.contains(x1,y1 + height1));
        assertTrue(rect1.contains(x1 + width1,y1 + height1));

        assertFalse(rect1.contains(x1 - 1, y1));
        assertFalse(rect1.contains(x1, y1 - 1));
        assertFalse(rect1.contains(x1 + width1 + 1, y1));
        assertFalse(rect1.contains(x1, y1 + height1 + 1));
        assertFalse(rect1.contains(x1 + width1 + 1, y1 + height1 + 1));
        assertFalse(rect1.contains(x1 - 1, y1 - 1));
    }
}