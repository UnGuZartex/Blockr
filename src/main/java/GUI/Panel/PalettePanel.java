package GUI.Panel;

import Controllers.PaletteController;
import GUI.GUIBlock;

import java.awt.*;

public class PalettePanel extends GamePanel {

    public final PaletteController controller = new PaletteController();
    public PalettePanel(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
    }

    @Override
    public void paint(Graphics g) {
        drawBackground(g);
        GUIBlock[] toDraw = controller.getBlocks();
        g.setColor(Color.GREEN);
        g.drawRect(getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y);

        for(int i = 0; i < toDraw.length; i++){
            toDraw[i].draw(g);
        }

    }

    protected void drawBackground(Graphics g) {
        g.drawImage(library.getPaletteBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        g.drawRect(getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y);
    }
}
