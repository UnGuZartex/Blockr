package GameWorld;

public enum Direction {
    UP(0),
    LEFT(1),
    DOWN(2),
    RIGHT(3);

    private int id;
    private static Direction[] values = values().clone();

    Direction(int id) {
        this.id = id;
    }

    public Direction turnLeft() {
        return values[(id + 1) % values.length];
    }

    public Direction turnRight() {
        return values[(id + values.length - 1) % values.length];
    }
}
