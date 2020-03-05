package GameWorld;

public class Robot {

    private int x;
    private int y;
    private Direction direction;

    public Robot(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }


    public Direction getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getXForward() {
        switch (direction) {
            case LEFT:  return x-1;
            case RIGHT: return x+1;
            default:    return getX();
        }
    }

    public int getY() {
        return y;
    }

    public int getYForward() {
        switch (direction) {
            case UP:    return y-1;
            case DOWN:  return y+1;
            default:    return getY();
        }
    }


    public void moveForward() {
        x = getXForward();
        y = getYForward();
    }

    public void turnLeft() {
        direction = direction.turnLeft();
    }

    public void turnRight() {
        direction = direction.turnRight();
    }
}
