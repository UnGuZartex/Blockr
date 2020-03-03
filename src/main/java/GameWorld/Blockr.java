package GameWorld;

import GameWorld.Level.Level;
import GameWorld.Level.LevelLoader;

public class Blockr {

    private Level level;

    public Blockr() {};

    public void initializeGame() {
        level = LevelLoader.getInstance().loadLevel();
    }
}
