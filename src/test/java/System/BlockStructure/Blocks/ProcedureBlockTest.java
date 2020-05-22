package System.BlockStructure.Blocks;

import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.DummyFunctionality;
import System.Logic.Palette.Palette;
import System.Logic.ProgramArea.ConnectionHandler;
import System.Logic.ProgramArea.ExecutionStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ProcedureBlockTest {

    ExecutionStack stack;
    GameWorldType type;
    ProcedureBlock emptyProcedure, procedure, notCompleteProcedure, procedureRecursion;
    Block block1, block2, block3, blockRecursion;
    ConnectionHandler handler;
    CavityBlock cavity;
    ProcedureCall call;

    ProcedureBlock procedureBlock;
    Block blockUnderCall;
    ProcedureCall procedureCall;

    Palette palette;

    @BeforeEach
    void setUp() {
        palette = new Palette(new ArrayList<>(Arrays.asList(new WhileBlock(), new IfBlock())));

        stack = new ExecutionStack();

        type = new LevelInitializer();

        procedure = new ProcedureBlock();
        emptyProcedure = new ProcedureBlock();
        notCompleteProcedure = new ProcedureBlock();
        procedureRecursion = new ProcedureBlock();

        handler = new ConnectionHandler();

        block1 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        block2 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        block3 = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        blockRecursion = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        cavity = new IfBlock();
        call = new ProcedureCall(procedureRecursion);

        handler.connect(block1, procedure.getSubConnectorAt(0));
        handler.connect(block2, block1.getSubConnectorAt(0));
        handler.connect(block3, block2.getSubConnectorAt(0));
        handler.connect(call, procedureRecursion.getSubConnectorAt(0));
        handler.connect(blockRecursion, call.getSubConnectorAt(0));
        handler.connect(cavity, notCompleteProcedure.getSubConnectorAt(0));

        procedureBlock = new ProcedureBlock();
        procedureCall = new ProcedureCall(procedureBlock);
        blockUnderCall = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0)));
        handler.connect(blockUnderCall, procedureCall.getSubConnectorAt(0));

        procedure.subscribe(palette);
    }

    @AfterEach
    void tearDown() {
        procedure.unSubscribe(palette);
        stack = null; 
        type = null;
        procedure = null;
        emptyProcedure = null;
        notCompleteProcedure = null;
        procedureRecursion = null;
        handler = null;
        block1 = null;
        block2 = null;
        block3 = null;
        cavity = null;
        call = null;
    }

    @Test
    void getFunctionality() {
        assertTrue(procedure.getFunctionality() instanceof DummyFunctionality);
    }

    @Test
    void getSubConnectors() {
        assertEquals(1, procedure.getSubConnectors().size());
    }

    @Test
    void hasProperConnections() {
        assertTrue(procedure.hasProperConnections());
        assertTrue(emptyProcedure.hasProperConnections());
        assertFalse(notCompleteProcedure.hasProperConnections());
        assertTrue(procedureRecursion.hasProperConnections());
    }

    @Test
    void getMainConnector() {
        assertNull(procedure.getMainConnector());
        assertNull(emptyProcedure.getMainConnector());
        assertNull(notCompleteProcedure.getMainConnector());
        assertNull(procedureRecursion.getMainConnector());
    }

    @Test
    void hasNext() {
        assertTrue(procedure.hasNext());
        assertFalse(emptyProcedure.hasNext());
        assertTrue(notCompleteProcedure.hasNext());
        assertTrue(procedureRecursion.hasNext());
    }

    @Test
    void testClone() {
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(2));
        Block block = procedure.clone();
        assertNotEquals(block, procedure);
        assertNotEquals(block.getFunctionality(), procedure.getFunctionality());
        assertTrue(block instanceof ProcedureBlock);
        assertTrue(block.getFunctionality() instanceof DummyFunctionality);
        assertFalse(block.getSubConnectorAt(0).isConnected());
        palette.getNewBlock(2);
    }

    @Test
    void getBlockAtIndex_negativeIndex() {
        assertNull(procedure.getBlockAtIndex(-1, stack));
    }

    @Test
    void getBlockAtIndex_indexOutOfRange() {
        assertNull(procedure.getBlockAtIndex(4, stack));
        assertNull(emptyProcedure.getBlockAtIndex(1, stack));
        assertNull(notCompleteProcedure.getBlockAtIndex(2, stack));
        assertNull(procedureRecursion.getBlockAtIndex(4, stack));
    }

    @Test
    void getBlockAtIndex() {
        assertEquals(procedure, procedure.getBlockAtIndex(0, stack));
        assertEquals(block1, procedure.getBlockAtIndex(1, stack));
        assertEquals(block2, procedure.getBlockAtIndex(2, stack));
        assertEquals(block3, procedure.getBlockAtIndex(3, stack));

        assertEquals(emptyProcedure, emptyProcedure.getBlockAtIndex(0, stack));

        assertEquals(notCompleteProcedure, notCompleteProcedure.getBlockAtIndex(0, stack));
        assertEquals(cavity, notCompleteProcedure.getBlockAtIndex(1, stack));

        assertEquals(procedureRecursion, procedureRecursion.getBlockAtIndex(0, stack));
        assertEquals(call, procedureRecursion.getBlockAtIndex(1, stack));
        assertEquals(procedureRecursion, procedureRecursion.getBlockAtIndex(2, stack));
        assertEquals(blockRecursion, procedureRecursion.getBlockAtIndex(3, stack));

        assertEquals(procedureCall, procedureCall.getBlockAtIndex(0, stack));
        assertEquals(procedureBlock, procedureCall.getBlockAtIndex(1, stack));
        assertEquals(blockUnderCall, procedureCall.getBlockAtIndex(2, stack));
    }

    @Test
    void getIndexOfBlock_invalidBlock() {
        assertEquals(-1, procedure.getIndexOfBlock(null, stack));
        assertEquals(2, procedure.getIndexOfBlock(cavity, stack));
        assertEquals(-1, emptyProcedure.getIndexOfBlock(cavity, stack));
    }

    @Test
    void getIndexOfBlock() {
        assertEquals(0, procedure.getIndexOfBlock(procedure, stack));
        assertEquals(1, procedure.getIndexOfBlock(block1, stack));
        assertEquals(2, procedure.getIndexOfBlock(block2, stack));
        assertEquals(3, procedure.getIndexOfBlock(block3, stack));

        assertEquals(0, emptyProcedure.getIndexOfBlock(emptyProcedure, stack));

        assertEquals(0, notCompleteProcedure.getIndexOfBlock(notCompleteProcedure, stack));
        assertEquals(1, notCompleteProcedure.getIndexOfBlock(cavity, stack));

        assertEquals(0, procedureRecursion.getIndexOfBlock(procedureRecursion, stack));
        assertEquals(1, procedureRecursion.getIndexOfBlock(call, stack));

        assertEquals(2, procedureCall.getIndexOfBlock(blockUnderCall, stack));
        assertEquals(1, procedureCall.getIndexOfBlock(procedureBlock, stack));
        assertEquals(0, procedureCall.getIndexOfBlock(procedureCall, stack));
    }


    @Test
    void isIllegalExtraStartingBlock() {
        assertFalse(procedure.isIllegalExtraStartingBlock());
        assertFalse(emptyProcedure.isIllegalExtraStartingBlock());
        assertFalse(notCompleteProcedure.isIllegalExtraStartingBlock());
        assertFalse(procedureRecursion.isIllegalExtraStartingBlock());
    }

    @Test
    void pushNextBlocks_blockInProcedure() {
        procedure.pushNextBlocks(stack);
        assertEquals(1, stack.size());
        assertEquals(block1, stack.pop());
    }

    @Test
    void pushNextBlocks_noBlockInProcedure() {
        emptyProcedure.pushNextBlocks(stack);
        assertEquals(0, stack.size());
    }

    @Test
    void terminate() {
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(2));
        palette.onProcedureCreated(procedure);
        palette.getNewBlock(2);
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(3));
        procedure.terminate();
        assertTrue(procedure.isTerminated());
        assertThrows(IndexOutOfBoundsException.class, () -> palette.getNewBlock(2));
    }
}