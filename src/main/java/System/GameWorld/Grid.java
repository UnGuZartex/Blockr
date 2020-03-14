package System.GameWorld;

/**
 * A class to keep track of the grid of the game. This is
 * a matrix of cells.
 *
 * @invar A grid must have proper dimensions.
 *        | areProperDimensions(getWidth(), getHeight(), getCells())
 *
 * @author Alpha-team
 */
public class Grid {

    /**
     * Variable referring to the height of this grid.
     */
    private final int height;
    /**
     * Variable referring to the width of this grid.
     */
    private final int width;
    /**
     * Variable referring to the cells in this grid.
     */
    private final Cell[][] cells;

    /**
     * Initialise a new grid with given cells.
     *
     * @param cells The cells for this Grid.
     *
     * @post A new grid is initialised with the given cells and
     *       the height and width of this grid is set to the the
     *       dimensions of the given cells.
     *
     * @effect Calls constructor with given cells and its dimensions.
     */
    public Grid(Cell[][] cells) {
        this(cells.length, cells[0].length, cells);
    }

    /**
     * Initialise a new grid with given height and width.
     *
     * @param width The width of this grid.
     * @param height The height of this grid.
     *
     * @post A new grid is initialised with the given width and
     *       height. All the cells of this grid are set to the
     *       default cell for a grid.
     */
    public Grid(int width, int height) {
        this(width, height, new Cell[width][height]);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = getDefaultCell();
            }
        }
    }

    /**
     * Initialise a new grid with given width, height and cells.
     *
     * @param width The width of this grid.
     * @param height The height of this grid.
     * @param cells The cells for this Grid.
     *
     * @pre The given width and height should correspond to the
     *      dimensions of the given cells.
     *
     * @post The properties of this grid are set to the given
     *       parameters.
     *
     * @throws IllegalArgumentException
     *         When the given arguments are none valid dimensions.
     */
    protected Grid(int width, int height, Cell[][] cells) throws IllegalArgumentException{
        if (!areProperDimensions(width, height, cells)) {
            throw new IllegalArgumentException("The given dimensions are invalid");
        }
        this.height = height;
        this.width = width;
        this.cells = cells;
    }

    /**
     * Checks whether or not the given parameters are valid dimensions.
     *
     * @param width The width to check.
     * @param height The height to check.
     * @param cells The cells to check.
     *
     * @return True if and only if the given width and height are greater
     *         than 0, and the dimensions of the given cells correspond to
     *         the given width and height.
     */
    public static boolean areProperDimensions(int width, int height, Cell[][] cells) {
        return width > 0 && height > 0 &&
                cells.length == width &&
                cells[0].length == height;
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
     * @param x The x coordinate for the cell to return.
     * @param y The y coordinate for the cell to return.
     *
     * @pre The given index must be both greater than 0 and x must
     *      be smaller than width and y must be smaller than height.
     *
     * @return The cell in this grid at position [x,y] coordinate.
     */
    public Cell getCellAt(int x, int y) {
        return cells[x][y];
    }

    /**
     * Get the cells of this grid.
     *
     * @return A clone of the cells in this grid.
     */
    public Cell[][] getCells() {
        return cells.clone();
    }

    /**
     * Get the default Cell for a grid.
     *
     * @return A new Cell with BLANK CellType.
     */
    public static Cell getDefaultCell() {
        return new Cell(CellType.BLANK);
    }
}