package GUI.Components;

import Controllers.ControllerClasses.HistoryController;
import GUI.Blocks.GUIBlock;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Panel.GamePanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import GameWorldAPI.History.Snapshot;
import Utility.Position;

import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A class used to handle the movement of GUI blocks between the
 * palette and program area panels.
 *
 * @author Alpha-team
 */
public class GUIBlockHandler {

    /**
     * Variable referring to the palette panel.
     */
    private final PalettePanel palette;

    /**
     * Variable referring to the program area panel.
     */
    private final ProgramAreaPanel programArea;

    /**
     * Variable referring to the panel where the current dragged block was first moved from.
     */
    private GamePanel blockSourcePanel;

    /**
     * Variable referring to the blocks connected to the current dragged block.
     */
    private List<GUIBlock> draggedBlocks;

    /**
     * Variable referring to the last valid positions of the current dragged blocks.
     */
    private List<Position> lastValidPositions;

    /**
     * Variable referring to the relation between the mouse and the current dragged block.
     */
    private Position dragDelta;

    /**
     * Variable referring to the index of the current dragged block inside the palette.
     */
    private int draggedBlockIndex;

    private Position startPosition;

    private HistoryController historyController;

    private List<Position> blockPositions = new ArrayList<>();
    private List<Integer> paletteIndices = new ArrayList<>();

    /**
     * Create a new gui block handler with a given palette and program area panel.
     *
     * @param palette The given palette panel.
     * @param programArea The given program area panel.
     *
     * @post The current palette panel is set to the given palette panel.
     * @post The current program area panel is set to the given program area panel.
     *
     * @param historyController TODO
     */
    public GUIBlockHandler(PalettePanel palette, ProgramAreaPanel programArea, HistoryController historyController) {
        this.palette = palette;
        this.programArea = programArea;
        this.historyController = historyController;
    }

    public void handleMouseEventPre(int id, int x, int y) {
        if (id == MouseEvent.MOUSE_RELEASED && draggedBlocks != null) {
            historyController.execute(new MoveCommand(startPosition, new Position(x, y), this));
        }
        else {
            handleMouseEvent(id, x, y);
        }
    }

    private void collectBlockData() {
        blockPositions = new ArrayList<>();
        paletteIndices = new ArrayList<>();

        programArea.getBlocks().sort((GUIBlock b1, GUIBlock b2) -> b1.getY())

        for (Map.Entry<GUIBlock, Integer> entry : programArea.getBlockPairs()) {
            blockPositions.add(entry.getKey().getPosition());
            paletteIndices.add(entry.getValue());
        }
    }

    /**
     * Handle the current mouse event with the given id, x and y coordinate.
     *
     * @param id the given mouse event id
     * @param x the given mouse x-coordinate
     * @param y the given mouse y-coordinate
     *
     * @effect The mouse event is handled accordingly depending on the given id.
     */
    public void handleMouseEvent(int id, int x, int y) {
        switch (id) {
            case MouseEvent.MOUSE_PRESSED:
                startPosition = new Position(x, y);
                collectBlockData();
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

    public Snapshot createSnapshot() {
        return new GUIBlockHandlerSnapshot();
    }

    public void loadSnapshot(Snapshot snapshot) {
        GUIBlockHandlerSnapshot guiSnapshot = (GUIBlockHandlerSnapshot) snapshot;
        programArea.deleteBlockFromProgramArea(programArea.getBlocks());

        for (int i = 0; i < guiSnapshot.blockPositionsSnapshot.size(); i++) {
            addPaletteBlockToProgramArea(guiSnapshot.blockPositionsSnapshot.get(i), guiSnapshot.paletteIndicesSnapshot.get(i));
        }
    }

    /**
     * Handle the event where the mouse is pressed depending on
     * where the mouse was pressed.
     *
     * @param x the given mouse x-coordinate
     * @param y the given mouse y-coordinate
     *
     * @post If the mouse collides with a block, the dragged block is set to
     *       the block the mouse collides with.
     * @post The list of dragged blocks is set to the blocks connected to the current dragged block.
     * @post The palette index is set to the index of the dragged block inside the palette, if possible.
     * @post The drag delta of the dragged block is set accordingly.
     * @post The last valid position of the dragged block is set accordingly.
     * @post The source panel where the dragged block came from is set accordingly.
     *
     * @effect If the mouse was pressed in the program area on a block, a temporary block is set
     *         in the program area.
     */
    private void handleMousePressed(int x, int y) {
        draggedBlockIndex = palette.getSelectedBlockIndex(x, y);
        boolean programAreaContainsMouse = programArea.getBlocks().stream().anyMatch(b -> b.contains(x, y));
        GUIBlock draggedBlock;

        if (programAreaContainsMouse || draggedBlockIndex != -1) {
            if (draggedBlockIndex != -1) {
                draggedBlock = palette.getNewBlock(draggedBlockIndex);
                draggedBlocks = new ArrayList<>(List.of(draggedBlock));
                blockSourcePanel = palette;
                programArea.setTemporaryBlock(draggedBlock);
            }
            else {
                draggedBlock = programArea.getBlocks().stream().filter(b -> b.contains(x, y)).reduce((first, second) -> second).get();
                draggedBlocks = draggedBlock.getConnectedBlocks();
                draggedBlock.disconnectHeight();
                programArea.setBlockDrawLayerFirst(draggedBlocks);
                blockSourcePanel = programArea;
            }

            dragDelta = new Position(draggedBlock.getX() - x, draggedBlock.getY() - y);
            lastValidPositions = new ArrayList<>();

            for (GUIBlock block : draggedBlocks) {
                lastValidPositions.add(new Position(block.getX(), block.getY()));
            }
        }
    }

    private void addPaletteBlockToProgramArea(Position pos, int paletteIndex) {
        draggedBlockIndex = paletteIndex;
        GUIBlock draggedBlock = palette.getNewBlock(paletteIndex);
        draggedBlocks = new ArrayList<>(List.of(draggedBlock));
        blockSourcePanel = palette;
        programArea.setTemporaryBlock(draggedBlock);
        dragDelta = new Position(0, 0);

        handleMouseDragged(pos.getX(), pos.getY());
        handleMouseReleased();
    }

    /**
     * Handle the event where the mouse is released depending on
     * where the mouse was pressed the first time and where it was released.
     *
     * @post The dragged block is set to null.
     * @post The list of dragged blocks is set to null.
     *
     * @effect The temporary block in the program area is set to null.
     * @effect If the dragged block is placed on an illegal position, the block is set to its
     *         last known location and the height of the structure containing the block is reset
     *         to before the block was disconnected.
     * @effect If the dragged block is dragged to the program area, the event is handled accordingly.
     * @effect If the dragged block was dragged from the program area to the palette, the program area disconnects
     *         and deletes the block from the program area.
     */
    private void handleMouseReleased() {
        if (draggedBlocks != null) {

            if (isInPanel(programArea.getPanelRectangle(), draggedBlocks)) {
                if (blockSourcePanel == palette) {
                    handleBlockFromPaletteToProgramArea();
                }
                else if (blockSourcePanel == programArea) {
                    handleBlockFromProgramAreaToProgramArea();
                }
            }
            else if (isInPanelAny(palette.getPanelRectangle(), draggedBlocks) && blockSourcePanel == programArea) {
                programArea.disconnectInProgramArea(draggedBlocks.get(0));
                programArea.deleteBlockFromProgramArea(draggedBlocks);
            }
            else {
                draggedBlocks.get(0).setPosition(lastValidPositions.get(0).getX(), lastValidPositions.get(0).getY());
                draggedBlocks.get(0).resetHeight();
            }

            programArea.setTemporaryBlock(null);
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
        if (draggedBlocks != null) {
            draggedBlocks.get(0).setPosition(x + dragDelta.getX(), y + dragDelta.getY());
        }
    }

    /**
     * Handle the event where the mouse is dragged from the palette to the program area.
     *
     * @effect The dragged block that came from the palette is added to the program area.
     * @effect The dragged block is connected with other blocks if possible.
     */
    private void handleBlockFromPaletteToProgramArea() {
        programArea.addTemporaryBlockToProgramArea(draggedBlockIndex);
        Optional<GUIBlock> connectedBlock = programArea.getBlocks().stream().filter(b -> b.intersectsWithConnector(draggedBlocks.get(0))).findAny();
        connectedBlock.ifPresent(guiBlock -> draggedBlocks.get(0).connectWithStaticBlock(guiBlock, programArea.getConnectionController()));
    }

    /**
     * Handle the event where the mouse is dragged from the program area to the program area.
     *
     * @effect The dragged block is disconnected in the program area.
     * @effect The dragged block and each of the blocks connected to that block are connected with other
     *         blocks if possible.
     */
    private void handleBlockFromProgramAreaToProgramArea() {
        programArea.disconnectInProgramArea(draggedBlocks.get(0));

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
     * Checks if a given list of gui blocks is in a given collision rectangle panel.
     *
     * @param panel the given panel
     * @param blocks the given list of gui blocks
     *
     * @return if the given list of gui blocks is in the given panel.
     */
    private boolean isInPanelAny(CollisionRectangle panel, List<GUIBlock> blocks) {
        return blocks.stream().anyMatch(b -> b.isInside(panel));
    }

    private final class GUIBlockHandlerSnapshot implements Snapshot {

        private final List<Position> blockPositionsSnapshot = new ArrayList<>(blockPositions);
        private final List<Integer> paletteIndicesSnapshot = new ArrayList<>(paletteIndices);

        @Override
        public String getName() {
            return null;
        }

        @Override
        public LocalDateTime getSnapshotDate() {
            return null;
        }
    }
}
