package BlockStructure.Connections;

public abstract class Connector {

    private final Orientation orientation;

    public Connector(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * TODO
     * @return
     */
    public Orientation getOrientation() {
        return orientation;
    }







}
