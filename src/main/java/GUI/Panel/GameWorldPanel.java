package GUI.Panel;

import Controllers.ProgramController;
import Controllers.ProgramListener;
import GUI.Components.GUIGrid;

import java.awt.*;

public class GameWorldPanel extends GamePanel implements ProgramListener {

    private GUIGrid GUIGrid;
    private String gameState = "";

    public GameWorldPanel(int cornerX, int cornerY, int width, int height, ProgramController controller) {
        super(cornerX, cornerY, width, height);
        controller.subscribeListener(this);
        GUIGrid = new GUIGrid(cornerX, cornerY, width, height);
    }

    public void resetGameText() {
        gameState = "";
    }

    @Override
    public void paint(Graphics g) {
        drawBackground(g);
        GUIGrid.paint(g, library);
        drawGameState(g);
    }

    @Override
    protected void drawBackground(Graphics g) {
        g.drawImage(library.getGameWorldBackgroundImage(), getLeftCorner().x, getLeftCorner().y, getSize().x, getSize().y, null);
        panelRectangle.paintNonFill(g);
    }

    private void drawGameState(Graphics g) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 4F);
        g.setFont(newFont);
        g.drawString(gameState, panelRectangle.getX(), 100);
        g.setFont(currentFont);
    }

    @Override
    public void onGameWon() {
        gameState = "YOU WIN!  :)";
    }

    @Override
    public void onGameLost() {
        gameState = "YOU LOSE!  :(";
    }

    @Override
    public void onProgramReset() {
        gameState = "";
    }

    @Override
    public void onTooManyPrograms() {
        gameState = "TOO MANY PROGRAMS IN PA!";
    }

    @Override
    public void onProgramInvalid() {
        gameState = "INVALID PROGRAM";
    }
}
