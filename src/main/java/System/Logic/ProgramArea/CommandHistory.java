package System.Logic.ProgramArea;

import GameWorldAPI.GameWorld.Result;
import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Functionality.BlockFunctionality;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Stack;

public class CommandHistory {
    /**
     * Variable referring to the undo stack.
     */
    private final Stack<BlockFunctionality> undoStackFunc = new Stack<>();
    /**
     * Variable referring to the redo stack.
     */
    private final Stack<BlockFunctionality> redoStackFunc = new Stack<>();

    /**
     * Variable referring to the undo stack.
     */
    private final Stack<Map.Entry<Result, Block>> undoStackResult = new Stack<>();
    /**
     * Variable referring to the redo stack.
     */
    private final Stack<Map.Entry<Result, Block>> redoStackResult  = new Stack<>();

    public Result execute(Block currentBlock) {
        BlockFunctionality command = currentBlock.getFunctionality().copy();
        currentBlock.setFunctionality(command);
        redoStackFunc.clear();
        redoStackResult.clear();
        Result endresult = command.execute();
        undoStackFunc.push(command);
        undoStackResult.push(new AbstractMap.SimpleEntry<>(endresult,currentBlock));
        return endresult;
    }

    public Map.Entry<Result, Block> undo() {
        if (undoStackFunc.isEmpty() || undoStackResult.isEmpty())
            return new AbstractMap.SimpleEntry<>(null,null);
        BlockFunctionality func = undoStackFunc.pop();
        func.undo();
        redoStackFunc.push(func);
        Map.Entry<Result,Block> resultBlockEntry = undoStackResult.pop();
        resultBlockEntry.getValue().setFunctionality(func);

        redoStackResult.push(resultBlockEntry);
        return resultBlockEntry;
    }

    public Map.Entry<Result, Block> redo() {
        if (redoStackFunc .isEmpty() || redoStackResult.isEmpty())
            return new AbstractMap.SimpleEntry<>(null,null);
        BlockFunctionality func = redoStackFunc.pop();
        func.redo();
        undoStackFunc.push(func);
        Map.Entry<Result,Block> resultBlockEntry = redoStackResult.pop();
        resultBlockEntry.getValue().setFunctionality(func);

        undoStackResult.push(resultBlockEntry);
        return resultBlockEntry;
    }

    public Map.Entry<Result, Block> reset() {
        if (undoStackFunc.isEmpty() || undoStackResult.isEmpty()) {
            return new AbstractMap.SimpleEntry<>(Result.SUCCESS, null);
        }
        BlockFunctionality func = undoStackFunc.firstElement();
        func.undo();
        Map.Entry<Result,Block> resultBlockEntry = undoStackResult.firstElement();

        redoStackFunc.clear();
        undoStackFunc.clear();
        redoStackResult.clear();
        undoStackResult.clear();
        return resultBlockEntry;
    }
}
