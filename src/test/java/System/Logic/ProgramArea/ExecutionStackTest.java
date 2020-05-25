package System.Logic.ProgramArea;

import System.BlockStructure.Blocks.Block;
import System.BlockStructure.Blocks.IfBlock;
import System.BlockStructure.Blocks.NotBlock;
import System.BlockStructure.Blocks.WhileBlock;
import System.Logic.ProgramArea.Utility.ExecutionStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionStackTest {

    ExecutionStack stack;
    Block block0, block1, block2;

    @BeforeEach
    void setUp() {
        stack = new ExecutionStack();
        block0 = new IfBlock();
        block1 = new WhileBlock();
        block2 = new NotBlock();
    }

    @AfterEach
    void tearDown() {
        stack.clear();
        stack = null;
        block0 = null;
        block1 = null;
        block2 = null;
    }

    @Test
    void isValidBlock_validBlock() {
        assertTrue(ExecutionStack.isValidBlock(block0));
        assertTrue(ExecutionStack.isValidBlock(block1));
        assertTrue(ExecutionStack.isValidBlock(block2));
    }

    @Test
    void isValidBlock_invalidBlock() {
        assertFalse(ExecutionStack.isValidBlock(null));
    }

    @Test
    void push_validBlock() {
        assertEquals(0, stack.size());
        assertEquals(block0, stack.push(block0));
        assertEquals(block0, stack.peek());
        assertEquals(1, stack.size());
        assertEquals(block1, stack.push(block1));
        assertEquals(block1, stack.peek());
        assertEquals(2, stack.size());
        assertEquals(block2, stack.push(block2));
        assertEquals(block2, stack.peek());
        assertEquals(3, stack.size());
    }

    @Test
    void push_invalidBlock() {
        assertEquals(0, stack.size());
        assertNull(stack.push(null));
        assertEquals(0, stack.size());
    }

    @Test
    void pop_notEmpty() {
        stack.push(block0);
        stack.push(block1);
        stack.push(block2);

        assertEquals(3, stack.size());
        assertEquals(block2, stack.pop());
        assertEquals(2, stack.size());
        assertEquals(block1, stack.pop());
        assertEquals(1, stack.size());
        assertEquals(block0, stack.pop());
        assertEquals(0, stack.size());
    }

    @Test
    void pop_empty() {
        assertTrue(stack.isEmpty());
        assertNull(stack.pop());
    }

    @Test
    void peek_notEmpty() {
        stack.push(block0);
        stack.push(block1);
        stack.push(block2);

        assertEquals(3, stack.size());
        assertEquals(block2, stack.peek());
        assertEquals(3, stack.size());
        assertEquals(block2, stack.peek());
        assertEquals(3, stack.size());
    }

    @Test
    void peek_empty() {
        assertTrue(stack.isEmpty());
        assertNull(stack.peek());
    }
}