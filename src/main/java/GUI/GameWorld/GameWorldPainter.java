package GUI.GameWorld;

import GUI.BlockrCanvas;
import GUI.Images.ImageLibrary;
import GUI.Images.ImagePreLoader;
import GUI.Painter;
import GameWorld.CellType;
import GameWorld.Grid;
import java.awt.*;

public class GameWorldPainter extends Painter {

    private int cellSize;
    private int gridStartingPointX;
    private int gridStartingPointY;
    private int mingridDelta = 20;

    // TODO MOVE TO CONTROLLER!!!
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
        drawBackground(g);
        drawGrid(g);
        drawRobot(g);
    }

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getGameWorldBackgroundImage(), cornerX, cornerY, width, height, null);
        g.drawRect(cornerX, cornerY, width, height);
    }

    private void calculateGridProperties() {
        cellSize = Math.min((width - mingridDelta) / gridWidthInCells, (height - mingridDelta) / gridHeightInCells);
        gridStartingPointX = cornerX + (width - (cellSize * gridWidthInCells)) / 2;
        gridStartingPointY = cornerY + (height - (cellSize * gridHeightInCells)) / 2;
    }

    private void populateGrid() {
        grid.changeCell(1, 0, CellType.WALL);
        grid.changeCell(2, 0, CellType.WALL);
        grid.changeCell(gridWidthInCells - 1, gridHeightInCells - 1, CellType.GOAL);
    }

    private void drawGrid(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        for (int x = 0; x < gridWidthInCells; x++) {
            for (int y = 0; y < gridHeightInCells; y++) {
                g.drawImage(library.getImage(grid.getCellAt(x, y).getCellType().name()), gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize, null);
                g.drawRect(gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize);
            }
        }

        g2.setStroke(new BasicStroke(1));
    }

    private void drawRobot(Graphics g) {
        g.drawImage(library.getRobotImage(), gridStartingPointX + robotPos.x * cellSize, gridStartingPointY + robotPos.y * cellSize, cellSize, cellSize, null);
    }
}
