package GameWorld;

import java.util.Arrays;

public class Grid {


    private final int height; /* The number of rows in this grid */
    private final int width; /* The number of columns in this grid */
    private Cell[][] cells; /* The cells in this grid */

    public Grid(Cell[][] cells) {
        this(cells.length, cells[0].length, cells);
    }

    public Grid(int width, int height) {
        this(width, height, new Cell[width][height]);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(CellType.BLANK);
            }
        }
    }

    private Grid(int width, int height, Cell[][] cells) {
        this.height = height;
        this.width = width;
        this.cells = cells;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // TODO LATER WEG DOEN!!!!!
    public void changeCell(int x, int y, CellType type) {
        cells[x][y] = new Cell(type);
    }

    public Cell getCellAt(int x, int y) {
        return cells[x][y];
    }
}
