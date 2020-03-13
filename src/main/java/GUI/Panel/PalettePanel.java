package GUI.Panel;

import Controllers.ProgramController;
import GUI.Blocks.Factories.*;
import GUI.Components.GUIBlockHandler;

import java.awt.*;
import java.util.HashMap;

public class PalettePanel extends GamePanel {

    private GUIBlockHandler blockHandler;
    private ProgramController controller;


    public PalettePanel(GUIBlockHandler blockHandler, int cornerX, int cornerY, int width, int height, ProgramController controller) {
        super(cornerX, cornerY, width, height);
        this.blockHandler = blockHandler;
        this.controller = controller;

    }


    public void addBlocksToGame(String ID) {
        if(!controller.reachedMaxBlocks()) {
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
}
