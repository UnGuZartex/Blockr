package GUI.Panel;

import Controllers.LevelDataLoader;
import System.GameWorld.Cell;
import System.GameWorld.Direction;
import Utility.Position;

import java.awt.*;

public class GameWorldPanel extends GamePanel {

    private LevelDataLoader loader = new LevelDataLoader();
    private Position gridCellSize;
    private Position robotPos;
    private Direction robotDirection;
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
        robotDirection = loader.getRobotDirection();
        gridCellSize = loader.getGridSize();
        cells = loader.getGridCells();
    }

    @Override
    public void paint(Graphics g) {
        loader.getRobotPosition();
        drawBackground(g);
        drawGrid(g);
        drawRobot(g);
    }

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getGameWorldBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        panelRectangle.paintNonFill(g);
    }

    private void calculateGridProperties() {
        cellSize = Math.min((getSize().x - mingridDelta) / gridCellSize.getX(), (getSize().y - mingridDelta) / gridCellSize.getY());
        gridStartingPointX = getLeftCorner().x + (getSize().x - (cellSize * gridCellSize.getX())) / 2;
        gridStartingPointY = getLeftCorner().y + (getSize().y - (cellSize * gridCellSize.getY())) / 2;
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.black);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        for (int x = 0; x < gridCellSize.getX(); x++) {
            for (int y = 0; y < gridCellSize.getY(); y++) {
                g.drawImage(library.getImage(cells[x][y].getCellType().name()), gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize, null);
                g.drawRect(gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize);
            }
        }

        g2.setStroke(new BasicStroke(1));
    }

    private void drawRobot(Graphics g) {
        g.drawImage(library.getRobotImage(robotDirection.name()), gridStartingPointX + robotPos.getX() * cellSize, gridStartingPointY + robotPos.getY() * cellSize, cellSize, cellSize, null);
    }
}
