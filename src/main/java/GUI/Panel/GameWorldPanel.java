package GUI.Panel;

import Controllers.LevelDataLoader;
import GUI.ImagePreLoader;
import System.GameWorld.Cell;

import java.awt.*;

public class GameWorldPanel extends GamePanel {

    private LevelDataLoader loader = new LevelDataLoader();
    private Point gridCellSize;
    private Point robotPos;
    private int cellSize;
    private int gridStartingPointX;
    private int gridStartingPointY;
    private Cell[][] cells;

    public GameWorldPanel(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
        initializeData();
        calculateGridProperties();
    }

    private void initializeData() {
        robotPos = loader.getRobotPosition();
        gridCellSize = loader.getGridSize();
        cells = loader.getGridCells();
    }

    @Override
    public void paint(Graphics g) {
        initializeData();
        g.setColor(Color.black);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        drawBackground(g);
        drawGrid(g);
        drawRobot(g);
    }

    private void calculateGridProperties() {
        Point size = getSize();
        Point leftCorner = getLeftCorner();

        int cellSizeX = size.x / gridCellSize.x;
        gridStartingPointX = leftCorner.x + (size.x - (cellSizeX * gridCellSize.x)) / 2;

        int cellSizeY = size.y / gridCellSize.y;
        gridStartingPointY = leftCorner.y + (size.y - (cellSizeX * gridCellSize.y)) / 2;

        cellSize = Math.min(cellSizeX, cellSizeY);
    }

    private void drawBackground(Graphics g) {
        g.drawImage(ImagePreLoader.getGameWorldBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
    }

    private void drawGrid(Graphics g) {
        for (int x = 0; x < gridCellSize.x; x++) {
            for (int y = 0; y < gridCellSize.y; y++) {
                g.drawImage(ImagePreLoader.getCellImage(cells[x][y].getCellType().name()), gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize, null);
                g.drawRect(gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize);
            }
        }
    }

    private void drawRobot(Graphics g) {
        g.drawImage(ImagePreLoader.getRobotImage(), gridStartingPointX + robotPos.x * cellSize, gridStartingPointY + robotPos.y * cellSize, cellSize, cellSize, null);
    }
}
