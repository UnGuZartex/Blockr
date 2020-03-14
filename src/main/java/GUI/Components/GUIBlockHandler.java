package GUI.Components;

import Controllers.ProgramController;
import GUI.BlockrCanvas;
import GUI.Blocks.GUIBlock;
import GUI.CollisionShapes.CollisionRectangle;
import GUI.Panel.PalettePanel;
import GUI.Panel.ProgramAreaPanel;
import Utility.Position;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class GUIBlockHandler {

    private final PalettePanel palette;
    private final ProgramAreaPanel programArea;
    private GUIBlock draggedBlock;
    private List<GUIBlock> draggedBlocks;
    private List<GUIBlock> blocks = new ArrayList<>();
    private Position oldBlockPosition, dragDelta, mousePos;

    public GUIBlockHandler(PalettePanel palette, ProgramAreaPanel programArea) {
        this.palette = palette;
        this.programArea = programArea;
        blocks.addAll(palette.blocks);
    }

    public void handleMouseEvent(int id, int x, int y, ProgramController programController) {

        mousePos = new Position(x, y);

        if (id == MouseEvent.MOUSE_PRESSED && draggedBlock == null && blocks.stream().anyMatch(b -> b.contains(x, y))) {
            OptionalInt blockIndex = IntStream.range(0, blocks.size()).filter(i -> blocks.get(i).contains(x, y)).reduce((first, second) -> second);
            draggedBlock = blocks.get(blockIndex.getAsInt());
            draggedBlocks = draggedBlock.getConnectedBlocks();
            draggedBlock.disconnect();

            // Get blocks on first layer zo they draw first
            blocks.remove(draggedBlocks);
            blocks.addAll(draggedBlocks);

            dragDelta = new Position(draggedBlock.getX() - x, draggedBlock.getY() - y);
        }
        if (id == MouseEvent.MOUSE_RELEASED && draggedBlock != null) {
            draggedBlock.setPosition(dragDelta.getX() + x, dragDelta.getY() + y);
            Optional<GUIBlock> connectedBlock = blocks.stream().filter(b -> b.intersectsWithConnector(draggedBlock)).findAny();

            if (connectedBlock.isPresent()) {
                draggedBlock.connectWithStaticBlock(connectedBlock.get(), programController.getController());
            }

            draggedBlock = null;
        }
    }

    public void paint(Graphics g) {

        for (GUIBlock block : blocks) {
            block.paint(g);
        }

        if (draggedBlock != null) {
            draggedBlock.setPosition(mousePos.getX() + dragDelta.getX(), mousePos.getY() + dragDelta.getY());
        }
    }

    //TODO private boolean is

    private boolean isInPanel(CollisionRectangle panel, GUIBlock block) {
        return block.isInside(panel);
    }
}
