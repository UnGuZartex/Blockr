package GUI.Components;

import Controllers.ProgramController;
import GUI.Blocks.GUIBlock;
import GUI.CollisionShapes.CollisionRectangle;
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
    private GUIBlock draggedBlock;
    private List<GUIBlock> draggedBlocks;
    private List<GUIBlock> blocks = new ArrayList<>();
    private Position lastValidPosition, dragDelta;

    public GUIBlockHandler(PalettePanel palette, ProgramAreaPanel programArea) {
        this.palette = palette;
        this.programArea = programArea;
        blocks.addAll(palette.blocks);
    }

    public void handleMouseEvent(int id, int x, int y, ProgramController programController) {

        if (id == MouseEvent.MOUSE_PRESSED) {
            handleMousePressed(x, y);
        }

        if (id == MouseEvent.MOUSE_RELEASED) {
            handleMouseReleased(x, y, programController);
        }

        if (id == MouseEvent.MOUSE_DRAGGED) {
            handleMouseDragged(x, y);
        }
    }

    private void handleMousePressed(int x, int y) {

        boolean paletteBlockContainsMouse = AnyContains(palette.getBlocks(), x, y);
        boolean programAreaContainsMouse = AnyContains(programArea.getBlocks(), x, y);

        if (paletteBlockContainsMouse || programAreaContainsMouse) {
            if (paletteBlockContainsMouse) {
                draggedBlock = palette.getBlocks().stream().filter(b -> b.contains(x, y)).findAny().get();
            }
            else if (programAreaContainsMouse) {
                draggedBlock = programArea.getBlocks().stream().filter(b -> b.contains(x, y)).reduce((first, second) -> second).get();
                draggedBlocks = draggedBlock.getConnectedBlocks();
                programArea.setBlockDrawLayerFirst(draggedBlocks);
            }

            draggedBlock.disconnect();
            dragDelta = new Position(draggedBlock.getX() - x, draggedBlock.getY() - y);
            lastValidPosition = new Position(x, y);
        }
    }

    private void handleMouseReleased(int x, int y, ProgramController programController) {

        if (draggedBlock != null) {
            draggedBlock.setPosition(dragDelta.getX() + x, dragDelta.getY() + y);
            Optional<GUIBlock> connectedBlock = blocks.stream().filter(b -> b.intersectsWithConnector(draggedBlock)).findAny();

            if (connectedBlock.isPresent()) {
                draggedBlock.connectWithStaticBlock(connectedBlock.get(), programController.getController());
            }

            draggedBlock = null;
        }
    }

    private void handleMouseDragged(int x, int y) {
        if (draggedBlock != null) {
            draggedBlock.setPosition(x + dragDelta.getX(), y + dragDelta.getY());
        }
    }

    private boolean AnyContains(List<GUIBlock> blocks, int x, int y) {
        return blocks.stream().anyMatch(b -> b.contains(x, y));
    }

    private boolean isInPanel(CollisionRectangle panel, GUIBlock block) {
        return block.isInside(panel);
    }
}
