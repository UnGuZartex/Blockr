package System.BlockStructure.Blocks;

import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.DummyFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class ProcedureBlockTest {

    GameWorldType type;
    ProcedureBlock emptyProcedure, procedure, notCompleteProcedure, procedureRecursion;
    Block block1, block2, block3, blockRecursion;
    ConnectionHandler handler;
    CavityBlock cavity;
    ProcedureCall call;

    ProcedureBlock procedureBlock;
    Block blockUnderCall;
    ProcedureCall procedureCall;

    @BeforeEach
    void setUp() {
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
    }

    @AfterEach
    void tearDown() {
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
        Block block = procedure.clone();
        assertNotEquals(block, procedure);
        assertNotEquals(block.getFunctionality(), procedure.getFunctionality());
        assertTrue(block instanceof ProcedureBlock);
        assertTrue(block.getFunctionality() instanceof DummyFunctionality);
        assertFalse(block.getSubConnectorAt(0).isConnected());
    }

    @Test
    void getBlockAtIndex_negativeIndex() {
        assertNull(procedure.getBlockAtIndex(-1, new Stack<>()));
    }

    @Test
    void getBlockAtIndex_indexOutOfRange() {
        assertNull(procedure.getBlockAtIndex(4, new Stack<>()));
        assertNull(emptyProcedure.getBlockAtIndex(1, new Stack<>()));
        assertNull(notCompleteProcedure.getBlockAtIndex(2, new Stack<>()));
        assertNull(procedureRecursion.getBlockAtIndex(4, new Stack<>()));
    }

    @Test
    void getBlockAtIndex() {
        assertEquals(procedure, procedure.getBlockAtIndex(0, new Stack<>()));
        assertEquals(block1, procedure.getBlockAtIndex(1, new Stack<>()));
        assertEquals(block2, procedure.getBlockAtIndex(2, new Stack<>()));
        assertEquals(block3, procedure.getBlockAtIndex(3, new Stack<>()));

        assertEquals(emptyProcedure, emptyProcedure.getBlockAtIndex(0, new Stack<>()));

        assertEquals(notCompleteProcedure, notCompleteProcedure.getBlockAtIndex(0, new Stack<>()));
        assertEquals(cavity, notCompleteProcedure.getBlockAtIndex(1, new Stack<>()));

        assertEquals(procedureRecursion, procedureRecursion.getBlockAtIndex(0, new Stack<>()));
        assertEquals(call, procedureRecursion.getBlockAtIndex(1, new Stack<>()));
        assertEquals(procedureRecursion, procedureRecursion.getBlockAtIndex(2, new Stack<>()));
        assertEquals(blockRecursion, procedureRecursion.getBlockAtIndex(3, new Stack<>()));

        assertEquals(procedureCall, procedureCall.getBlockAtIndex(0, new Stack<>()));
        assertEquals(procedureBlock, procedureCall.getBlockAtIndex(1, new Stack<>()));
        assertEquals(blockUnderCall, procedureCall.getBlockAtIndex(2, new Stack<>()));
    }

    @Test
    void getIndexOfBlock_invalidBlock() {
        assertEquals(-1, procedure.getIndexOfBlock(null, new Stack<>()));
        assertEquals(2, procedure.getIndexOfBlock(cavity, new Stack<>()));
    }

    @Test
    void getIndexOfBlock() {
        assertEquals(0, procedure.getIndexOfBlock(procedure, new Stack<>()));
        assertEquals(1, procedure.getIndexOfBlock(block1, new Stack<>()));
        assertEquals(2, procedure.getIndexOfBlock(block2, new Stack<>()));
        assertEquals(3, procedure.getIndexOfBlock(block3, new Stack<>()));

        assertEquals(0, emptyProcedure.getIndexOfBlock(emptyProcedure, new Stack<>()));

        assertEquals(0, notCompleteProcedure.getIndexOfBlock(notCompleteProcedure, new Stack<>()));
        assertEquals(1, notCompleteProcedure.getIndexOfBlock(cavity, new Stack<>()));

        assertEquals(0, procedureRecursion.getIndexOfBlock(procedureRecursion, new Stack<>()));
        assertEquals(1, procedureRecursion.getIndexOfBlock(call, new Stack<>()));

        assertEquals(2, procedureCall.getIndexOfBlock(blockUnderCall, new Stack<>()));
        assertEquals(1, procedureCall.getIndexOfBlock(procedureBlock, new Stack<>()));
        assertEquals(0, procedureCall.getIndexOfBlock(procedureCall, new Stack<>()));
    }

    @Test
    void isIllegalExtraStartingBlock() {
        assertFalse(procedure.isIllegalExtraStartingBlock());
        assertFalse(emptyProcedure.isIllegalExtraStartingBlock());
        assertFalse(notCompleteProcedure.isIllegalExtraStartingBlock());
        assertFalse(procedureRecursion.isIllegalExtraStartingBlock());
    }
}