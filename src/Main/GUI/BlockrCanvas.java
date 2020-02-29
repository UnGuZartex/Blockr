package Main.GUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class BlockrCanvas extends CanvasWindow {

    private Painter[] painters;
    private ArrayList<GUIBlock> testBlocks = new ArrayList<GUIBlock>();
    private GUIBlock draggedBlock;
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

        setPainters();
        initTestBlocks();
    }

    protected void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
        setPainters();
    }

    @Override
    protected void paint(Graphics g) {
        for(Painter painter : painters) {
            painter.paint(g);
        }

        for (GUIBlock block : testBlocks) {
            if (draggedBlock == null || !draggedBlock.equals(block)) {
                block.draw(g);
            }
        }

        if (draggedBlock != null) {
            draggedBlock.changePosition(mousePos.x + dragDelta.x, mousePos.y + dragDelta.y);
            draggedBlock.draw(g);
        }
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);

        mousePos = new Point(x, y);

        if (id == MouseEvent.MOUSE_PRESSED && draggedBlock == null && testBlocks.stream().anyMatch(b -> b.contains(mousePos))) {
            OptionalInt blockIndex = IntStream.range(0, testBlocks.size()).filter(i -> testBlocks.get(i).contains(mousePos)).reduce((first, second)-> second);
            draggedBlock = testBlocks.get(blockIndex.getAsInt());
            testBlocks.remove(blockIndex.getAsInt());
            testBlocks.add(draggedBlock);
            dragDelta = new Point(draggedBlock.getPosition().x - x,
                    draggedBlock.getPosition().y - y);
        }
        if (id == MouseEvent.MOUSE_RELEASED && draggedBlock != null) {
            draggedBlock.changePosition(dragDelta.x + x, dragDelta.y + y);
            System.out.println(testBlocks.stream().filter(b-> b != draggedBlock && b.collidesWith(draggedBlock.getPolygon())).findFirst().orElse(null));
            draggedBlock = null;
        }

        repaint();
    }

    private void initTestBlocks() {
        testBlocks.add(new GUIBlock(500, 500, 500, 250, Color.GREEN));
        testBlocks.add(new GUIBlock(200, 500, 400, 300, Color.BLUE));
        testBlocks.add(new GUIBlock(500, 300, 300, 100, Color.MAGENTA));
    }

    private void setPainters() {
        painters[0] = new PalettePainter(0,0, width / 3, height);
        painters[1] = new ProgramAreaPainter(width / 3,0, width / 3, height);
        painters[2] = new GameWorldPainter(2 * width / 3,0, width / 3, height);
    }
}
