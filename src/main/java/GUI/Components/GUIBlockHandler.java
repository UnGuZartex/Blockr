package GUI.Components;

import Controllers.ConnectionController;
import Controllers.ProgramController;
import GUI.Blocks.GUIBlock;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Panel.GamePanel;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import System.Logic.ProgramArea.Program;
import Utility.Position;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

            palette.update();
            draggedBlock = null;
            draggedBlocks = null;
        }
    }

    private void handleMouseDragged(int x, int y) {
        if (draggedBlock != null) {
            draggedBlock.setPosition(x + dragDelta.getX(), y + dragDelta.getY());
        }
    }

    private void handleBlockFromPaletteToProgramArea() {

        GUIBlock newBlock = palette.getNewBlock(draggedBlock.getId(), draggedBlock.getX(), draggedBlock.getY());
        draggedBlock.setPosition(lastValidPosition.getX(), lastValidPosition.getY());
        programArea.addBlockToProgramArea(newBlock);
        draggedBlock = newBlock;

        Optional<GUIBlock> connectedBlock = programArea.getBlocks().stream().filter(b -> b.intersectsWithConnector(draggedBlock)).findAny();

        if (connectedBlock.isEmpty()) {
            programArea.addBlockToProgramAreaControllerCall(draggedBlock);
        }
        else {
            draggedBlock.connectWithStaticBlock(connectedBlock.get(), programArea.getProgramController(), programArea.getConnectionController());
        }
    }

    private void handleBlockFromProgramAreaToProgramArea() {
        boolean connectionFound = false;
        programArea.disconnectInProgramArea(draggedBlock);
        draggedBlock.disconnectMainConnector();


        for (GUIBlock block : draggedBlocks) {
            Optional<GUIBlock> connectedBlock = programArea.getBlocks().stream().filter(b -> b.intersectsWithConnector(block)).findAny();

            if (connectedBlock.isPresent()) {
                block.connectWithStaticBlock(connectedBlock.get(), programArea.getProgramController(), programArea.getConnectionController());
                connectionFound = true;
                break;
            }
        }
        if (!connectionFound) {
            programArea.addBlockToProgramAreaControllerCall(draggedBlocks.get(0));
        }
    }

    private void handleBlockToPalette() {

        programArea.disconnectInProgramArea(draggedBlock);
        draggedBlock.disconnectMainConnector();

        if (blockSourcePanel == palette) {
            draggedBlock.setPosition(lastValidPosition.getX(), lastValidPosition.getY());
        }
        else if (blockSourcePanel == programArea) {
            programArea.deleteBlockFromProgramArea(draggedBlocks);
        }
    }

    private boolean AnyContains(List<GUIBlock> blocks, int x, int y) {
        return blocks.stream().anyMatch(b -> b.contains(x, y));
    }

    private boolean isInPanel(CollisionRectangle panel, List<GUIBlock> blocks) {
        return blocks.stream().allMatch(b -> b.isInside(panel));
    }

    private boolean isInPanelAny(CollisionRectangle panel, List<GUIBlock> blocks) {
        return blocks.stream().anyMatch(b -> b.isInside(panel));
    }
}
