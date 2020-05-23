package Controllers;

/**
 * An interface for listeners to a palette.
 *
 * @author Alpha-team
 */
public interface PaletteListener {

    /**
     * Event to call when a procedure is deleted.
     *
     * @param index The index of the call block in the palette of the deleted procedure.
     */
    void procedureDeleted(int index);

    /**
     * Event to call when a procedure is created.
     */
    void procedureCreated();
}
