package System.UseCases;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.LevelInitializer;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.CommandHistory;
import System.Logic.ProgramArea.ConnectionHandler;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DisconnectBlockTest {

    ConnectionHandler connectionHandler;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock, procedure;

    PABlockHandler paBlockHandler;
    FunctionalBlock functionalBlockUp, functionalBlockUnder, functionalBlockIn;
    CavityBlock cavityBlock, cavityBlockIn, cavityBlockUnder;
    OperationalBlock operationalBlock1, operationalBlock2;
    PredicateBlock predicateBlock1, predicateBlock2;
    int initialNbBlocks;

    PABlockHandler paBlockHandlerProcedures;
    ProcedureBlock procedure1, procedure2, procedure3;
    ProcedureCall call1In1, call2In3Up, call3In3Under;
    FunctionalBlock functionalIn2, functionalUnderCall3;
    CavityBlock cavity1, cavity3;
    int initialNbBlocksProcedures;


    @BeforeEach
    void setUp() {
        connectionHandler = new ConnectionHandler();

        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();

        moveForward = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(0))); // MoveForwardAction
        turnLeft = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(1))); // TurnLeftAction
        turnRight = new FunctionalBlock(new ActionFunctionality(type.getAllActions().get(2))); // TurnRightAction
        wallInFront = new PredicateBlock(new PredicateFunctionality(type.getAllPredicates().get(0))); // WallInFrontPredicate
        notBlock = new NotBlock();
        whileBlock = new WhileBlock();
        ifBlock = new IfBlock();
        procedure = new ProcedureBlock();

        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)), new ProgramArea(gameWorld, new CommandHistory()));

        functionalBlockUp = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        functionalBlockUnder = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        functionalBlockIn = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        cavityBlock = (CavityBlock) paBlockHandler.getFromPalette(5);
        cavityBlockIn = (CavityBlock) paBlockHandler.getFromPalette(6);
        cavityBlockUnder = (CavityBlock) paBlockHandler.getFromPalette(5);
        operationalBlock1 = (OperationalBlock) paBlockHandler.getFromPalette(4);
        operationalBlock2 = (OperationalBlock) paBlockHandler.getFromPalette(4);
        predicateBlock1 = (PredicateBlock) paBlockHandler.getFromPalette(3);
        predicateBlock2 = (PredicateBlock) paBlockHandler.getFromPalette(3);

//                functionalBlockUp
//                cavityBlock : operationalBlock1 : operationalBlock2 : predicateBlock1
//                | cavityBlockIn : predicateBlock2
//                | | functionalBlockIn
//                | ---
//                | functionalBlockUnder
//                | cavityBlockUnder
//                | |
//                | ---
//                ---

        paBlockHandler.addToPA(functionalBlockUp);
        paBlockHandler.connectToExistingBlock(cavityBlock, functionalBlockUp.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(operationalBlock1, cavityBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(operationalBlock2, operationalBlock1.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(predicateBlock1, operationalBlock2.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(cavityBlockIn, cavityBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(predicateBlock2, cavityBlockIn.getConditionalSubConnector());

        paBlockHandler.connectToExistingBlock(functionalBlockIn, cavityBlockIn.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(functionalBlockUnder, cavityBlockIn.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(cavityBlockUnder, functionalBlockUnder.getSubConnectorAt(0));

        initialNbBlocks = 10;
        assertEquals(initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());

//              procedure1      procedure2            procedure3
//              | call1In1      | functionalIn2       | cavity3
//              | cavity1       ---                   | | call2In3Up
//              ---                                   | | call3In3Under
//                                                    | | functionalUnderCall3
//                                                    | ---
//                                                    ---

        paBlockHandlerProcedures = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock, procedure)), new ProgramArea(gameWorld, new CommandHistory()));
        procedure1 = (ProcedureBlock) paBlockHandlerProcedures.getFromPalette(7);
        paBlockHandlerProcedures.addToPA(procedure1);
        procedure2 = (ProcedureBlock) paBlockHandlerProcedures.getFromPalette(7);
        paBlockHandlerProcedures.addToPA(procedure2);
        procedure3 = (ProcedureBlock) paBlockHandlerProcedures.getFromPalette(7);
        paBlockHandlerProcedures.addToPA(procedure3);
        call1In1 = (ProcedureCall) paBlockHandlerProcedures.getFromPalette(8);
        call2In3Up = (ProcedureCall) paBlockHandlerProcedures.getFromPalette(9);
        call3In3Under = (ProcedureCall) paBlockHandlerProcedures.getFromPalette(10);
        functionalIn2 = (FunctionalBlock) paBlockHandlerProcedures.getFromPalette(0);
        functionalUnderCall3 = (FunctionalBlock) paBlockHandlerProcedures.getFromPalette(2);
        cavity1 = (CavityBlock) paBlockHandlerProcedures.getFromPalette(5);
        cavity3 = (CavityBlock) paBlockHandlerProcedures.getFromPalette(6);

        connectionHandler.connect(call1In1, procedure1.getSubConnectorAt(0));
        connectionHandler.connect(cavity1, call1In1.getSubConnectorAt(0));

        connectionHandler.connect(functionalIn2, procedure2.getSubConnectorAt(0));

        connectionHandler.connect(cavity3, procedure3.getSubConnectorAt(0));
        connectionHandler.connect(call2In3Up, cavity3.getCavitySubConnector());
        connectionHandler.connect(call3In3Under, call2In3Up.getSubConnectorAt(0));
        connectionHandler.connect(functionalUnderCall3, call3In3Under.getSubConnectorAt(0));

        initialNbBlocksProcedures = 10;
        assertEquals(initialNbBlocksProcedures, paBlockHandlerProcedures.getPA().getAllBlocksCount());

    }

    @AfterEach
    void tearDown() {
        assertEquals(initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
        assertEquals(initialNbBlocksProcedures, paBlockHandlerProcedures.getPA().getAllBlocksCount());
        connectionHandler = null;
        type = null;
        gameWorld = null;
        moveForward = null;
        turnLeft = null;
        turnRight = null;
        wallInFront = null;
        notBlock = null;
        whileBlock = null;
        ifBlock = null;
        functionalBlockUp = null;
        functionalBlockUnder = null;
        functionalBlockIn = null;
        cavityBlock = null;
        cavityBlockIn = null;
        cavityBlockUnder = null;
        operationalBlock1 = null;
        operationalBlock2 = null;
        predicateBlock1 = null;
        predicateBlock2 = null;
        paBlockHandlerProcedures = null;
        procedure1 = null;
        procedure2 = null;
        procedure3 = null;
        call1In1 = null;
        call2In3Up = null;
        call3In3Under = null;
        functionalIn2 = null;
        functionalUnderCall3 = null;
        cavity1 = null;
        cavity3 = null;
    }

    @Test
    void underFunctional() {
        assertTrue(cavityBlock.getMainConnector().isConnected());
        assertTrue(functionalBlockUp.getSubConnectorAt(0).isConnected());
        assertEquals(functionalBlockUp, cavityBlock.getMainConnector().getConnectedBlock());
        assertEquals(cavityBlock, functionalBlockUp.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandler.disconnectInPA(cavityBlock);

        assertFalse(cavityBlock.getMainConnector().isConnected());
        assertFalse(functionalBlockUp.getSubConnectorAt(0).isConnected());
    }

    @Test
    void functionalInCavity() {
        assertTrue(functionalBlockIn.getMainConnector().isConnected());
        assertTrue(cavityBlockIn.getCavitySubConnector().isConnected());
        assertEquals(cavityBlockIn, functionalBlockIn.getMainConnector().getConnectedBlock());
        assertEquals(functionalBlockIn, cavityBlockIn.getCavitySubConnector().getConnectedBlock());

        paBlockHandler.disconnectInPA(functionalBlockIn);

        assertFalse(functionalBlockIn.getMainConnector().isConnected());
        assertFalse(cavityBlockIn.getCavitySubConnector().isConnected());
    }

    @Test
    void cavityInCavity() {
        assertTrue(cavityBlockIn.getMainConnector().isConnected());
        assertTrue(cavityBlock.getCavitySubConnector().isConnected());
        assertEquals(cavityBlock, cavityBlockIn.getMainConnector().getConnectedBlock());
        assertEquals(cavityBlockIn, cavityBlock.getCavitySubConnector().getConnectedBlock());

        paBlockHandler.disconnectInPA(cavityBlockIn);

        assertFalse(cavityBlockIn.getMainConnector().isConnected());
        assertFalse(cavityBlock.getCavitySubConnector().isConnected());
    }

    @Test
    void functionalUnderCavity() {
        assertTrue(functionalBlockUnder.getMainConnector().isConnected());
        assertTrue(cavityBlockIn.getSubConnectorAt(0).isConnected());
        assertEquals(cavityBlockIn, functionalBlockUnder.getMainConnector().getConnectedBlock());
        assertEquals(functionalBlockUnder, cavityBlockIn.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandler.disconnectInPA(functionalBlockUnder);

        assertFalse(functionalBlockUnder.getMainConnector().isConnected());
        assertFalse(cavityBlockIn.getSubConnectorAt(0).isConnected());
    }

    @Test
    void cavityUnderFunctional() {
        assertTrue(cavityBlockUnder.getMainConnector().isConnected());
        assertTrue(functionalBlockUnder.getSubConnectorAt(0).isConnected());
        assertEquals(functionalBlockUnder, cavityBlockUnder.getMainConnector().getConnectedBlock());
        assertEquals(cavityBlockUnder, functionalBlockUnder.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandler.disconnectInPA(cavityBlockUnder);

        assertFalse(cavityBlockUnder.getMainConnector().isConnected());
        assertFalse(functionalBlockUnder.getSubConnectorAt(0).isConnected());
    }

    @Test
    void operationalFromCavity() {
        assertTrue(operationalBlock1.getMainConnector().isConnected());
        assertTrue(cavityBlock.getConditionalSubConnector().isConnected());
        assertEquals(cavityBlock, operationalBlock1.getMainConnector().getConnectedBlock());
        assertEquals(operationalBlock1, cavityBlock.getConditionalSubConnector().getConnectedBlock());

        paBlockHandler.disconnectInPA(operationalBlock1);

        assertFalse(operationalBlock1.getMainConnector().isConnected());
        assertFalse(cavityBlock.getConditionalSubConnector().isConnected());
    }

    @Test
    void operationalFromOperational() {
        assertTrue(operationalBlock2.getMainConnector().isConnected());
        assertTrue(operationalBlock1.getSubConnectorAt(0).isConnected());
        assertEquals(operationalBlock1, operationalBlock2.getMainConnector().getConnectedBlock());
        assertEquals(operationalBlock2, operationalBlock1.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandler.disconnectInPA(operationalBlock2);

        assertFalse(operationalBlock2.getMainConnector().isConnected());
        assertFalse(operationalBlock1.getSubConnectorAt(0).isConnected());
    }

    @Test
    void statementFromOperational() {
        assertTrue(predicateBlock1.getMainConnector().isConnected());
        assertTrue(operationalBlock2.getSubConnectorAt(0).isConnected());
        assertEquals(operationalBlock2, predicateBlock1.getMainConnector().getConnectedBlock());
        assertEquals(predicateBlock1, operationalBlock2.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandler.disconnectInPA(predicateBlock1);

        assertFalse(predicateBlock1.getMainConnector().isConnected());
        assertFalse(operationalBlock2.getSubConnectorAt(0).isConnected());
    }

    @Test
    void statementFromCavity() {
        assertTrue(predicateBlock2.getMainConnector().isConnected());
        assertTrue(cavityBlockIn.getConditionalSubConnector().isConnected());
        assertEquals(cavityBlockIn, predicateBlock2.getMainConnector().getConnectedBlock());
        assertEquals(predicateBlock2, cavityBlockIn.getConditionalSubConnector().getConnectedBlock());

        paBlockHandler.disconnectInPA(predicateBlock2);

        assertFalse(predicateBlock2.getMainConnector().isConnected());
        assertFalse(cavityBlockIn.getConditionalSubConnector().isConnected());
    }

    @Test
    void callFromProcedure() {
        assertTrue(call1In1.getMainConnector().isConnected());
        assertTrue(procedure1.getSubConnectorAt(0).isConnected());
        assertEquals(procedure1, call1In1.getMainConnector().getConnectedBlock());
        assertEquals(call1In1, procedure1.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandlerProcedures.disconnectInPA(call1In1);

        assertFalse(call1In1.getMainConnector().isConnected());
        assertFalse(procedure1.getSubConnectorAt(0).isConnected());
    }

    @Test
    void cavityFromCall() {
        assertTrue(cavity1.getMainConnector().isConnected());
        assertTrue(call1In1.getSubConnectorAt(0).isConnected());
        assertEquals(call1In1, cavity1.getMainConnector().getConnectedBlock());
        assertEquals(cavity1, call1In1.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandlerProcedures.disconnectInPA(cavity1);

        assertFalse(cavity1.getMainConnector().isConnected());
        assertFalse(call1In1.getSubConnectorAt(0).isConnected());
    }

    @Test
    void functionalFromProcedure() {
        assertTrue(functionalIn2.getMainConnector().isConnected());
        assertTrue(procedure2.getSubConnectorAt(0).isConnected());
        assertEquals(procedure2, functionalIn2.getMainConnector().getConnectedBlock());
        assertEquals(functionalIn2, procedure2.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandlerProcedures.disconnectInPA(functionalIn2);

        assertFalse(functionalIn2.getMainConnector().isConnected());
        assertFalse(procedure2.getSubConnectorAt(0).isConnected());
    }

    @Test
    void cavityFromProcedure() {
        assertTrue(cavity3.getMainConnector().isConnected());
        assertTrue(procedure3.getSubConnectorAt(0).isConnected());
        assertEquals(procedure3, cavity3.getMainConnector().getConnectedBlock());
        assertEquals(cavity3, procedure3.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandlerProcedures.disconnectInPA(cavity3);

        assertFalse(cavity3.getMainConnector().isConnected());
        assertFalse(procedure3.getSubConnectorAt(0).isConnected());
    }

    @Test
    void callInCavity() {
        assertTrue(call2In3Up.getMainConnector().isConnected());
        assertTrue(cavity3.getCavitySubConnector().isConnected());
        assertEquals(cavity3, call2In3Up.getMainConnector().getConnectedBlock());
        assertEquals(call2In3Up, cavity3.getCavitySubConnector().getConnectedBlock());

        paBlockHandlerProcedures.disconnectInPA(call2In3Up);

        assertFalse(call2In3Up.getMainConnector().isConnected());
        assertFalse(cavity3.getCavitySubConnector().isConnected());
    }

    @Test
    void callFromCall() {
        assertTrue(call3In3Under.getMainConnector().isConnected());
        assertTrue(call2In3Up.getSubConnectorAt(0).isConnected());
        assertEquals(call2In3Up, call3In3Under.getMainConnector().getConnectedBlock());
        assertEquals(call3In3Under, call2In3Up.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandlerProcedures.disconnectInPA(call3In3Under);

        assertFalse(call3In3Under.getMainConnector().isConnected());
        assertFalse(call2In3Up.getSubConnectorAt(0).isConnected());
    }

    @Test
    void functionalFromCall() {
        assertTrue(functionalUnderCall3.getMainConnector().isConnected());
        assertTrue(call3In3Under.getSubConnectorAt(0).isConnected());
        assertEquals(call3In3Under, functionalUnderCall3.getMainConnector().getConnectedBlock());
        assertEquals(functionalUnderCall3, call3In3Under.getSubConnectorAt(0).getConnectedBlock());

        paBlockHandlerProcedures.disconnectInPA(functionalUnderCall3);

        assertFalse(functionalUnderCall3.getMainConnector().isConnected());
        assertFalse(call3In3Under.getSubConnectorAt(0).isConnected());
    }
}