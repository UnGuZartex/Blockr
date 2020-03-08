package GUI.Panel;

import Controllers.LevelDataLoader;
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
    private int mingridDelta = 20;

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
        g.setColor(Color.BLACK);
        drawBackground(g);
        drawGrid(g);
        drawRobot(g);
    }

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getGameWorldBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        g.drawRect(getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y);
    }

    private void calculateGridProperties() {
        cellSize = Math.min((getSize().x - mingridDelta) / gridCellSize.x, (getSize().y - mingridDelta) / gridCellSize.y);
        gridStartingPointX = getLeftCorner().x + (getSize().x - (cellSize * gridCellSize.x)) / 2;
        gridStartingPointY = getLeftCorner().y + (getSize().y - (cellSize * gridCellSize.y)) / 2;
    }

    private void drawGrid(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        for (int x = 0; x < gridCellSize.x; x++) {
            for (int y = 0; y < gridCellSize.y; y++) {
                g.drawImage(library.getImage(cells[x][y].getCellType().name()), gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize, null);
                g.drawRect(gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize);
            }
        }

        g2.setStroke(new BasicStroke(1));
    }

    private void drawRobot(Graphics g) {
        g.drawImage(library.getRobotImage(), gridStartingPointX + robotPos.x * cellSize, gridStartingPointY + robotPos.y * cellSize, cellSize, cellSize, null);
    }
}
