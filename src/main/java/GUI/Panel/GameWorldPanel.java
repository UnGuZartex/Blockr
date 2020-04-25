package GUI.Panel;

import GameWorldAPI.GameWorld.GameWorld;
import Images.ImageLibrary;

import java.awt.*;

/**
 * A class for representing the game world panel.
 *
 * @author Alpha-team
 */
public class GameWorldPanel extends GamePanel {

    /**
     * Variable representing the current game world.
     */
    private GameWorld gw;

    /**
     * Initialise a new game world panel with a given game world, position and dimensions.
     *
     * @param gw The given game world.
     * @param cornerX The x coordinate for the corner of this panel.
     * @param cornerY The y coordinate for the corner of this panel.
     * @param width The width of this panel.
     * @param height The height of this panel.
     *
     * @post The current game world is set to the given game world.
     *
     * @effect Calls the super constructor with the given position and dimensions.
     */
    public GameWorldPanel(GameWorld gw, int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
        this.gw = gw;
    }

    /**
     * Paint the game state.
     *
     * @param g The given graphics.
     * @library library The image library.
     *
     * @effect The game world is drawn.
     */
    @Override
    public void paint(Graphics g, ImageLibrary library) {
        Graphics g2 = g.create(getPanelRectangle().getX(), getPanelRectangle().getY(), getPanelRectangle().getWidth(), getPanelRectangle().getHeight());
        gw.paint(g2, library);
        drawBackGround(g, library, null);
    }
}
