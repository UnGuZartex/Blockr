package Main.GameWorld;

public class Level {

    private Grid grid;
    private Robot robot;

    public Level(Grid grid, Robot robot) {
        this.grid = grid;
        this.robot = robot;
    }

    public boolean hasWon() {
        return grid.getCellAt(robot.getX(), robot.getY()).getCellType().isWin();
    }

    public boolean canNotWalkHere() {
        return !grid.getCellAt(robot.getX(), robot.getY()).getCellType().canWalkOn();
    }


}
