package Controllers;

import System.GameWorld.Direction;
import Utility.Position;

/**
 * A class used as a listener for receiving events about the program state.
 *
 * @author Alpha-team
 */
public interface ProgramListener {

    /**
     * This event is called when the program has finished and the game is won.
     */
    void onGameWon();

    /**
     * This event is called when the player has lost the game.
     */
    void onGameLost();

    /**
     * This event is called when the program has been reset.
     */
    void onProgramReset();

    /**
     * This event is called when the program area contains too much programs.
     */
    void onTooManyPrograms();

    /**
     * This event is called when the current program is invalid.
     */
    void onProgramInvalid();
}
