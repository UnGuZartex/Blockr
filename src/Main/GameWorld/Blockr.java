package Main.GameWorld;

import Main.GameWorld.Level.Level;
import Main.GameWorld.Level.LevelLoader;

public class Blockr {

    private Level level;

    public Blockr() {};

    public void initializeGame() {
        level = LevelLoader.getInstance().loadLevel();
    }
}
