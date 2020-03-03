package Main.GUI.GameWorld;

import Main.GUI.ImagePreLoader;
import Main.GUI.Painter;
import Main.GameWorld.Cell;
import Main.GameWorld.CellType;
import Main.GameWorld.Grid;

import java.awt.*;

public class GameWorldPainter extends Painter {

    private int cellSize;
    private int gridStartingPointX;
    private int gridStartingPointY;

    // TODO MOVE TO CONTROLLER!!!
    // TODO FOUT BIJ KORTE LANGE GRIDS VERKEERDE VERPLAATSING
    private int gridHeightInCells = 5;
    private int gridWidthInCells = 5;
    private Grid grid = new Grid(gridWidthInCells, gridHeightInCells);
    private Point robotPos = new Point(0, 0);

    public GameWorldPainter(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
        System.err.println("vergeet deze constructor dubbele oproep niet! " + width);
        calculateGridProperties();
        populateGrid();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        drawBackground(g);
        drawGrid(g);
        drawRobot(g);
    }

    private void calculateGridProperties() {
        int cellSizeX = width / gridWidthInCells;
        gridStartingPointX = cornerX + (width - (cellSizeX * gridWidthInCells)) / 2;

        int cellSizeY = height / gridHeightInCells;
        gridStartingPointY = cornerY + (height - (cellSizeX * gridHeightInCells)) / 2;

        cellSize = Math.min(cellSizeX, cellSizeY);
    }

    private void populateGrid() {
        grid.changeCell(1, 0, CellType.WALL);
        grid.changeCell(2, 0, CellType.WALL);
        grid.changeCell(gridWidthInCells - 1, gridHeightInCells - 1, CellType.GOAL);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(ImagePreLoader.getGameWorldBackgroundImage(), cornerX, cornerY, width, height, null);
    }

    private void drawGrid(Graphics g) {
        for (int x = 0; x < gridWidthInCells; x++) {
            for (int y = 0; y < gridHeightInCells; y++) {
                g.drawImage(ImagePreLoader.getCellImage(grid.getCellAt(x, y).getCellType().name()), gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize, null);
                g.drawRect(gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize);
            }
        }
    }

    private void drawRobot(Graphics g) {
        //System.err.println("\nPAINT: " + ImagePreLoader.getRobotImage());
        g.drawImage(ImagePreLoader.getRobotImage(), gridStartingPointX + robotPos.x * cellSize, gridStartingPointY + robotPos.y * cellSize, cellSize, cellSize, null);
    }
}
