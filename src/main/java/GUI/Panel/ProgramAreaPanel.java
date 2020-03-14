package GUI.Panel;

import GUI.Blocks.GUIBlock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramAreaPanel extends GamePanel {

    private List<GUIBlock> blocks = new ArrayList<>();

    public ProgramAreaPanel(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
    }

    public void addBlockToProgramArea(GUIBlock block) {
        blocks.add(block);
        System.err.println("COUNT: " + blocks.size());
        // TODO controller call
    }

    public void deleteBlockFromProgramArea(List<GUIBlock> GUIblocks) {
        System.err.println("COUNT - BEFORE: " + blocks.size());
        blocks.removeAll(GUIblocks);
        System.err.println("COUNT - AFTER: " + blocks.size());
        // TODO controller call
    }

    public List<GUIBlock> getBlocks() {
        return new ArrayList<>(blocks);
    }

    public void setBlockDrawLayerFirst(List<GUIBlock> highestLayerBlocks) {
        blocks.remove(highestLayerBlocks);
        blocks.addAll(highestLayerBlocks);
    }

    @Override
    public void paint(Graphics g) {
        drawBackground(g);
    }

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getProgramAreaBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        panelRectangle.paintNonFill(g);
    }

    public void drawBlocks(Graphics g) {
        for (GUIBlock block : blocks) {
            block.paint(g);
        }
    }
}
