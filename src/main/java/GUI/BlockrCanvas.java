package GUI;


import Controllers.ProgramController;
import GUI.CollisionShapes.CollisionCircle;
import GUI.Components.GUIBlockHandler;
import GUI.Images.ImageLibrary;
import GUI.Panel.GamePanel;
import GUI.Panel.GameWorldPanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class BlockrCanvas extends CanvasWindow {

    public static final double PALETTE_WIDTH_RATIO = 0.1;
    public static final double PROGRAMAREA_WIDTH_RATIO = 0.5;
    public static final double GAMEWORLD_WIDTH_RATIO = 0.4;

    private GamePanel[] panels;
    private GUIBlockHandler blockHandler;

    private ProgramController programController = new ProgramController();


    /**
     * Initializes a CanvasWindow object. 
     *
     * @param title Window title
     */
    protected BlockrCanvas(String title, ImageLibrary library, int width, int height) {
        super(title);
        panels = new GamePanel[3];

        GamePanel.setImageLibrary(library);
        blockHandler = new GUIBlockHandler();
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

    private void setPainters() {
        panels[0] = new PalettePanel(blockHandler,0, 0, (int)(width * PALETTE_WIDTH_RATIO), height, programController);
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
