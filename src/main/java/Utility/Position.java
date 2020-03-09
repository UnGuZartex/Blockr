package Utility;

public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return  y;
    }

    public double getDistance(Position other) {
        return Math.sqrt(Math.abs(x - other.x)^2 + Math.abs(y - other.y)^2);
    }
}
