package GUI.Panel;

import Controllers.ConnectionController;
import Controllers.ProgramController;
import Controllers.ProgramListener;
import GUI.Blocks.GUIBlock;
import System.BlockStructure.Blocks.Block;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramAreaPanel extends GamePanel implements ProgramListener {

    private List<GUIBlock> blocks = new ArrayList<>();
    private ProgramController programController;
    private ConnectionController connectionController;

    public ProgramAreaPanel(int cornerX, int cornerY, int width, int height, ProgramController programController,
                            ConnectionController connectionController) {
        super(cornerX, cornerY, width, height);
        this.programController = programController;
        this.connectionController = connectionController;
        programController.subscribeListener(this);
    }

    public ProgramController getProgramController() {
        return programController;
    }

    public ConnectionController getConnectionController() {
        return connectionController;
    }

    public void addBlockToProgramArea(GUIBlock block) {
        blocks.add(block);
    }

    public void addBlockToProgramAreaControllerCall(GUIBlock block) {
        programController.addBlockToPA(block);
    }

    public void disconnectInProgramArea(GUIBlock GUIBlock) {
        connectionController.disconnectBlock(GUIBlock);
        programController.resetProgram();
    }

    public void deleteBlockFromProgramArea(List<GUIBlock> GUIBlocks) {
        blocks.removeAll(GUIBlocks);
        for (GUIBlock block:GUIBlocks) {
            programController.deleteFromPA(block);
        }
    }

    public List<GUIBlock> getBlocks() {
        return new ArrayList<>(blocks);
    }

    public void setBlockDrawLayerFirst(List<GUIBlock> highestLayerBlocks) {
        blocks.remove(highestLayerBlocks);
        blocks.addAll(highestLayerBlocks);
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

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getProgramAreaBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        panelRectangle.paintNonFill(g);
    }

    @Override
    public void onGameWon() {
        changeBlockColors(Color.green);
    }

    @Override
    public void onGameLost() {
        changeBlockColors(Color.orange);
    }

    @Override
    public void onProgramReset() {
        System.err.println("OKE");
        changeBlockColors(Color.white);
    }

    @Override
    public void onTooManyPrograms() {
        changeBlockColors(Color.red);
    }

    @Override
    public void onProgramInvalid() {
        changeBlockColors(Color.red);
    }

    private void changeBlockColors(Color color) {
        for (GUIBlock block : blocks) {
            block.setColor(color);
        }
    }
}
