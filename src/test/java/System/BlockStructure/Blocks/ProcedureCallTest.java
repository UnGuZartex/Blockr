package System.BlockStructure.Blocks;

import System.BlockStructure.Functionality.DummyFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class ProcedureCallTest {

    ProcedureBlock procedure, procedureTop, procedureBottom;
    ProcedureCall call, callTop, callBottom, callClone;
    Block blockBottom, blockTop;
    ConnectionHandler handler;

    @BeforeEach
    void setUp() {
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
        assertNull(call.getBlockAtIndex(-1, new Stack<>()));
    }

    @Test
    void getBlockAtIndex_noNext() {
        procedureBottom.terminate();
        assertFalse(callBottom.hasNext());
        assertEquals(callBottom, callBottom.getBlockAtIndex(0, new Stack<>()));
        assertEquals(blockBottom, callBottom.getBlockAtIndex(1, new Stack<>()));
    }

    @Test
    void getBlockAtIndex() {
        assertEquals(call, call.getBlockAtIndex(0, new Stack<>()));
        assertEquals(procedure, call.getBlockAtIndex(1, new Stack<>()));

        assertEquals(callBottom, callBottom.getBlockAtIndex(0, new Stack<>()));
        assertEquals(procedureBottom, callBottom.getBlockAtIndex(1, new Stack<>()));
        assertEquals(blockBottom, callBottom.getBlockAtIndex(2, new Stack<>()));
    }

    @Test
    void getIndexOfBlock_illegalBlock() {
        assertEquals(-1, call.getIndexOfBlock(null, new Stack<>()));
    }

    @Test
    void getIndexOfBlock_noNext() {
        procedureBottom.terminate();
        assertFalse(callBottom.hasNext());
        assertEquals(0, callBottom.getIndexOfBlock(callBottom, new Stack<>()));
        assertEquals(1, callBottom.getIndexOfBlock(blockBottom, new Stack<>()));
    }

    @Test
    void getIndexOfBlock() {
        assertEquals(0, call.getIndexOfBlock(call, new Stack<>()));
        assertEquals(1, call.getIndexOfBlock(procedure, new Stack<>()));

        assertEquals(0, callBottom.getIndexOfBlock(callBottom, new Stack<>()));
        assertEquals(1, callBottom.getIndexOfBlock(procedureBottom, new Stack<>()));
        assertEquals(2, callBottom.getIndexOfBlock(blockBottom, new Stack<>()));
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
    void onEvent_invalidEvent() {
        assertThrows(IllegalArgumentException.class, () -> call.onEvent("InvalidEventName"));
    }

    @Test
    void onEvent() {
        assertFalse(call.isTerminated());
        assertFalse(callClone.isTerminated());
        call.onEvent("ProcedureDel");
        assertTrue(call.isTerminated());
        assertTrue(callClone.isTerminated());

        assertTrue(callTop.isConnectedOnMain());
        callTop.onEvent("ProcedureDel");
        assertFalse(callTop.isConnectedOnMain());
    }
}