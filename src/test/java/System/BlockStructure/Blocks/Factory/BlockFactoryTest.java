package System.BlockStructure.Blocks.Factory;

import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.hierarchical.ThrowableCollector;

import static org.junit.jupiter.api.Assertions.*;

class BlockFactoryTest {

    BlockFactory notFactory, ifFactory, turnLeftFactory, turnRightFactory,
            moveForwardFactory, whileFactory, wallInFrontFactory;
    Block block;

    @BeforeEach
    void setUp() {
        notFactory = new NotBlockFactory();
        ifFactory = new IfBlockFactory();
        turnLeftFactory = new TurnLeftBlockFactory();
        turnRightFactory = new TurnRightBlockFactory();
        moveForwardFactory = new MoveForwardBlockFactory();
        whileFactory = new WhileBlockFactory();
        wallInFrontFactory = new WallInFrontBlockFactory();
    }

    @AfterEach
    void tearDown() {
        block = null;
        notFactory = null;
        ifFactory = null;
        turnLeftFactory = null;
        turnRightFactory = null;
        moveForwardFactory = null;
        whileFactory = null;
        wallInFrontFactory = null;
    }

    @Test
    void createBlock_Not() {
        block = notFactory.createBlock();
        assertTrue(block instanceof OperationalBlock);
        assertTrue(block.getFunctionality() instanceof NotFunctionality);
    }

    @Test
    void createBlock_If() {
        block = ifFactory.createBlock();
        assertTrue(block instanceof IfBlock);
        assertTrue(block.getFunctionality() instanceof CavityFunctionality);
    }

    @Test
    void createBlock_While() {
        block = whileFactory.createBlock();
        assertTrue(block instanceof WhileBlock);
        assertTrue(block.getFunctionality() instanceof CavityFunctionality);
    }

    @Test
    void createBlock_TurnLeft() {
        block = turnLeftFactory.createBlock();
        assertTrue(block instanceof FunctionalBlock);
        assertTrue(block.getFunctionality() instanceof TurnLeftFunctionality);
    }

    @Test
    void createBlock_TurnRight() {
        block = turnRightFactory.createBlock();
        assertTrue(block instanceof FunctionalBlock);
        assertTrue(block.getFunctionality() instanceof TurnRightFunctionality);
    }

    @Test
    void createBlock_MoveForward() {
        block = moveForwardFactory.createBlock();
        assertTrue(block instanceof FunctionalBlock);
        assertTrue(block.getFunctionality() instanceof MoveForwardFunctionality);
    }

    @Test
    void createBlock_WallInFront() {
        block = wallInFrontFactory.createBlock();
        assertTrue(block instanceof StatementBlock);
        assertTrue(block.getFunctionality() instanceof WallInFrontFunctionality);
    }
}