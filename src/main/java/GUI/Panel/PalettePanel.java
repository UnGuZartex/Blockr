package GUI.Panel;

import java.awt.*;

public class PalettePanel extends GamePanel {
    
    public PalettePanel(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
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
