package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import java.util.Stack;

/**
 * A class for the execution stack of blocks. This class is a stack
 * for which blocks should be executed next.
 *
 * @invar The stack must exist of valid blocks.
 *        | isValidBlock(this.get(i))
 *
 * @author Alpha-team
 */
public class ExecutionStack extends Stack<Block> {

    /**
     * Checks whether or not the given block is valid.
     *
     * @param block The block to check.
     *
     * @return True if and only if the given block is not null.
     */
    public static boolean isValidBlock(Block block) {
        return block != null;
    }

    /**
     * Push the given block to this execution stack.
     *
     * @param block The block to push.
     *
     * @return The argument passed.
     *
     * @effect Only if the given block is valid, then it is pushed to the stack.
     */
    @Override
    public Block push(Block block) {
        if (isValidBlock(block)) {
            return super.push(block);
        }
        return block;
    }

    /**
     * Pop a block from the stack.
     *
     * @return The block on top of the stack if any exist, otherwise null is returned.
     */
    @Override
    public Block pop() {
        if (isEmpty()) {
            return null;
        } else {
            return super.pop();
        }
    }

    /**
     * Peek the block on the top of the stack.
     *
     * @return The block on top of the stack if it isn't empty, otherwise
     *         is null returned.
     */
    @Override
    public Block peek() {
        if (isEmpty()) {
            return null;
        } else {
            return super.peek();
        }
    }
}