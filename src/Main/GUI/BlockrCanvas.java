package Main.GUI;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;

public class BlockrCanvas extends CanvasWindow {

    private Painter[] painters;

    /**
     * Initializes a CanvasWindow object.
     *
     * @param title Window title
     */
    protected BlockrCanvas(String title) {
        super(title);
        painters = new Painter[3];
        painters[0] = new PalettePainter(0,0, width/3,height);
        painters[1] = new ProgramAreaPainter(width/3,0, width/3,height);
        painters[2] = new GameWorldPainter(2*width/3,0, width/3,height);
    }


    protected void setDimensions(int width, int height) {
        this.height = 1070;
        this.width = 1910;
        painters[0] = new PalettePainter(0,0, width/3,height);
        painters[1] = new ProgramAreaPainter(width/3,0, width/3,height);
        painters[2] = new GameWorldPainter(2*width/3,0, width/3,height);
    }


    @Override
    protected void paint(Graphics g) {
        for(Painter painter : painters) {
            painter.paint(g);
        }

        g.drawRect(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y, 20, 20);
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);
        repaint();
    }
}
