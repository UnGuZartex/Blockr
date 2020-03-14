package GUI.Panel;

import Controllers.ProgramController;
import GUI.Blocks.GUIBlock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramAreaPanel extends GamePanel {

    private List<GUIBlock> blocks = new ArrayList<>();

    private ProgramController controller;

    public ProgramAreaPanel(int cornerX, int cornerY, int width, int height, ProgramController controller) {
        super(cornerX, cornerY, width, height);
        this.controller = controller;
    }

    public void addBlockToProgramArea(GUIBlock block) {
        blocks.add(block);
        System.err.println("COUNT: " + blocks.size());
        controller.addBlockToPA(block);
    }

    public void disconnectInProgramArea(GUIBlock GUIBlock) {
        controller.getController().disconnectBlock(GUIBlock);
        controller.resetProgram();
    }

    public void deleteBlockFromProgramArea(List<GUIBlock> GUIBlocks) {
        blocks.removeAll(GUIBlocks);
        for (GUIBlock block:GUIBlocks) {
            controller.deleteFromPA(block);
        }
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
