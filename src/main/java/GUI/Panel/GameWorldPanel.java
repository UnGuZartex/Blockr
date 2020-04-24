package GUI.Panel;

import GameWorldAPI.GameWorld.GameWorld;
import Images.ImageLibrary;

import java.awt.*;

/**
 * A class for representing the game world panel.
 *
 * @invar The game world panel must contain a valid game world at all time.
 *        | isValidGameWorld(gameWorld)
 *
 * @author Alpha-team
 */
public class GameWorldPanel extends GamePanel {

    /**
     * Variable referring to the game world in the game world panel.
     */
    private final GameWorld gameWorld;

    /**
     * Initialise a new game world panel with a given game world, position and dimensions.
     *
     * @param gameWorld The game world for this panel.
     * @param cornerX The x coordinate for the corner of this panel.
     * @param cornerY The y coordinate for the corner of this panel.
     * @param width The width of this panel.
     * @param height The height of this panel.
     *
     * @post Set the game world of this panel to the given game world.
     *
     * @effect Calls super constructor with the given coordinates and dimensions.
     *
     * @throws IllegalArgumentException
     *         If the given game world is not valid.
     */
    public GameWorldPanel(GameWorld gameWorld, int cornerX, int cornerY, int width, int height) throws IllegalArgumentException {
        super(cornerX, cornerY, width, height);
        if (!isValidGameWorld(gameWorld)) {
            throw new IllegalArgumentException("The given game world is not valid!");
        }
        this.gameWorld = gameWorld;
    }

    /**
     * Checks whether or not the given game world is valid.
     *
     * @param gameWorld The game world to check.
     *
     * @return True if and only if the given game world is not null.
=======
     *
     * @post The current game world is set to the given game world.
     *
     * @effect Calls the super constructor with the given position and dimensions.
>>>>>>> b2ed5d8aecfa994c58c41603357365bcf8d9f443
     */
    public static boolean isValidGameWorld(GameWorld gameWorld) {
        return gameWorld != null;
    }

    /**
     * Paint the game state.
     *
     * @param g The given graphics.
     * @param library library The image library.
     *
     * @effect The game world is drawn.
     */
    @Override
    public void paint(Graphics g, ImageLibrary library) {
        Graphics g2 = g.create(getPanelRectangle().getX(), getPanelRectangle().getY(), getPanelRectangle().getWidth(), getPanelRectangle().getHeight());
        gameWorld.paint(g2, library);
        drawBackGround(g, library, null);
    }
}