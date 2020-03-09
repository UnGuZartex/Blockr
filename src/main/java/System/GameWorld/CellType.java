package System.GameWorld;

/**
 * An enum for the different types of cells.
 *
 * @author Alpha-team
 */
public enum CellType {

    BLANK(true, false),
    WALL(false, false),
    GOAL(true, true);

    private boolean canWalkOn;
    private boolean isWin;

    CellType(boolean canWalkOn, boolean isWin){
        this.canWalkOn = canWalkOn;
        this.isWin = isWin;
    }

    public boolean canWalkOn() {
        return canWalkOn;
    }

    public boolean isWin() {
        return isWin;
    }

}
