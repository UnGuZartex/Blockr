package System.GameState;

import System.GameWorld.Level.Level;

/**
 * A class to keep track of the state of the game.
 *
 * @author Alpha-team
 */
public class GameState {

    /**
     * Variable referring to the current level in the game.
     */
    private static Level currentLevel;
    /**
     * Variable referring to the maximum amount of blocks which may be used.
     */
    private static int maxAmountOfBlocks = 3;

    /**
     * Get the current level in the game.
     *
     * @return The current level in the game.
     */
    public static Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Set the current level in this game.
     *
     * @param currentLevel The new current level for this game.
     */
    public static void setCurrentLevel(Level currentLevel) {
        GameState.currentLevel = currentLevel;
    }

    /**
     * Get the maximum amount of blocks which may be used in this game.
     *
     * @return The maximum amount of blocks which may be used in this game.
     */
    public static int getMaxAmountOfBlocks() {
        return maxAmountOfBlocks;
    }

    /**
     * Set the maximum amount of blocks which may be used in this game.
     *
     * @param maxAmountOfBlocks The new maximum amount of blocks which may be used in this game.
     */
    public static void setMaxAmountOfBlocks(int maxAmountOfBlocks) {
        GameState.maxAmountOfBlocks = maxAmountOfBlocks;
    }
}
