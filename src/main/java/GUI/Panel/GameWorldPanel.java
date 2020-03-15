package GUI.Panel;

import GUI.Components.GUIGrid;

import java.awt.*;

public class GameWorldPanel extends GamePanel {

    private GUIGrid GUIGrid;
    private String gameState = "";

    public GameWorldPanel(int cornerX, int cornerY, int width, int height) {
        super(cornerX, cornerY, width, height);
        GUIGrid = new GUIGrid(cornerX, cornerY, width, height);
    }

    public void setWin() {
        gameState = "YOU WIN!";
    }

    public void setLose() {
        gameState = "YOU LOSE!";
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
}
