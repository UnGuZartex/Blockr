package System.GameState;

import System.GameWorld.Level.Level;

public class GameState {
    public static Level currentLevel;
    public static int maxAmountOfBlocks = 2;

    public static void setCurrentLevel(Level currentLevel) {
        GameState.currentLevel = currentLevel;
    }
}
