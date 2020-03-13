package GUI.Panel;

import Controllers.ProgramController;
import GUI.Components.GUIBlockHandler;
import Utility.Position;

import java.awt.*;
import java.util.HashMap;

public class PalettePanel extends GamePanel {

    private GUIBlockHandler blockHandler;
    private ProgramController controller;
    private final HashMap<String, Position> IDMAP = new HashMap<>();


    public PalettePanel(GUIBlockHandler blockHandler, int cornerX, int cornerY, int width, int height, ProgramController controller) {
        super(cornerX, cornerY, width, height);
        this.blockHandler = blockHandler;
        this.controller = controller;
        IDMAP.put("IF", new Position(10,0));
        IDMAP.put("WHILE", new Position(10,200));
        IDMAP.put("NOT", new Position(10,300));
        IDMAP.put("WALL IN FRONT", new Position(10, 400));
        IDMAP.put("MOVE FORWARD", new Position(10,500));
        IDMAP.put("TURN LEFT", new Position(10,600));
        IDMAP.put("TURN RIGHT", new Position(10,700));

        refillPalette();
    }


    public void addBlocksToGame(String ID, int x, int y) {
        if (!controller.reachedMaxBlocks()) {
            blockHandler.addBlock(controller.getBlock(ID, x, y));
        }
    }

    @Override
    public void paint(Graphics g) {
        drawBackground(g);
        g.setColor(Color.GREEN);
        g.drawRect(getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y);
    }

    protected void drawBackground(Graphics g) {
        g.drawImage(library.getPaletteBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        g.drawRect(getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y);
    }

    private void refillPalette() {
        if (!controller.reachedMaxBlocks()) {
            for (String id : IDMAP.keySet()) {
                addBlocksToGame(id, IDMAP.get(id).getX(), IDMAP.get(id).getY());
            }
        }

    }
}
