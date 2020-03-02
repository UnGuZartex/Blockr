package Main.GUI.GameWorld;

import Main.GUI.Painter;

import java.awt.*;
import java.awt.geom.Point2D;

public class GameWorldPainter extends Painter {

    private int cellSize;
    private int gridStartingPointX;
    private int gridStartingPointY;

    // TODO MOVE TO CONTROLLER!!!
    private int gridHeightInCells = 50;
    private int gridWidthInCells = 50;

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
        int cellSizeX = (Toolkit.getDefaultToolkit().getScreenSize().width - cornerX) / gridWidthInCells;
        gridStartingPointX = cornerX + ((Toolkit.getDefaultToolkit().getScreenSize().width - cornerX) - (cellSizeX * gridWidthInCells)) / 2;

        int cellSizeY = (Toolkit.getDefaultToolkit().getScreenSize().height - cornerY) / gridHeightInCells;
        gridStartingPointY = cornerY + ((Toolkit.getDefaultToolkit().getScreenSize().height - cornerY) - (cellSizeX * gridHeightInCells)) / 2;

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
