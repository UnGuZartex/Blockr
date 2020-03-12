package System.GameWorld;

/**
 * An enum for the different types of cells.
 *
 * @author Alpha-team
 */
public enum CellType {

    /**
     * On a blank CellType can be walked, but it is not winning.
     */
    BLANK(true, false),
    /**
     * On a wall CellType can not be walked, it isn't winning.
     */
    WALL(false, false),
    /**
     * On a goal CellType can be walked and it is winning.
     */
    GOAL(true, true);

    /**
     * Variable referring to whether or not there can be walked on
     * this CellType.
     */
    private boolean canWalkOn;
    /**
     * Variable checking whether or not this CellType is winning.
     */
    private boolean isWin;

    /**
     * Initialise a new CellType with values whether there can be walked
     * on the type and if it is winning.
     *
     * @param canWalkOn If there can be walked on the CellType.
     * @param isWin If the CellType is winning.
     */
    CellType(boolean canWalkOn, boolean isWin){
        this.canWalkOn = canWalkOn;
        this.isWin = isWin;
    }

    /**
     * Checks whether or not there can be walked on this CellType.
     *
     * @return True if and only if there can be walked on this CellType.
     */
    public boolean canWalkOn() {
        return canWalkOn;
    }

    /**
     * Checks whether or not this CellType is winning.
     *
     * @return True if and only if this CellType is winning.
     */
    public boolean isWin() {
        return isWin;
    }

}
