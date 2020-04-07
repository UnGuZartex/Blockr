package GUI.Panel;

import Controllers.ControllerClasses.ProgramController;
import Controllers.ProgramListener;
import GUI.Components.GUIGrid;

import java.awt.*;

/**
 * A class for a game world panel, which can listen to the program.
 *
 * @author Alpha-team
 */
public class GameWorldPanel extends GamePanel implements ProgramListener {

    /**
     * Variable referring to the grid.
     */
    private GUIGrid GUIGrid;
    /**
     * Variable referring to the game state.
     */
    protected String gameState = "";

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
    public GameWorldPanel(int cornerX, int cornerY, int width, int height, ProgramController controller) {
        super(cornerX, cornerY, width, height);
        controller.subscribeListener(this);
        GUIGrid = new GUIGrid(cornerX, cornerY, width, height);
    }

    /**
     * Reset the game state.
     *
     * @post The game state is set to an empty string.
     */
    public void resetGameText() {
        gameState = "";
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
    public void paint(Graphics g) {
        drawBackground(g);
        GUIGrid.paint(g, library);
        drawGameState(g);
    }

    /**
     * Draw the background of this panel.
     *
     * @param g The graphics to draw the background with.
     */
    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getGameWorldBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        panelRectangle.paintNonFill(g);
    }

    /**
     * Draw the gamestate of this panel.
     *
     * @param g The graphics to draw the game state with.
     */
    private void drawGameState(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 4F);
        g.setFont(newFont);
        g.drawString(gameState, panelRectangle.getX(), 100);
        g.setFont(currentFont);
    }

    /**
     * Event to call when the game has been won.
     */
    @Override
    public void onGameWon() {
        gameState = "YOU WIN!  :)";
    }

    /**
     * Event to call when the game has been lost.
     */
    @Override
    public void onGameLost() {
        gameState = "YOU LOSE!  :(";
    }

    /**
     * Event to call when the program has been reset.
     */
    @Override
    public void onProgramReset() {
        gameState = "";
    }

    /**
     * Event to call when there are too many programs.
     */
    @Override
    public void onTooManyPrograms() {
        gameState = "TOO MANY PROGRAMS!";
    }

    /**
     * Event to call when there is an invalid program.
     */
    @Override
    public void onProgramInvalid() {
        gameState = "INVALID PROGRAM";
    }
}
