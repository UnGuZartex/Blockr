package GUI;


import GUI.BlockShape.CollisionCircle;
import GUI.Components.GUIBlock;
import GUI.Components.GUIBlock2;
import GUI.Components.GUIBlockHandler;
import GUI.Images.ImageLibrary;
import Controllers.ProgramController;
import GUI.Panel.GamePanel;
import GUI.Panel.GameWorldPanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class BlockrCanvas extends CanvasWindow {

    public static final double PALETTE_WIDTH_RATIO = 0.2;
    public static final double PROGRAMAREA_WIDTH_RATIO = 0.4;
    public static final double GAMEWORLD_WIDTH_RATIO = 0.4;

    private GamePanel[] panels;
    private ArrayList<GUIBlock2> testBlocks = new ArrayList<GUIBlock2>();
    private GUIBlock2 draggedBlock;
    private Point dragDelta;
    private Point mousePos;
    private GUIBlockHandler blockHandler;

    private ProgramController programController = new ProgramController();


    /**
     * Initializes a CanvasWindow object. 
     *
     * @param title Window title
     */
    protected BlockrCanvas(String title, ImageLibrary library) {
        super(title);
        panels = new GamePanel[3];

        GamePanel.setImageLibrary(library);
        setPainters();
        initTestBlocks();
        blockHandler = new GUIBlockHandler();
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
        g.setColor(Color.black);

        for (GamePanel panel : panels) {
            panel.paint(g);
        }

        blockHandler.paint(g);
    }

    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount) {
        super.handleMouseEvent(id, x, y, clickCount);
        blockHandler.handleMouseEvent(id, x, y);
        repaint();

        CollisionCircle circle1 = new CollisionCircle(0, 0,19, 0, Color.black );
        CollisionCircle circle2 = new CollisionCircle(0, 21,1, 0, Color.black );
        System.err.println(circle1.intersects(circle2));
    }

    private void initTestBlocks() {
        testBlocks.add(new GUIBlock2(500, 500, 500, 250, Color.GREEN));
        testBlocks.add(new GUIBlock2(200, 500, 400, 300, Color.BLUE));
        testBlocks.add(new GUIBlock2(500, 300, 300, 100, Color.MAGENTA));
    }

    private void setPainters() {
        panels[0] = new PalettePanel(0, 0, (int)(width * PALETTE_WIDTH_RATIO), height);
        panels[1] = new ProgramAreaPanel((int)(width * PALETTE_WIDTH_RATIO),0, (int)(width * PROGRAMAREA_WIDTH_RATIO), height);
        panels[2] = new GameWorldPanel((int)(width * PALETTE_WIDTH_RATIO) + (int)(width * PROGRAMAREA_WIDTH_RATIO),0, (int)(width * GAMEWORLD_WIDTH_RATIO), height);
    }

    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar) {
        if (keyCode == KeyEvent.VK_F5) {
            programController.runProgramStep();
        }
        if (keyCode == KeyEvent.VK_ESCAPE) {
            programController.resetProgram();
        }

        repaint();
    }
}
