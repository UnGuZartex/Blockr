package Main.GUI;

import java.awt.*;
import java.awt.geom.Point2D;

public class GameWorldPainter extends Painter {



    public GameWorldPainter(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
    }


    @Override
    public void paint(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.drawRect(cornerX, cornerY, width, height);
    }
}
