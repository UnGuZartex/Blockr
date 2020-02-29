package Main.GameWorld;

import java.util.Arrays;

public class Grid {

    private final int height;
    private final int width;
    private Cell[][] cells;

    public Grid(int height, int width, Cell[][] cells) {
        this.height = height;
        this.width = width;
        this.cells = cells;
    }

    public Grid(int height, int width) {
        this(height, width, new Cell[width][height]);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(CellType.BLANK);
            }
        }
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCellAt(int x, int y) {
        return cells[x][y];
    }

}
