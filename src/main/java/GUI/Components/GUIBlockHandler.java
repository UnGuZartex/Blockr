package GUI.Components;

import GUI.Blocks.GUIBlock;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Panel.GamePanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import Utility.Position;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A class used to handle GUI blocks.
 *
 * @author Alpha-team
 */
public class GUIBlockHandler {

    private final PalettePanel palette;
    private final ProgramAreaPanel programArea;
    private GamePanel blockSourcePanel;
    private GUIBlock draggedBlock;
    private List<GUIBlock> draggedBlocks;
    private Position lastValidPosition, dragDelta;

    public GUIBlockHandler(PalettePanel palette, ProgramAreaPanel programArea) {
        this.palette = palette;
        this.programArea = programArea;
    }

    /**
     * Handle the current mouse event with the given id, x and y coordinate.
     * @param id the given mouse event id
     * @param x the given mouse x-coordinate
     * @param y the given mouse y-coordinate
     *
     * @effect The right mouse event is handled accordingly.
     */
    public void handleMouseEvent(int id, int x, int y) {

        if (id == MouseEvent.MOUSE_PRESSED) {
            handleMousePressed(x, y);
        }
        else if (id == MouseEvent.MOUSE_RELEASED) {
            handleMouseReleased();
        }
        else if (id == MouseEvent.MOUSE_DRAGGED) {
            handleMouseDragged(x, y);
        }
    }

    /**
     * todo
     * @param x
     * @param y
     */
    private void handleMousePressed(int x, int y) {

        boolean paletteBlockContainsMouse = AnyContains(palette.getBlocks(), x, y);
        boolean programAreaContainsMouse = AnyContains(programArea.getBlocks(), x, y);

        if (paletteBlockContainsMouse || programAreaContainsMouse) {
            if (paletteBlockContainsMouse) {
                draggedBlock = palette.getBlocks().stream().filter(b -> b.contains(x, y)).findAny().get();
                draggedBlocks = new ArrayList<>(List.of(draggedBlock));
                blockSourcePanel = palette;
            }
            else {
                draggedBlock = programArea.getBlocks().stream().filter(b -> b.contains(x, y)).reduce((first, second) -> second).get();
                draggedBlocks = draggedBlock.getConnectedBlocks();
                draggedBlock.disconnectHeight();
                programArea.setBlockDrawLayerFirst(draggedBlocks);
                blockSourcePanel = programArea;
            }

            dragDelta = new Position(draggedBlock.getX() - x, draggedBlock.getY() - y);
            lastValidPosition = new Position(draggedBlock.getX(), draggedBlock.getY());
        }
    }

    /**
     * todo
     */
    private void handleMouseReleased() {

        if (draggedBlock != null) {
            if (isInPanel(programArea.getPanelRectangle(), draggedBlocks)) {
                if (blockSourcePanel == palette) {
                    handleBlockFromPaletteToProgramArea();
                }
                else if (blockSourcePanel == programArea) {
                    handleBlockFromProgramAreaToProgramArea();
                }
            }
            else if (isInPanelAny(palette.getPanelRectangle(), draggedBlocks)) {
                handleBlockToPalette();
            }
            else {
                draggedBlock.setPosition(lastValidPosition.getX(), lastValidPosition.getY());
                draggedBlock.resetHeight();
            }

            draggedBlock = null;
            draggedBlocks = null;
        }
    }

    /**
     * Handle the event where the mouse is dragged with the given x and y coordinates of the mouse.
     *
     * @param x the given x-coordinate of the mouse
     * @param y the given y-coordinate of the mouse
     *
     * @effect The position of the dragged block is updated accordingly.
     */
    private void handleMouseDragged(int x, int y) {
        if (draggedBlock != null) {
            draggedBlock.setPosition(x + dragDelta.getX(), y + dragDelta.getY());
        }
    }

    /**
     * todo
     */
    private void handleBlockFromPaletteToProgramArea() {
        GUIBlock newBlock = palette.getNewBlock(draggedBlock);
        draggedBlock.setPosition(lastValidPosition.getX(), lastValidPosition.getY());
        programArea.addBlockToProgramArea(newBlock);
        draggedBlock = newBlock;

        Optional<GUIBlock> connectedBlock = programArea.getBlocks().stream().filter(b -> b.intersectsWithConnector(draggedBlock)).findAny();
        connectedBlock.ifPresent(guiBlock -> draggedBlock.connectWithStaticBlock(guiBlock, programArea.getConnectionController()));
    }

    /**
     * todo
     */
    private void handleBlockFromProgramAreaToProgramArea() {
        programArea.disconnectInProgramArea(draggedBlock);
        programArea.addBlockToProgramArea(draggedBlock);

        for (GUIBlock block : draggedBlocks) {
            Optional<GUIBlock> connectedBlock = programArea.getBlocks().stream().filter(b -> b.intersectsWithConnector(block)).findAny();

            if (connectedBlock.isPresent()) {
                block.connectWithStaticBlock(connectedBlock.get(), programArea.getConnectionController());
                break;
            }
        }
    }

    /**
     * Handle the event where the dragged gui bock goes from a certain position to the palette
     *
     * @effect Disconnect the dragged block from its previous set of blocks.
     * @effect Reset the position of the original palette block if the dragged block came from the palette.
     * @effect Delete the block from the program area if the dragged block came from the program area.
     */
    private void handleBlockToPalette() {
        programArea.disconnectInProgramArea(draggedBlock);

        if (blockSourcePanel == palette) {
            draggedBlock.setPosition(lastValidPosition.getX(), lastValidPosition.getY());
        }
        else if (blockSourcePanel == programArea) {
            programArea.deleteBlockFromProgramArea(draggedBlocks);
        }
    }

    /**
     * Return whether any block in the given list of gui blocks contains the point defined
     * by the given x and y coordinate.
     *
     * @param blocks the given list of gui blocks
     * @param x the given x-coordinate
     * @param y the given y-coordinate
     *
     * @return whether any block in the given list of gui blocks contains the point.
     */
    private boolean AnyContains(List<GUIBlock> blocks, int x, int y) {
        return blocks.stream().anyMatch(b -> b.contains(x, y));
    }

    /**
     * Checks if a given gui block is in a given collision rectangle panel.
     *
     * @param panel the given panel
     * @param blocks the given gui block
     *
     * @return if the given gui block is in the given panel.
     */
    private boolean isInPanel(CollisionRectangle panel, List<GUIBlock> blocks) {
        return blocks.stream().allMatch(b -> b.isInside(panel));
    }

    /**
     * Checks if a given list of guiblocks is in a given collision rectangle panel.
     *
     * @param panel the given panel
     * @param blocks the given list of gui blocks
     *
     * @return if the given list of gui blocks is in the given panel.
     */
    private boolean isInPanelAny(CollisionRectangle panel, List<GUIBlock> blocks) {
        return blocks.stream().anyMatch(b -> b.isInside(panel));
    }
}
