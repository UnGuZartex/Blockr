package System.GameWorld;

public class Grid {

    private final int height;
    private final int width;
    private final Cell[][] cells;

    public Grid(Cell[][] cells) {
        this(cells.length, cells[0].length, cells);
    }

    public Grid(int width, int height) {
        this(width, height, new Cell[width][height]);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell(getDefaultType());
            }
        }
    }

    private Grid(int width, int height, Cell[][] cells) {
        this.height = height;
        this.width = width;
        this.cells = cells;
    }

    /**
     * Get the height of this grid.
     *
     * @return The height of this grid.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the width of this grid.
     *
     * @return The width of this grid.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the cell in this grid at the given indices.
     *
     * @param x
     *        The x coordinate for the cell to return.
     * @param y
     *        The y coordinate for the cell to return.
     *
     * TODO: add pre
     *
     * @return The cell in this grid at position (x,y).
     */
    public Cell getCellAt(int x, int y) {
        return cells[x][y];
    }

    public Cell[][] getCells() {
        return cells.clone();
    }

    /**
     * Get the default CellType for a cell in the grid.
     *
     * @return The BLANK CellType.
     */
    public static CellType getDefaultType() {
        return CellType.BLANK;
    }
}
