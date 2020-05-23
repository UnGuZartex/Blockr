package GUI.Components;

import GUI.Blocks.GUIBlock;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Panel.GamePanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import GameWorldAPI.Utility.Snapshot;
import Utility.Position;

import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A class used to handle the movement of GUI blocks between the
 * palette and program area panels.
 *
 * @invar The palette panel of this gui block handler may not be null.
 *        | palette != null
 * @invar The program area panel of this gui block handler may not be null.
 *        | programArea != null
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
     * Variable referring to the block position data in the gui block handler.
     */
    private List<Position> blockPositions = new ArrayList<>();

    /**
     * Variable referring to the palette indices data in the gui block handler.
     */
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
     * @throws IllegalArgumentException
     *         When the given palette is not effective.
     * @throws IllegalArgumentException
     *         When the given program area is not effective.
     */
    public GUIBlockHandler(PalettePanel palette, ProgramAreaPanel programArea) throws IllegalArgumentException {
        if (palette == null) {
            throw new IllegalArgumentException("The given palette is not effective!");
        }
        if (programArea == null) {
            throw new IllegalArgumentException("The given program area is not effective!");
        }

        this.palette = palette;
        this.programArea = programArea;
    }

    public boolean blocksAreDragged() {
        return draggedBlocks != null;
    }

    /**
     * Handle the current mouse event with the given id, x and y coordinate.
     *
     * @param id The given mouse event id.
     * @param x The given mouse x-coordinate.
     * @param y The given mouse y-coordinate.
     *
     * @effect The mouse event is handled accordingly depending on the given id.
     */
    public void handleMouseEvent(int id, int x, int y) {
        programArea.update();
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
     * Create a new gui block handler snapshot.
     *
     * @return A new gui block handler snapshot.
     */
    public Snapshot createSnapshot() {
        return new GUIBlockHandlerSnapshot();
    }

    /**
     * Load a gui block handler snapshot.
     *
     * @param snapshot The given gui block handler snapshot.
     *
     * @effect The program area deletes the current blocks from the program area panel.
     * @effect New blocks are added to the program area, based on the snapshot data.
     * @effect New block data is collected.
     */
    public void loadSnapshot(Snapshot snapshot) {
        GUIBlockHandlerSnapshot guiSnapshot = (GUIBlockHandlerSnapshot) snapshot;
        programArea.deleteBlockFromProgramArea(programArea.getBlocks());
        for (int i = 0; i < guiSnapshot.blockPositionsSnapshot.size(); i++) {
            addPaletteBlockToProgramArea(guiSnapshot.blockPositionsSnapshot.get(i), guiSnapshot.paletteIndicesSnapshot.get(i));
        }
        collectBlockData();
    }

    /**
     * Handle the event where the mouse is pressed depending on
     * where the mouse was pressed.
     *
     * @param x the given mouse x-coordinate
     * @param y the given mouse y-coordinate
     *
     * @post The list of dragged blocks is set to the blocks connected to the current dragged block.
     * @post The drag delta of the dragged block is set accordingly.
     * @post The last valid position of the dragged block is set accordingly.
     * @post The source panel where the dragged block came from is set accordingly.
     *
     * @effect If the mouse was pressed in the program area on a block, a temporary block is set
     *         in the program area.
     * @effect The current block data is collected.
     */
    private void handleMousePressed(int x, int y) {
        if (draggedBlocks == null) {
            int draggedBlockIndex = palette.getSelectedBlockIndex(x, y);
            boolean programAreaContainsMouse = programArea.getBlocks().stream().anyMatch(b -> b.contains(x, y));
            GUIBlock draggedBlock;

            if (programAreaContainsMouse || draggedBlockIndex != -1) {
                if (draggedBlockIndex != -1) {
                    draggedBlock = palette.getNewBlock(draggedBlockIndex);
                    draggedBlocks = new ArrayList<>(List.of(draggedBlock));
                    blockSourcePanel = palette;
                    programArea.setTemporaryBlockPair(new AbstractMap.SimpleEntry<>(draggedBlock, draggedBlockIndex));
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
    }

    /**
     * Collect the data of all the blocks.
     *
     * @effect The block positions is set to the positions of the gui blocks in the program area.
     * @effect The palette indices are set to the indices of the blocks in the program area.
     */
    private void collectBlockData() {
        System.out.println("Reached");
        blockPositions = new ArrayList<>();
        paletteIndices = new ArrayList<>();

        List<GUIBlock> blocks = programArea.getBlocks();
        Collections.sort(blocks);

        for (GUIBlock block : blocks) {
            Map.Entry<GUIBlock, Integer> entry = programArea.getBlockPairs()
                    .stream().filter(x -> x.getKey().equals(block)).findAny().orElse(null);
            blockPositions.add(entry.getKey().getPosition());
            paletteIndices.add(entry.getValue());
        }
    }

    /**
     * Add a new block from the palette to the program area panel with a given
     * block position, and a palette index.
     *
     * @param pos The given position.
     * @param paletteIndex The given palette index.
     *
     * @post The dragged blocks are set to a new block generated from the
     *       given palette index.
     * @post The drag delta is set to 0 to ensure valid block movement.
     *
     * @effect A temporary block pair is set in the program area
     * @effect Dragging the mouse is simulated.
     * @effect Releasing the mouse is simulated.
     */
    private void addPaletteBlockToProgramArea(Position pos, int paletteIndex) {

        GUIBlock draggedBlock = palette.getNewBlock(paletteIndex);
        draggedBlocks = new ArrayList<>(List.of(draggedBlock));
        blockSourcePanel = palette;
        programArea.setTemporaryBlockPair(new AbstractMap.SimpleEntry<>(draggedBlock, paletteIndex));
        dragDelta = new Position(0, 0);

        handleMouseDragged(pos.getX(), pos.getY());
        handleMouseReleased();
    }

    /**
     * Handle the event where the mouse is released depending on
     * where the mouse was pressed the first time and where it was released.
     *
     * @post The dragged blocks list is reset.
     *
     * @effect The temporary block in the program area is reset.
     * @effect The program area is notified that the program is in its default state again.
     * @effect If the dragged block is placed on an illegal position, the block is set to its
     *         last known location, and the height of the structure containing the block is reset
     *         to before the block was disconnected.
     * @effect If the dragged block is dragged to the program area, the event is handled accordingly.
     * @effect If the dragged block was dragged from the program area to the palette, the program area disconnects
     *         and deletes the block from the program area.
     * @effect New block data is collected.
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

                if (blockSourcePanel == palette) {
                    draggedBlocks.get(0).terminate();
                }
            }

            programArea.setTemporaryBlockPair(null);
            programArea.onProgramDefaultState();
            draggedBlocks = null;
            collectBlockData();
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
        programArea.addTemporaryBlockToProgramArea();
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


    /**
     * A private inner class for gui block handler snapshots.
     */
    private final class GUIBlockHandlerSnapshot implements Snapshot {

        /**
         * Variable referring to the stored block positions currently set in the gui block handler.
         */
        private final List<Position> blockPositionsSnapshot = new ArrayList<>(blockPositions);

        /**
         * Variable referring to the stored palette indices currently set in the gui block handler.
         */
        private final List<Integer> paletteIndicesSnapshot = new ArrayList<>(paletteIndices);

        /**
         * Variable referring to the creation time of this snapshot.
         */
        private final LocalDateTime creationTime = LocalDateTime.now();

        /**
         * Get the name of this snapshot.
         *
         * @return the name of this snapshot.
         */
        @Override
        public String getName() {
            return "GUIBlockHandler snapshot " + this;
        }

        /**
         * Get the snapshot date.
         *
         * @return The the time this snapshot was initialised.
         */
        @Override
        public LocalDateTime getSnapshotDate() {
            return creationTime;
        }
    }
}
