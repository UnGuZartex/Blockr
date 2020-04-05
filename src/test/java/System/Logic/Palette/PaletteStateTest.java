package System.Logic.Palette;

import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaletteStateTest {

    Palette palette;
    Block block;

    @BeforeEach
    void setUp() {
        palette = new Palette((List<Block>) new IfBlock());
    }

    @AfterEach
    void tearDown() {
        palette = null;
        block = null;
    }

    @Test
    void getNewBlockWithID_InvalidID() {
        assertThrows(IllegalArgumentException.class, () -> { palette.getNewBlockWithID("INVALID ID"); });
    }

    @Test
    void getNewBlockWithID_If() {
        block = palette.getNewBlockWithID("IF");
        assertTrue(block instanceof IfBlock);
        assertTrue(block.getFunctionality() instanceof CavityFunctionality);
    }

    @Test
    void getNewBlockWithID_While() {
        block = palette.getNewBlockWithID("WHILE");
        assertTrue(block instanceof WhileBlock);
        assertTrue(block.getFunctionality() instanceof CavityFunctionality);
    }

    @Test
    void getNewBlockWithID_MoveForward() {
        block = palette.getNewBlockWithID("MOVE FORWARD");
        assertTrue(block instanceof FunctionalBlock);
        assertTrue(block.getFunctionality() instanceof MoveForwardFunctionality);
    }
    @Test
    void getNewBlockWithID_TurnLeft() {
        block = palette.getNewBlockWithID("TURN LEFT");
        assertTrue(block instanceof FunctionalBlock);
        assertTrue(block.getFunctionality() instanceof TurnLeftFunctionality);
    }

    @Test
    void getNewBlockWithID_TurnRight() {
        block = palette.getNewBlockWithID("TURN RIGHT");
        assertTrue(block instanceof FunctionalBlock);
        assertTrue(block.getFunctionality() instanceof TurnRightFunctionality);
    }

    @Test
    void getNewBlockWithID_Not() {
        block = palette.getNewBlockWithID("NOT");
        assertTrue(block instanceof OperationalBlock);
        assertTrue(block.getFunctionality() instanceof NotFunctionality);
    }

    @Test
    void getNewBlockWithID_WallInFront() {
        block = palette.getNewBlockWithID("WALL IN FRONT");
        assertTrue(block instanceof StatementBlock);
        assertTrue(block.getFunctionality() instanceof WallInFrontFunctionality);
    }
}