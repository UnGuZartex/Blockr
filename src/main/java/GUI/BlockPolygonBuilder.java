package GUI;

import java.awt.*;

public class BlockPolygonBuilder {

    private final int CONNECTOR_WIDTH, CONNECTOR_WIDTH_DELTA, CONNECTOR_HEIGHT;


    public BlockPolygonBuilder(int CONNECTOR_WIDTH, int CONNECTOR_WIDTH_DELTA, int CONNECTOR_HEIGHT) {
        this.CONNECTOR_WIDTH = CONNECTOR_WIDTH;
        this.CONNECTOR_WIDTH_DELTA = CONNECTOR_WIDTH_DELTA;
        this.CONNECTOR_HEIGHT = CONNECTOR_HEIGHT;
    }

    public Polygon addLine(Polygon polygon, int x, int y) {
        polygon.addPoint(x, y);
        return polygon;
    }

    public Polygon addSocketFacingUp(Polygon polygon, int x, int y) {

        polygon.addPoint(x + (-CONNECTOR_WIDTH - 2) / 2, y);
        polygon.addPoint(x + (-CONNECTOR_WIDTH - 2 + CONNECTOR_WIDTH_DELTA) / 2, y + CONNECTOR_HEIGHT);
        polygon.addPoint(x + (CONNECTOR_WIDTH + 2 - CONNECTOR_WIDTH_DELTA) / 2, y + CONNECTOR_HEIGHT);
        polygon.addPoint(x + (CONNECTOR_WIDTH + 2) / 2, y);

        return polygon;
    }

    public Polygon addPlugFacingDown(Polygon polygon, int x, int y) {

        polygon.addPoint(x + CONNECTOR_WIDTH / 2, y);
        polygon.addPoint(x + (CONNECTOR_WIDTH - CONNECTOR_WIDTH_DELTA) / 2, y + CONNECTOR_HEIGHT);
        polygon.addPoint(x + (-CONNECTOR_WIDTH + CONNECTOR_WIDTH_DELTA) / 2, y + CONNECTOR_HEIGHT);
        polygon.addPoint(x + -CONNECTOR_WIDTH / 2, y);

        return polygon;
    }

    public Polygon addSocket(Polygon polygon) {
        throw new UnsupportedOperationException();
    }

    public Polygon addConditionalTop(Polygon polygon) {
        throw new UnsupportedOperationException();
    }
}
