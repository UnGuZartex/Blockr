package Controllers;

import System.GameWorld.Level.LevelLoader;

public class LevelController {

    static LevelLoader loader = new LevelLoader();

    public static void resetLevel() {
        loader.loadLevel();
    }
}
