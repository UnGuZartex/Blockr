package Controllers;

import GameWorldAPI.GameWorld.Result;

/**
 * An interface used as a listener for receiving events about the program state.
 *
 * @author Alpha-team
 */
public interface ProgramListener {

    /**
     * This event is called when the game run by the program has finished.
     *
     * @param result the result of the game
     */
    void onGameFinished(Result result);

    /**
     * This event is called when the program is in its start state.
     */
    void onProgramStartState();

    /**
     * This event is called when the program area contains too much programs.
     */
    void onTooManyPrograms();

    /**
     * This event is called when the current program is invalid.
     */
    void onProgramInvalid();
}
