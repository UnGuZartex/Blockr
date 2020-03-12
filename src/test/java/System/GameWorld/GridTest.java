package System.GameWorld;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

class GridTest {

    private Grid grid1, grid2, grid3, grid4, grid5, grid6;
    private Cell[][] cells4, cells5, cells6;
    private int height1, height2, height3, height4, height5, height6;
    private int width1, width2, width3, width4, width5, width6;
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
        width6 = random.nextInt(MAX_WIDTH) + 1;
        height6 = random.nextInt(MAX_HEIGHT) + 1;

        CellType[] cellTypes = CellType.values();
        cells4 = new Cell[width4][height4];
        for (int x = 0; x < width4; x++) {
            for (int y = 0; y < height4; y++) {
                cells4[x][y] = new Cell(cellTypes[random.nextInt(cellTypes.length)]);
            }
        }
        cells5 = new Cell[width5][height5];
        for (int x = 0; x < width5; x++) {
            for (int y = 0; y < height5; y++) {
                cells5[x][y] = new Cell(cellTypes[random.nextInt(cellTypes.length)]);
            }
        }
        cells6 = new Cell[width6][height6];
        for (int x = 0; x < width6; x++) {
            for (int y = 0; y < height6; y++) {
                cells6[x][y] = new Cell(cellTypes[random.nextInt(cellTypes.length)]);
            }
        }

        grid1 = new Grid(width1, height1);
        grid2 = new Grid(width2, height2);
        grid3 = new Grid(width3, height3);
        grid4 = new Grid(cells4);
        grid5 = new Grid(cells5);
        grid6 = new Grid(cells6);
    }

    @AfterEach
    void tearDown() {
        cells4 = null;
        cells5 = null;
        cells6 = null;

        grid1 = null;
        grid2 = null;
        grid3 = null;
        grid4 = null;
        grid5 = null;
        grid6 = null;
    }

    @Test
    void areProperDimensions() {
        assertTrue(Grid.areProperDimensions(1,1, new Cell[1][1]));
        assertTrue(Grid.areProperDimensions(5,2, new Cell[5][2]));
        assertTrue(Grid.areProperDimensions(2,5, new Cell[2][5]));

        assertFalse(Grid.areProperDimensions(0,0, new Cell[0][0]));
        assertFalse(Grid.areProperDimensions(0,-1, new Cell[0][0]));
        assertFalse(Grid.areProperDimensions(-1,0, new Cell[0][0]));
        assertFalse(Grid.areProperDimensions(2,5, new Cell[2][2]));
        assertFalse(Grid.areProperDimensions(5,2, new Cell[2][2]));
    }

    @Test
    void getHeight() {
        assertEquals(height1, grid1.getHeight());
        assertEquals(height2, grid2.getHeight());
        assertEquals(height3, grid3.getHeight());
        assertEquals(height4, grid4.getHeight());
        assertEquals(height5, grid5.getHeight());
        assertEquals(height6, grid6.getHeight());
    }

    @Test
    void getWidth() {
        assertEquals(width1, grid1.getWidth());
        assertEquals(width2, grid2.getWidth());
        assertEquals(width3, grid3.getWidth());
        assertEquals(width4, grid4.getWidth());
        assertEquals(width5, grid5.getWidth());
        assertEquals(width6, grid6.getWidth());
    }

    @Test
    void getCellAt() {
        for (int x = 0; x < width1; x++) {
            for (int y = 0; y < height1; y++) {
                assertEquals(Grid.getDefaultCell().getCellType(), grid1.getCellAt(x,y).getCellType());
            }
        }

        for (int x = 0; x < width2; x++) {
            for (int y = 0; y < height2; y++) {
                assertEquals(Grid.getDefaultCell().getCellType(), grid2.getCellAt(x,y).getCellType());
            }
        }

        for (int x = 0; x < width3; x++) {
            for (int y = 0; y < height3; y++) {
                assertEquals(Grid.getDefaultCell().getCellType(), grid3.getCellAt(x,y).getCellType());
            }
        }

        for (int x = 0; x < width4; x++) {
            for (int y = 0; y < height4; y++) {
                assertEquals(cells4[x][y], grid4.getCellAt(x,y));
            }
        }

        for (int x = 0; x < width5; x++) {
            for (int y = 0; y < height5; y++) {
                assertEquals(cells5[x][y], grid5.getCellAt(x,y));
            }
        }

        for (int x = 0; x < width6; x++) {
            for (int y = 0; y < height6; y++) {
                assertEquals(cells6[x][y], grid6.getCellAt(x,y));
            }
        }
    }

    @Test
    void getCells() {
        for (int x = 0; x < width1; x++) {
            for (int y = 0; y < height1; y++) {
                assertEquals(Grid.getDefaultCell().getCellType(), grid1.getCells()[x][y].getCellType());
            }
        }

        for (int x = 0; x < width2; x++) {
            for (int y = 0; y < height2; y++) {
                assertEquals(Grid.getDefaultCell().getCellType(), grid2.getCells()[x][y].getCellType());
            }
        }

        for (int x = 0; x < width3; x++) {
            for (int y = 0; y < height3; y++) {
                assertEquals(Grid.getDefaultCell().getCellType(), grid3.getCells()[x][y].getCellType());
            }
        }

        for (int x = 0; x < width4; x++) {
            for (int y = 0; y < height4; y++) {
                assertEquals(cells4[x][y].getCellType(), grid4.getCells()[x][y].getCellType());
            }
        }

        for (int x = 0; x < width5; x++) {
            for (int y = 0; y < height5; y++) {
                assertEquals(cells5[x][y].getCellType(), grid5.getCells()[x][y].getCellType());
            }
        }

        for (int x = 0; x < width6; x++) {
            for (int y = 0; y < height6; y++) {
                assertEquals(cells6[x][y].getCellType(), grid6.getCells()[x][y].getCellType());
            }
        }
    }

    @Test
    void getDefaultType() {
        assertEquals(CellType.BLANK, Grid.getDefaultCell().getCellType());
    }
}