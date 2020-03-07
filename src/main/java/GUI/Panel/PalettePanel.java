package GUI.Panel;

import java.awt.*;

public class PalettePanel extends GamePanel {

    public PalettePanel(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y);
    }
}
