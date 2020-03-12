package System.GameState;

import System.GameWorld.Level.Level;

public class GameState {
    public static Level currentLevel;

    public static void setCurrentLevel(Level currentLevel) {
        GameState.currentLevel = currentLevel;
    }
}
