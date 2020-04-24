package GUI.Panel;

import GameWorldAPI.GameWorld.GameWorld;
import Images.ImageLibrary;

import java.awt.*;

/**
 * A class for a game world panel, which can listen to the program.
 *
 * @author Alpha-team
 */
public class GameWorldPanel extends GamePanel {


    private GameWorld gw;

    /**
     * Initialise a new game world panel with given corner, dimension and controller.
     *
     * @param cornerX The x coordinate for the corner of this panel.
     * @param cornerY The y coordinate for the corner of this panel.
     * @param width The width of this panel.
     * @param height The height of this panel.
     * @param controller The controller to control this panel.
     *
     * @effect Calls super constructor with the given coordinates and dimensions.
     * @effect subscribes this panel as a listener to the controller.
     * @effect The grid is initialised to a new grid with given coordinates and dimensions.
     */
    /**
     * TODO commentaar
     */
    public GameWorldPanel(GameWorld gw, int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
        this.gw = gw;
    }

    /**
     * Paint the game state.
     *
     * @param g The graphics to paint this panel with.
     *
     * @effect Draws the background of this panel.
     * @effect Draws the grid for this panel using the image library.
     * @effect draws the game state.
     */
    @Override
    public void paint(Graphics g, ImageLibrary images) {

        drawBackground(g);
        Graphics g2 = g.create(getPanelRectangle().getX(), getPanelRectangle().getY(), getPanelRectangle().getWidth(), getPanelRectangle().getHeight());
        gw.paint(g2, images);
    }




}
