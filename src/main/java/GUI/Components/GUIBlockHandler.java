package GUI.Components;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;
import GUI.Blocks.GUIConditionalBlock;
import GUI.Blocks.GUIFunctionalityBlock;
import GUI.Blocks.GUIOperatorBlock;
import Utility.Position;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class GUIBlockHandler {
    GUIBlock draggedBlock;
    List<GUIBlock> blocks;
    private Position dragDelta;
    private Position mousePos;

    public GUIBlockHandler() {
        // TODO Wegdoen
        blocks = new ArrayList<>();
        blocks.add(new GUIFunctionalityBlock("8", 500, 500));
        blocks.add(new GUICavityBlock("5",200, 500));
        blocks.add(new GUIOperatorBlock("9",500, 200));
        blocks.add(new GUIConditionalBlock("5", 100, 100));
    }

    public void handleMouseEvent(int id, int x, int y) {

        mousePos = new Position(x, y);

        if (id == MouseEvent.MOUSE_PRESSED && draggedBlock == null && blocks.stream().anyMatch(b -> b.contains(x, y))) {
            OptionalInt blockIndex = IntStream.range(0, blocks.size()).filter(i -> blocks.get(i).contains(x, y)).reduce((first, second) -> second);
            draggedBlock = blocks.get(blockIndex.getAsInt());
            draggedBlock.disconnectMainConnector();
            blocks.remove(blockIndex.getAsInt());
            blocks.add(draggedBlock);
            dragDelta = new Position(draggedBlock.getX() - x, draggedBlock.getY() - y);
        }
        if (id == MouseEvent.MOUSE_RELEASED && draggedBlock != null) {
            draggedBlock.setPosition(dragDelta.getX() + x, dragDelta.getY() + y);
            Optional<GUIBlock> connectedBlock = blocks.stream().filter(b -> b.intersectsWithConnector(draggedBlock)).findAny();

            if (connectedBlock.isPresent()) {
                draggedBlock.connectWithStaticBlock(connectedBlock.get());
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
}
