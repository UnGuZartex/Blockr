package System.GameWorld;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

class GridTest {

    private Grid grid1, grid2, grid3, grid4, grid5;
    private Cell[][] cells1, cells2, cells3, cells4, cells5;
    private int height1, height2, height3, height4, height5;
    private int width1, width2, width3, width4, width5;
    private static final int MAX_WIDTH = 20;
    private static final int MAX_HEIGHT = 20;
    private Random random;

    @BeforeEach
    void setUp() { // TODO test with cell matrix given and extra methods
        random = new Random();
        width1 = random.nextInt(MAX_WIDTH) + 1;
        height1 = random.nextInt(MAX_HEIGHT) + 1;
        width2 = random.nextInt(MAX_WIDTH) + 1;
        height2 = random.nextInt(MAX_HEIGHT) + 1;
        width3 = random.nextInt(MAX_WIDTH) + 1;
        height3 = random.nextInt(MAX_HEIGHT) + 1;
        width4 = random.nextInt(MAX_WIDTH) + 1;
        height4 = random.nextInt(MAX_HEIGHT) + 1;
        width5 = random.nextInt(MAX_WIDTH) + 1;
        height5 = random.nextInt(MAX_HEIGHT) + 1;

        grid1 = new Grid(width1, height1);
        grid2 = new Grid(width2, height2);
        grid3 = new Grid(width3, height3);
        grid4 = new Grid(width4, height4);
        grid5 = new Grid(width5, height5);
    }

    @AfterEach
    void tearDown() {
        grid1 = null;
        grid2 = null;
        grid3 = null;
        grid4 = null;
        grid5 = null;
    }

    @Test
    void getHeight() {
        Assert.assertEquals(height1, grid1.getHeight());
        Assert.assertEquals(height2, grid2.getHeight());
        Assert.assertEquals(height3, grid3.getHeight());
        Assert.assertEquals(height4, grid4.getHeight());
        Assert.assertEquals(height5, grid5.getHeight());
    }

    @Test
    void getWidth() {
        Assert.assertEquals(width1, grid1.getWidth());
        Assert.assertEquals(width2, grid2.getWidth());
        Assert.assertEquals(width3, grid3.getWidth());
        Assert.assertEquals(width4, grid4.getWidth());
        Assert.assertEquals(width5, grid5.getWidth());
    }

    @Test
    void changeCell() {
    }

    @Test
    void getCellAt() {
    }
}