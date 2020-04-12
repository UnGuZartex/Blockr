package Controllers.ControllerClasses;

import GUI.Blocks.GUIBlock;

/**
 * A class used as a listener for receiving events about newly created GUI blocks.
 *
 * @author Alpha-team
 */
public interface GUIBlockListener {

    /**
     * This event is called when a GUI palette creates a new GUI block
     *
     * @param block the newly created GUI block
     * @param index the index of the block in the palette its created in
     */
    void onGUIBlockCreated(GUIBlock block, int index);
}
