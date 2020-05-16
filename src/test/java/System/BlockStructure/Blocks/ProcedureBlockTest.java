package System.BlockStructure.Blocks;

import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.DummyFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void getNext() {
        assertEquals(block1, procedure.getNext());
        assertEquals(emptyProcedure.getReturnToBlock(), emptyProcedure.getNext());
        assertEquals(cavity, notCompleteProcedure.getNext());
        assertEquals(call, procedureRecursion.getNext());
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
        assertNull(procedure.getBlockAtIndex(-1));
    }

    @Test
    void getBlockAtIndex_indexOutOfRange() {
        assertNull(procedure.getBlockAtIndex(4));
        assertNull(emptyProcedure.getBlockAtIndex(1));
        assertNull(notCompleteProcedure.getBlockAtIndex(2));
        assertNull(procedureRecursion.getBlockAtIndex(3));
    }

    @Test
    void getBlockAtIndex() {
        assertEquals(procedure, procedure.getBlockAtIndex(0));
        assertEquals(block1, procedure.getBlockAtIndex(1));
        assertEquals(block2, procedure.getBlockAtIndex(2));
        assertEquals(block3, procedure.getBlockAtIndex(3));

        assertEquals(emptyProcedure, emptyProcedure.getBlockAtIndex(0));

        assertEquals(notCompleteProcedure, notCompleteProcedure.getBlockAtIndex(0));
        assertEquals(cavity, notCompleteProcedure.getBlockAtIndex(1));

        assertEquals(procedureRecursion, procedureRecursion.getBlockAtIndex(0));
        assertEquals(call, procedureRecursion.getBlockAtIndex(1));

        assertEquals(procedureCall, procedureCall.getBlockAtIndex(0));
        assertEquals(procedureBlock, procedureCall.getBlockAtIndex(1));
        assertEquals(blockUnderCall, procedureCall.getBlockAtIndex(2));
    }

    @Test
    void getIndexOfBlock_invalidBlock() {
        assertEquals(-1, procedure.getIndexOfBlock(null));
        assertEquals(2, procedure.getIndexOfBlock(cavity));
    }

    @Test
    void getIndexOfBlock_noReturnToBlock() {
        emptyProcedure.setReturnToBlock(null);
        assertEquals(-1, emptyProcedure.getIndexOfBlock(blockUnderCall));
    }

    @Test
    void getIndexOfBlock() {
        assertEquals(0, procedure.getIndexOfBlock(procedure));
        assertEquals(1, procedure.getIndexOfBlock(block1));
        assertEquals(2, procedure.getIndexOfBlock(block2));
        assertEquals(3, procedure.getIndexOfBlock(block3));

        assertEquals(0, emptyProcedure.getIndexOfBlock(emptyProcedure));

        assertEquals(0, notCompleteProcedure.getIndexOfBlock(notCompleteProcedure));
        assertEquals(1, notCompleteProcedure.getIndexOfBlock(cavity));

        assertEquals(0, procedureRecursion.getIndexOfBlock(procedureRecursion));
        assertEquals(1, procedureRecursion.getIndexOfBlock(call));

        assertEquals(2, procedureCall.getIndexOfBlock(blockUnderCall));
        assertEquals(1, procedureCall.getIndexOfBlock(procedureBlock));
        assertEquals(0, procedureCall.getIndexOfBlock(procedureCall));
    }

    @Test
    void isIllegalExtraStartingBlock() {
        assertFalse(procedure.isIllegalExtraStartingBlock());
        assertFalse(emptyProcedure.isIllegalExtraStartingBlock());
        assertFalse(notCompleteProcedure.isIllegalExtraStartingBlock());
        assertFalse(procedureRecursion.isIllegalExtraStartingBlock());
    }

    @Test
    void isPassed() {
        assertFalse(procedure.isPassed());
        assertFalse(emptyProcedure.isPassed());
        assertFalse(notCompleteProcedure.isPassed());
        assertFalse(procedureRecursion.isPassed());
    }
}