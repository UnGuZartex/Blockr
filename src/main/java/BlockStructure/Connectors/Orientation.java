package BlockStructure.Connectors;

import GameWorld.Direction;

public enum Orientation {
    FACING_UP(0),
    FACING_LEFT(1),
    FACING_DOWN(2),
    FACING_RIGHT(3);

    private int id;

    Orientation(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isOpposite(Orientation other) {
        return id == other.id + 2 || id == other.id - 2;
    }

}
