package GUI;

import GUI.Painters.Painter;
import GUI.Panel.GamePanel;
import GUI.Panel.GameWorldPanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class BlockrCanvas extends CanvasWindow {

    public static final double PALETTE_WIDTH_RATIO = 0.2;
    public static final double PROGRAMAREA_WIDTH_RATIO = 0.4;
    public static final double GAMEWORLD_WIDTH_RATIO = 0.4;

    private GamePanel[] panels;
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
        panels = new GamePanel[3];

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

        g.setColor(Color.lightGray);
        g.fillRect(0, 0, width, height);

        for (GamePanel panel : panels) {
            panel.paint(g);
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
        panels[0] = new PalettePanel(0, 0, (int)(width * PALETTE_WIDTH_RATIO), height);
        panels[1] = new ProgramAreaPanel((int)(width * PALETTE_WIDTH_RATIO),0, (int)(width * PROGRAMAREA_WIDTH_RATIO), height);
        panels[2] = new GameWorldPanel((int)(width * PALETTE_WIDTH_RATIO) + (int)(width * PROGRAMAREA_WIDTH_RATIO),0, (int)(width * GAMEWORLD_WIDTH_RATIO), height);
    }
}
