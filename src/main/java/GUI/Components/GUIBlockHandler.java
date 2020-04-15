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
    private int draggedBlockIndex;

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
        switch (id) {
            case MouseEvent.MOUSE_PRESSED:
                handleMousePressed(x, y);
                break;

            case MouseEvent.MOUSE_RELEASED:
                handleMouseReleased();
                break;

            case MouseEvent.MOUSE_DRAGGED:
                handleMouseDragged(x, y);
                break;
        }
    }

    /**
     * todo
     * @param x
     * @param y
     */
    private void handleMousePressed(int x, int y) {
        draggedBlockIndex = palette.getSelectedBlockIndex(x, y);
        boolean programAreaContainsMouse = programArea.getBlocks().stream().anyMatch(b -> b.contains(x, y));

        if (programAreaContainsMouse || draggedBlockIndex != -1) {
            if (draggedBlockIndex != -1) {
                draggedBlock = palette.getNewBlock(draggedBlockIndex);
                draggedBlocks = new ArrayList<>(List.of(draggedBlock));
                blockSourcePanel = palette;
                programArea.addVisualBlock(draggedBlock);
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
                if (blockSourcePanel == programArea) {
                    programArea.disconnectInProgramArea(draggedBlock);
                    programArea.deleteBlockFromProgramArea(draggedBlocks);
                }
                else {
                    programArea.removeVisualBlock(draggedBlock);
                }
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
        programArea.addBlockToProgramArea(draggedBlock, draggedBlockIndex);
        Optional<GUIBlock> connectedBlock = programArea.getBlocks().stream().filter(b -> b.intersectsWithConnector(draggedBlock)).findAny();
        connectedBlock.ifPresent(guiBlock -> draggedBlock.connectWithStaticBlock(guiBlock, programArea.getConnectionController()));
    }

    /**
     * todo
     */
    private void handleBlockFromProgramAreaToProgramArea() {
        programArea.disconnectInProgramArea(draggedBlock);

        for (GUIBlock block : draggedBlocks) {
            Optional<GUIBlock> connectedBlock = programArea.getBlocks().stream().filter(b -> b.intersectsWithConnector(block)).findAny();

            if (connectedBlock.isPresent()) {
                block.connectWithStaticBlock(connectedBlock.get(), programArea.getConnectionController());
                break;
            }
        }
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
