package GUI;

import java.awt.*;

public class ProgramAreaPainter extends Painter {

    public ProgramAreaPainter(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
    }

    @Override
    public void paint(Graphics g) {
        drawBackground(g);
    }

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getProgramAreaBackgroundImage(), cornerX, cornerY, width, height, null);
        g.drawRect(cornerX, cornerY, width, height);
    }
}
