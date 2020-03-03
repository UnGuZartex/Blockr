package GUI;

import java.awt.*;
import java.awt.geom.Point2D;

public class PalettePainter extends Painter {


    public PalettePainter(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.drawRect(cornerX, cornerY, width, height);
    }




}
