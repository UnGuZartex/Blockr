package Main.GameWorld.Level;

// SINGLETON CLASS
public class LevelLoader {

    private static LevelLoader loader;

    private LevelLoader() {}

    public static LevelLoader getInstance() {
        if (loader == null) {
            loader = new LevelLoader();
        }

        return loader;
    }

    public Level loadLevel() {
        return null;
    }
}
