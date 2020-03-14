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
    }

    public void deleteBlockFromProgramArea(GUIBlock block) {
        blocks.remove(block);
    }

    @Override
    public void paint(Graphics g) {
        drawBackground(g);

        for (GUIBlock block : blocks) {
            block.paint(g);
        }
    }

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getProgramAreaBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        panelRectangle.paintNonFill(g);
    }
}
