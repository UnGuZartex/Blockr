package GUI.Panel;

import Controllers.ProgramController;
import GUI.Blocks.GUIBlock;
import GUI.Components.GUIBlockHandler;
import System.BlockStructure.Blocks.Block;
import Utility.Position;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PalettePanel extends GamePanel {

    private ProgramController controller;
    private final List<String> IDList;
    public List<GUIBlock> blocks = new ArrayList<>();

    public PalettePanel(int cornerX, int cornerY, int width, int height, ProgramController controller) {
        super(cornerX, cornerY, width, height);
        this.controller = controller;
        IDList = Arrays.asList("IF", "WHILE", "NOT", "WALL IN FRONT", "MOVE FORWARD", "TURN LEFT", "TURN RIGHT");
        refillPalette();
        setBlockPositions();
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

    public void update() {
        if (controller.reachedMaxBlocks()) {
            blocks.clear();
        } else if (blocks.size() == 0) {
            refillPalette();
            setBlockPositions();
        }
    }

    private void refillPalette() {
        if (!controller.reachedMaxBlocks()) {
            for (String id : IDList) {
                if (!controller.reachedMaxBlocks()) {
                    blocks.add(controller.getBlock(id, 0, 0));
                }
            }
        }
    }

    private void setBlockPositions() {
        int freeHeightPerBlock = (panelRectangle.getHeight() - getTotalBlockHeight()) / (blocks.size() + 1);
        int currentHeight = freeHeightPerBlock;

        for (GUIBlock block : blocks) {
            block.setPosition((panelRectangle.getWidth() - block.getWidth()) / 2, currentHeight);
            currentHeight = currentHeight + block.getHeight() + freeHeightPerBlock;
        }
    }

    private int getTotalBlockHeight() {

        int totalHeight = 0;

        for (GUIBlock block : blocks) {
            totalHeight += block.getHeight();
        }

        return totalHeight;
    }
}
