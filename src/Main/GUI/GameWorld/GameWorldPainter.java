package Main.GUI.GameWorld;

import Main.GUI.Painter;

import java.awt.*;
import java.awt.geom.Point2D;

public class GameWorldPainter extends Painter {

    private int cellSize;
    private int gridStartingPointX;
    private int gridStartingPointY;

    // TODO MOVE TO CONTROLLER!!!
    private int gridHeightInCells = 10;
    private int gridWidthInCells = 10;

    public GameWorldPainter(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
        System.err.println("vergeet deze constructor dubbele oproep niet! " + width);
        calculateGridProperties();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        drawGrid(g);
    }

    private void calculateGridProperties() {
        int cellSizeX = width / gridWidthInCells;
        gridStartingPointX = cornerX + (width - (cellSizeX * gridWidthInCells)) / 2;

        int cellSizeY = height / gridHeightInCells;
        gridStartingPointY = cornerY + (height - (cellSizeX * gridHeightInCells)) / 2;

        cellSize = Math.min(cellSizeX, cellSizeY);
    }

    private void drawGrid(Graphics g) {
        for (int x = 0; x < gridWidthInCells; x++) {
            for (int y = 0; y < gridHeightInCells; y++) {
                g.drawRect(gridStartingPointX + x * cellSize, gridStartingPointY + y * cellSize, cellSize, cellSize);
            }
        }
    }
}
