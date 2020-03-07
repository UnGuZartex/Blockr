package GUI.Panel;

import java.awt.*;

public class ProgramAreaPanel extends GamePanel {

    public ProgramAreaPanel(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y);
    }
}
