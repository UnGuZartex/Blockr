package GUI.Components;

import Controllers.LevelDataLoader;
import Controllers.RobotListener;
import GUI.Images.ImageLibrary;
import System.GameWorld.Cell;
import System.GameWorld.Direction;
import Utility.Position;

import java.awt.*;

public class GUIGrid implements RobotListener {

    private Position gridCellSize;
    private Position robotPos;
    private Direction robotDirection;
    private int cellSize;
    private int gridStartingPointX;
    private int gridStartingPointY;
    private Cell[][] cells;
    private int minGridDelta = 20;
    LevelDataLoader loader = new LevelDataLoader();

    public GUIGrid(int x, int y, int width, int height) {
        initializeData();
        calculateGridProperties(x, y, width, height);
    }

    private void initializeData() {
        robotPos = loader.getRobotPosition();
        robotDirection = loader.getRobotDirection();
        gridCellSize = loader.getGridSize();
        cells = loader.getGridCells();
        loader.subscribe(this);
    }

    private void calculateGridProperties(int x, int y, int width, int height) {
        cellSize = Math.min((width - minGridDelta) / gridCellSize.getX(), (height - minGridDelta) / gridCellSize.getY());
        gridStartingPointX = x + (width - (cellSize * gridCellSize.getX())) / 2;
        gridStartingPointY = y + (height - (cellSize * gridCellSize.getY())) / 2;
    }

    public void paint(Graphics g, ImageLibrary library) {
        drawGrid(g, library);
        drawRobot(g, library);
    }

    private void drawGrid(Graphics g, ImageLibrary library) {
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

    private void drawRobot(Graphics g, ImageLibrary library) {
        g.drawImage(library.getRobotImage(robotDirection.name()), gridStartingPointX + robotPos.getX() * cellSize, gridStartingPointY + robotPos.getY() * cellSize, cellSize, cellSize, null);
    }

    @Override
    public void onRobotMoved(Position newPosition) {
        robotPos = newPosition;
    }

    @Override
    public void onRobotChangedDirection(Direction newDirection) {
        System.err.println("CHANGED " + newDirection);
        robotDirection = newDirection;
    }
}
