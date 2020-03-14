package GUI.Panel;

import Controllers.ProgramController;
import GUI.Blocks.GUIBlock;
import GUI.Components.GUIBlockHandler;
import Utility.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PalettePanel extends GamePanel {

    private ProgramController controller;
    private final HashMap<String, Position> IDMap = new HashMap<>();
    public List<GUIBlock> blocks = new ArrayList<>();

    public PalettePanel(int cornerX, int cornerY, int width, int height, ProgramController controller) {
        super(cornerX, cornerY, width, height);

        this.controller = controller;
        IDMap.put("IF", new Position(10,0));
        IDMap.put("WHILE", new Position(10,200));
        IDMap.put("NOT", new Position(10,300));
        IDMap.put("WALL IN FRONT", new Position(10, 400));
        IDMap.put("MOVE FORWARD", new Position(10,500));
        IDMap.put("TURN LEFT", new Position(10,600));
        IDMap.put("TURN RIGHT", new Position(10,700));

        refillPalette();
    }

    public List<GUIBlock> getBlocks() {
        return new ArrayList<>(blocks);
    }

    public GUIBlock getNewBlock(String ID, int x, int y) {
        return controller.getBlock(ID, x, y);
    }

    public void drawBlocks(Graphics g) {
        for (GUIBlock block : blocks) {
            block.paint(g);
        }
    }

    @Override
    public void paint(Graphics g) {
        drawBackground(g);
    }

    protected void drawBackground(Graphics g) {
        g.drawImage(library.getPaletteBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        panelRectangle.paintNonFill(g);
    }

    private void refillPalette() {
        if (!controller.reachedMaxBlocks()) {
            for (String id : IDMap.keySet()) {
                if (!controller.reachedMaxBlocks()) {
                    blocks.add(controller.getBlock(id, IDMap.get(id).getX(), IDMap.get(id).getY()));
                }
            }
        }
    }
}
