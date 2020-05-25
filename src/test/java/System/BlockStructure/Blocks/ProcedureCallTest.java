package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.DummyFunctionality;
import System.Logic.ProgramArea.Handlers.ConnectionHandler;
import System.Logic.ProgramArea.Utility.ExecutionStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcedureCallTest {

    ExecutionStack stack;
    ProcedureBlock procedure, procedureTop, procedureBottom;
    ProcedureCall call, callTop, callBottom, callClone;
    Block blockBottom, blockTop;
    ConnectionHandler handler;

    @BeforeEach
    void setUp() {
        stack = new ExecutionStack();
        procedure = new ProcedureBlock();
        procedureTop = new ProcedureBlock();
        procedureBottom = new ProcedureBlock();

        call = new ProcedureCall(procedure);
        callTop = new ProcedureCall(procedureTop);
        callBottom = new ProcedureCall(procedureBottom);
        callClone = (ProcedureCall)call.clone();

        handler = new ConnectionHandler();
        blockBottom = new IfBlock();
        blockTop = new WhileBlock();

        handler.connect(blockBottom, callBottom.getSubConnectorAt(0));
        handler.connect(callTop, blockTop.getSubConnectorAt(0));
    }

    @AfterEach
    void tearDown() {
        stack = null;
        procedure = null;
        procedureTop = null;
        procedureBottom = null;
        call = null;
        callTop = null;
        callBottom = null;
        callClone = null;
        handler = null;
        blockBottom = null;
        blockTop = null;
    }

    @Test
    void procedureBlock_invalidProcedure() {
        assertThrows(IllegalArgumentException.class, () -> new ProcedureCall(null));
    }

    @Test
    void isValidProcedure() {
        assertTrue(ProcedureCall.isValidProcedure(procedure));
        assertTrue(ProcedureCall.isValidProcedure(procedureTop));
        assertTrue(ProcedureCall.isValidProcedure(procedureBottom));
        assertFalse(ProcedureCall.isValidProcedure(null));
    }

    @Test
    void getProcedure() {
        assertEquals(procedure, call.getProcedure());
        assertEquals(procedure, callClone.getProcedure());
        assertEquals(procedureTop, callTop.getProcedure());
        assertEquals(procedureBottom, callBottom.getProcedure());
    }

    @Test
    void unSubscribe() {
        call.unSubscribe(callClone);
        call.terminate();
        assertTrue(call.isTerminated());
        assertFalse(callClone.isTerminated());
    }

    @Test
    void terminate_withListener() {
        call.terminate();
        assertTrue(call.isTerminated());
        assertTrue(callClone.isTerminated());
    }

    @Test
    void terminate_connections() {
        assertTrue(blockTop.getSubConnectorAt(0).isConnected());
        assertTrue(blockBottom.isConnectedOnMain());
        assertFalse(call.isTerminated());
        assertFalse(callTop.isTerminated());
        assertFalse(callBottom.isTerminated());

        call.subscribe(callTop);
        call.subscribe(callBottom);
        call.terminate();

        assertTrue(call.isTerminated());
        assertTrue(callTop.isTerminated());
        assertTrue(callBottom.isTerminated());
        assertFalse(blockTop.getSubConnectorAt(0).isConnected());
        assertFalse(blockBottom.isConnectedOnMain());
    }

    @Test
    void hasProperConnections_hasNoNext() {
        procedure.terminate();
        assertFalse(call.hasNext());
        assertTrue(call.hasProperConnections());
    }

    @Test
    void hasProperConnections_procedureHasNoProperConnections() {
        handler.connect(new IfBlock(), procedure.getSubConnectorAt(0));
        assertFalse(procedure.hasProperConnections());
        assertFalse(call.hasProperConnections());
    }

    @Test
    void hasProperConnections_notConnectedOnBottom() {
        assertFalse(callTop.getSubConnectorAt(0).isConnected());
        assertTrue(procedureTop.hasProperConnections());
        assertTrue(callTop.hasProperConnections());
    }

    @Test
    void hasProperConnections_invalidOnBottom() {
        assertTrue(callBottom.getSubConnectorAt(0).isConnected());
        assertTrue(procedureBottom.hasProperConnections());
        assertFalse(blockBottom.hasProperConnections());
        assertFalse(callBottom.hasProperConnections());
    }

    @Test
    void hasProperConnections_validOnBottom() {
        handler.connect(new FunctionalBlock(new DummyFunctionality()), call.getSubConnectorAt(0));
        assertTrue(call.getSubConnectorAt(0).isConnected());
        assertTrue(procedure.hasProperConnections());
        assertTrue(call.getSubConnectorAt(0).getConnectedBlock().hasProperConnections());
        assertTrue(call.hasProperConnections());
    }

    @Test
    void hasNext_noneTerminatedProcedure() {
        assertFalse(procedure.isTerminated());
        assertTrue(call.hasNext());
    }

    @Test
    void hasNext_terminatedProcedure() {
        procedure.terminate();
        assertTrue(procedure.isTerminated());
        assertFalse(call.hasNext());
    }

    @Test
    void getBlockAtIndex_illegalIndex() {
        assertNull(call.getBlockAtIndex(-1, stack));
    }

    @Test
    void getBlockAtIndex_noNext() {
        procedureBottom.terminate();
        assertFalse(callBottom.hasNext());
        assertEquals(callBottom, callBottom.getBlockAtIndex(0, stack));
        assertEquals(blockBottom, callBottom.getBlockAtIndex(1, stack));
    }

    @Test
    void getBlockAtIndex() {
        assertEquals(call, call.getBlockAtIndex(0, stack));
        assertEquals(procedure, call.getBlockAtIndex(1, stack));

        assertEquals(callBottom, callBottom.getBlockAtIndex(0, stack));
        assertEquals(procedureBottom, callBottom.getBlockAtIndex(1, stack));
        assertEquals(blockBottom, callBottom.getBlockAtIndex(2, stack));
    }

    @Test
    void getIndexOfBlock_illegalBlock() {
        assertEquals(-1, call.getIndexOfBlock(null, stack));
    }

    @Test
    void getIndexOfBlock_noNext() {
        procedureBottom.terminate();
        assertFalse(callBottom.hasNext());
        assertEquals(0, callBottom.getIndexOfBlock(callBottom, stack));
        assertEquals(1, callBottom.getIndexOfBlock(blockBottom, stack));
    }

    @Test
    void getIndexOfBlock() {
        assertEquals(0, call.getIndexOfBlock(call, stack));
        assertEquals(1, call.getIndexOfBlock(procedure, stack));

        assertEquals(0, callBottom.getIndexOfBlock(callBottom, stack));
        assertEquals(1, callBottom.getIndexOfBlock(procedureBottom, stack));
        assertEquals(2, callBottom.getIndexOfBlock(blockBottom, stack));
    }

    @Test
    void testClone() {
        Block clone = call.clone();
        assertNotEquals(clone, call);
        assertEquals(((ProcedureCall)clone).getProcedure(), call.getProcedure());

        call.terminate();
        assertTrue(call.isTerminated());
        assertTrue(clone.isTerminated());
    }

    @Test
    void pushNextBlocks_noNext() {
        call.pushNextBlocks(stack);
        assertEquals(1, stack.size());
        assertEquals(procedure, stack.pop());
    }

    @Test
    void pushNextBlocks_next() {
        callBottom.pushNextBlocks(stack);
        assertEquals(2, stack.size());
        assertEquals(procedureBottom, stack.pop());
        assertEquals(blockBottom, stack.pop());
    }
}