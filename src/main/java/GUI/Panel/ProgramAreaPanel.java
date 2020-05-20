package GUI.Panel;

import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.BlockHandlerController;
import Controllers.ProgramListener;
import GUI.Blocks.GUIBlock;
import GameWorldAPI.GameWorld.Result;
import Images.ImageLibrary;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A class for the panel of the program area. This panel can listen
 * to the program.
 *
 * @invar The program area panel must contain a valid block handler controller at all time.
 *        | blockHandlerController != null
 * @invar The program area panel must contain a valid connection controller at all time.
 *        | connectionController != null
 *
 * @author ALpha-team
 */
public class ProgramAreaPanel extends GamePanel implements ProgramListener {

    /**
     * Variable referring to the blocks in the program area panel with their palette index.
     */
    private final List<Map.Entry<GUIBlock, Integer>> blockPairs = new ArrayList<>();
    /**
     * Variable referring to the block that is currently being dragged from the palette
     * and may or may not end up inside the program area.
     */
    private Map.Entry<GUIBlock, Integer> temporaryBlockPair;
    /**
     * Variable referring to the program controller.
     */
    private final BlockHandlerController blockHandlerController;
    /**
     * Variable referring to the connection controller.
     */
    private final ConnectionController connectionController;
    /**
     * Variable referring to the game state.
     */
    protected String gameState = "";

    /**
     * Initialise a new program area panel with given corner, dimensions and controller.
     *
     * @param cornerX The x coordinate for the corner of this panel.
     * @param cornerY The y coordinate for the corner of this panel.
     * @param width The width of this panel.
     * @param height The height of this panel.
     * @param blockHandlerController The program controller used to handle program actions.
     * @param connectionController The connection controller used to handle connections.
     *
     * @post The program controller of this panel is set to the given program controller.
     * @post The connection controller of this panel is set to the given connection controller.
     *
     * @effect Calls the super constructor with given coordinates and dimensions.
     *
     * @throws IllegalArgumentException
     *         when the given block handler controller is null.
     * @throws IllegalArgumentException
     *         when the given connection controller is null.
     */
    public ProgramAreaPanel(int cornerX, int cornerY, int width, int height, BlockHandlerController blockHandlerController,
                ConnectionController connectionController) throws IllegalArgumentException {
        super(cornerX, cornerY, width, height);

        if (blockHandlerController == null) {
            throw new IllegalArgumentException("The given block handler controller is null.");
        }

        if (connectionController == null) {
            throw new IllegalArgumentException("The given connection controller is null.");
        }

        this.blockHandlerController = blockHandlerController;
        this.connectionController = connectionController;
    }

    /**
     * Return the connection controller.
     *
     * @return the connection controller.
     */
    public ConnectionController getConnectionController() {
        return connectionController;
    }

    /**
     * Add the current temporary block to the program area.
     *
     * @post The current temporary block pair is added to the list of block pairs.
     *
     * @effect The temporary block is added to the program area internally.
     *
     * @throws IllegalStateException
     *         when there is no temporary block available.
     * @throws IllegalArgumentException
     *         when the temporary block pair is already contained in the program area.
     */
    public void addTemporaryBlockToProgramArea() throws IllegalStateException {
        if (temporaryBlockPair == null) {
            throw new IllegalStateException("There is no temporary block available!");
        }
        if (blockPairs.contains(temporaryBlockPair)) {
            throw new IllegalStateException("Can't add a block that is already in the program area to the program area.");
        }
        blockPairs.add(temporaryBlockPair);
        blockHandlerController.addBlockToPA(temporaryBlockPair.getKey(), temporaryBlockPair.getValue());
    }

    /**
     * Set the temporary block pair to a new pair.
     *
     * @param blockPair The given block pair.
     *
     * @post The current temporary block pair is set to the given block pair.
     */
    public void setTemporaryBlockPair(Map.Entry<GUIBlock, Integer> blockPair) {
        temporaryBlockPair = blockPair;
    }

    /**
     * Disconnect the given block in the program area.
     *
     * @param GUIBlock The block to disconnect.
     *
     * @pre The given block must be in the program area.
     *
     * @effect The block is disconnected from the program area
     * @effect The program is reset.
     */
    public void disconnectInProgramArea(GUIBlock GUIBlock) {
        GUIBlock.disconnectMainConnector();
        connectionController.disconnectBlock(GUIBlock);
    }

    /**
     * Delete the given blocks from the program area.
     *
     * @param GUIBlocks The blocks to delete from the program area.
     *
     * @post All given blocks are deleted from the program area block pair list.
     * @effect All given blocks are internally deleted from the program area.
     */
    /**
     * TODO
     * @param GUIBlocks
     */
    public void deleteBlockFromProgramArea(List<GUIBlock> GUIBlocks) {
        for (Map.Entry<GUIBlock, Integer> block : blockPairs) {
            if (GUIBlocks.contains(block.getKey())) {
                block.getKey().terminate();
            }
        }
        blockPairs.removeIf(entry -> GUIBlocks.contains(entry.getKey()));
        GUIBlocks.forEach(blockHandlerController::deleteFromPA);
        update();
    }

    /**
     * Get all blocks in the program area.
     *
     * @return All the blocks in the program area.
     */
    public List<GUIBlock> getBlocks() {
        List<GUIBlock> blocks = new ArrayList<>();
        blockPairs.forEach(x -> blocks.add(x.getKey()));
        return blocks;
    }

    /**
     * Return a new list containing all block pairs.
     *
     * @return a new list containing all block pairs.
     */
    public List<Map.Entry<GUIBlock, Integer>> getBlockPairs() {
        return new ArrayList<>(blockPairs);
    }

    /**
     * Change the order of the blocks to draw them properly.
     *
     * @param highestLayerBlocks The block which should be drawn on top.
     *
     * @pre The given blocks must all be in the program area.
     *
     * @post The blocks which should be drawn last are put at the end
     *       of the blocks list.
     */
    public void setBlockDrawLayerFirst(List<GUIBlock> highestLayerBlocks) {
        List<Map.Entry<GUIBlock, Integer>> blocksCopy = new ArrayList<>();

        for (GUIBlock block : highestLayerBlocks) {
            blocksCopy.add(blockPairs.stream().filter(x -> x.getKey().equals(block)).findAny().orElse(null));
        }

        blockPairs.removeAll(blocksCopy);
        blockPairs.addAll(blocksCopy);
    }

    /**
     * Event to call if the game has finished
     *
     * @param result The result of the game
     *
     * @effect The colors of the blocks are changed to green if the player reached its goal
     *         during the game, orange otherwise.
     * @effect The game state text is updated accordingly.
     */
    @Override
    public void onGameFinished(Result result) {
        switch (result) {
            case END:
                changeBlockColors(Color.green);
                gameState = "YOU WIN!  :)";
                break;

            case FAILURE:
            case SUCCESS:
                changeBlockColors(Color.orange);
                gameState = "YOU LOSE!  :(";
                break;
        }
    }

    /**
     * Event to call when the program is in its default state.
     *
     * @effect The block colors are set to white.
     * @effect The game state text is updated accordingly.
     */
    @Override
    public void onProgramDefaultState() {
        changeBlockColors(Color.white);
        gameState = "";
    }

    /**
     * Event to call when there are to many programs.
     *
     * @effect The colors of the blocks are set to red.
     * @effect The game state text is updated accordingly.
     */
    @Override
    public void onTooManyPrograms() {
        changeBlockColors(Color.red);
        gameState = "TOO MANY PROGRAMS!";
    }

    /**
     * Event to call when the program is invalid.
     *
     * @effect The block colors are set to red.
     * @effect The game state text is updated accordingly.
     */
    @Override
    public void onProgramInvalid() {
        changeBlockColors(Color.red);
        gameState = "INVALID PROGRAM!";
    }

    /**
     * Change the color of the blocks to the given color.
     *
     * @param color The new color for the blocks.
     */
    private void changeBlockColors(Color color) {
        blockPairs.forEach(x -> x.getKey().setColor(color));
    }

    /**
     * Paint this panel.
     *
     * @param g The given graphics.
     * @param library The image library.
     *
     * @effect The program area background is drawn.
     * @effect The program area blocks are drawn.
     * @effect The game state is drawn.
     */
    @Override
    public void paint(Graphics g, ImageLibrary library) {
        drawBackGround(g, library, "programAreaBackground");
        drawBlocks(g);
        drawGameState(g);
    }

    /**
     * Draw all the blocks in the program area + the temporary block if it's currently set.
     *
     * @param g The graphics to draw the blocks with.
     *
     * @effect Each block in the program area and the temporary block (if any exists) is drawn.
     */
    private void drawBlocks(Graphics g) {
        blockPairs.forEach(x -> x.getKey().paint(g));
        if (temporaryBlockPair != null) temporaryBlockPair.getKey().paint(g);
    }

    /**
     * Draw the game state of this panel.
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
     * TODO comments
     */
    public void update() {
        for (Map.Entry<GUIBlock, Integer> block : new ArrayList<>(blockPairs)) {
            if (block.getKey().isTerminated()) {
                blockPairs.remove(block);
                blockHandlerController.deleteFromPA(block.getKey());
                block.getKey().removeInBetween(connectionController, blockHandlerController);
            }
        }
    }
}
