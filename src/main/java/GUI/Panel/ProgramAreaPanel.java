package GUI.Panel;

import java.awt.*;

public class ProgramAreaPanel extends GamePanel {

    public ProgramAreaPanel(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        drawBackground(g);
        g.drawRect(getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y);
    }

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getProgramAreaBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
    }
}
