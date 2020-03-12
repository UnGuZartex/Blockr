package Controllers;

import System.GameWorld.Level.LevelLoader;

public class LevelController {

    private LevelLoader loader = new LevelLoader();

    public void loadLevel() {
        loader.loadLevel();
    }

}
