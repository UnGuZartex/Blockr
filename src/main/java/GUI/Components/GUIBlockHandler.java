package GUI.Components;

import Controllers.ProgramController;
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

    public void handleMouseEvent(int id, int x, int y, ProgramController programController) {

        if (id == MouseEvent.MOUSE_PRESSED) {
            handleMousePressed(x, y);
        }
        else if (id == MouseEvent.MOUSE_RELEASED) {
            handleMouseReleased(x, y, programController);
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
            else if (programAreaContainsMouse) {
                draggedBlock = programArea.getBlocks().stream().filter(b -> b.contains(x, y)).reduce((first, second) -> second).get();
                draggedBlocks = draggedBlock.getConnectedBlocks();
                programArea.setBlockDrawLayerFirst(draggedBlocks);
                blockSourcePanel = programArea;
            }

            draggedBlock.disconnect();
            dragDelta = new Position(draggedBlock.getX() - x, draggedBlock.getY() - y);
            lastValidPosition = new Position(draggedBlock.getX(), draggedBlock.getY());
        }
    }

    private void handleMouseReleased(int x, int y, ProgramController programController) {

        if (draggedBlock != null) {

            System.err.println("RELEASED");

            if (isInPanel(programArea.getPanelRectangle(), draggedBlocks)) {
                System.err.println("PA");
                // van palette naar pa
                if (blockSourcePanel == palette) {
                    GUIBlock newBlock = palette.getNewBlock(draggedBlock.getId(), draggedBlock.getX(), draggedBlock.getY());
                    draggedBlock.setPosition(lastValidPosition.getX(), lastValidPosition.getY());
                    programArea.addBlockToProgramArea(newBlock);
                }

                Optional<GUIBlock> connectedBlock = programArea.getBlocks().stream().filter(b -> b.intersectsWithConnector(draggedBlock)).findAny();
                if (connectedBlock.isPresent()) {
                    draggedBlock.connectWithStaticBlock(connectedBlock.get(), programController.getController());
                }
            }
            // van ? naar palette
            else if (isInPanel(palette.getPanelRectangle(), draggedBlocks)) {
                // delete
            }
            else {
                draggedBlock.setPosition(lastValidPosition.getX(), lastValidPosition.getY());
            }

            //draggedBlock.setPosition(dragDelta.getX() + x, dragDelta.getY() + y);
            /*

            if (connectedBlock.isPresent()) {
                draggedBlock.connectWithStaticBlock(connectedBlock.get(), programController.getController());
            }*/

            draggedBlock = null;
        }
    }

    private void handleMouseDragged(int x, int y) {
        if (draggedBlock != null) {
            System.err.println("DRAGGED");
            draggedBlock.setPosition(x + dragDelta.getX(), y + dragDelta.getY());
        }
    }

    private boolean AnyContains(List<GUIBlock> blocks, int x, int y) {
        return blocks.stream().anyMatch(b -> b.contains(x, y));
    }

    private boolean isInPanel(CollisionRectangle panel, List<GUIBlock> blocks) {
        return blocks.stream().allMatch(b -> b.isInside(panel));
    }
}
