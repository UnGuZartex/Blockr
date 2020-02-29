package Main.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

public class BlockrCanvas extends CanvasWindow {

    private Painter[] painters;
    private ArrayList<Rectangle> testBlocks = new ArrayList<Rectangle>();
    private Rectangle draggedRectangle;
    private Point dragDelta;
    private Point mousePos;

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
        initTestBlocks();
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

        for (Rectangle rect : testBlocks) {
            if (draggedRectangle != null && draggedRectangle.equals(rect)) {
                g.drawRect(mousePos.x + dragDelta.x,
                        mousePos.y + dragDelta.y,
                        rect.width, rect.height);
            }
            else {
                g.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);

        mousePos = new Point(x, y);

        if (id == MouseEvent.MOUSE_PRESSED && draggedRectangle == null && testBlocks.stream().anyMatch(b -> b.contains(mousePos))) {
            System.err.println("INSIDE: " + mousePos +" \n");
            draggedRectangle = testBlocks.stream().filter(b -> b.contains(mousePos)).findFirst().orElse(null);
            dragDelta = new Point(draggedRectangle.x - x,
                    draggedRectangle.y - y);
        }
        if (id == MouseEvent.MOUSE_RELEASED && draggedRectangle != null) {
            draggedRectangle.x = dragDelta.x + x;
            draggedRectangle.y = dragDelta.y + y;
            draggedRectangle = null;
        }

        repaint();
    }

    private void initTestBlocks() {
        testBlocks.add(new Rectangle(700, 700, 250, 250));
        testBlocks.add(new Rectangle(200, 500, 100, 300));
        testBlocks.add(new Rectangle(500, 300, 400, 400));
        testBlocks.add(new Rectangle(500, 850, 100, 50));
    }
}
