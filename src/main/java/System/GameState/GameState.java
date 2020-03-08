package System.GameState;

import System.GameWorld.Level.Level;

public class GameState {
    public static int maxNbBlocks;
    public static int currentNbBlocks = 9;



    public static Level currentLevel;

    public static void increaseCurrentNbBlocks() {
        currentNbBlocks++;
    }

    public static void decreaseCurrentNbBlocks() {
        currentNbBlocks--;
    }

    public static void setCurrentLevel(Level currentLevel) {
        GameState.currentLevel = currentLevel;
    }
}
