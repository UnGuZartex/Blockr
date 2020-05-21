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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConnectBlockTest {

    ConnectionHandler connectionHandler;
    PABlockHandler paBlockHandler;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
    ProcedureBlock procedure;
    FunctionalBlock functionalBlock;
    CavityBlock cavityBlock;
    OperationalBlock operationalBlock;
    PredicateBlock predicateBlock;
    ProcedureBlock procedureBlock;
    ProcedureCall callBlock;
    int initialNbBlocks;

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

        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock, procedure)), new ProgramArea(gameWorld, new CommandHistory()));

        procedure.subscribe(paBlockHandler.getPalette());
        functionalBlock = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        paBlockHandler.addToPA(functionalBlock);
        cavityBlock = (CavityBlock) paBlockHandler.getFromPalette(5);
        paBlockHandler.addToPA(cavityBlock);
        operationalBlock = (OperationalBlock) paBlockHandler.getFromPalette(4);
        paBlockHandler.addToPA(operationalBlock);
        predicateBlock = (PredicateBlock) paBlockHandler.getFromPalette(3);
        paBlockHandler.addToPA(predicateBlock);
        procedureBlock = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedureBlock);
        callBlock = (ProcedureCall) paBlockHandler.getFromPalette(8);
        paBlockHandler.addToPA(callBlock);

        initialNbBlocks = 6;
        assertEquals(initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }

    @AfterEach
    void tearDown() {
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
        functionalBlock = null;
        cavityBlock = null;
        operationalBlock = null;
        predicateBlock = null;
        procedureBlock = null;
        callBlock = null;
    }

    @Test
    void singleBlock_cavityToFunctional() {
        CavityBlock cavity = (CavityBlock) paBlockHandler.getFromPalette(5);

        paBlockHandler.connectToExistingBlock(cavity, functionalBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(cavity.getMainConnector().isConnected());
        assertEquals(functionalBlock, cavity.getMainConnector().getConnectedBlock());
        assertEquals(cavity, functionalBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_functionalToFunctional() {
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.connectToExistingBlock(functional, functionalBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional.getMainConnector().isConnected());
        assertEquals(functionalBlock, functional.getMainConnector().getConnectedBlock());
        assertEquals(functional, functionalBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void multipleBlocks_toFunctional() {
        FunctionalBlock functional1 = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock functional2 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        connectionHandler.connect(functional2, functional1.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(functional1, functionalBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional1.getMainConnector().isConnected());
        assertEquals(functionalBlock, functional1.getMainConnector().getConnectedBlock());
        assertEquals(functional1, functionalBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_statementToOperational() {
        PredicateBlock statement = (PredicateBlock) paBlockHandler.getFromPalette(3);

        paBlockHandler.connectToExistingBlock(statement, operationalBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(statement.getMainConnector().isConnected());
        assertEquals(operationalBlock, statement.getMainConnector().getConnectedBlock());
        assertEquals(statement, operationalBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_operationalToOperational() {
        OperationalBlock operational = (OperationalBlock) paBlockHandler.getFromPalette(4);

        paBlockHandler.connectToExistingBlock(operational, operationalBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(operational.getMainConnector().isConnected());
        assertEquals(operationalBlock, operational.getMainConnector().getConnectedBlock());
        assertEquals(operational, operationalBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void multipleBlocks_toOperational() {
        OperationalBlock operational = (OperationalBlock) paBlockHandler.getFromPalette(4);
        PredicateBlock statement = (PredicateBlock) paBlockHandler.getFromPalette(3);
        connectionHandler.connect(statement, operational.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(operational, operationalBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(operational.getMainConnector().isConnected());
        assertEquals(operationalBlock, operational.getMainConnector().getConnectedBlock());
        assertEquals(operational, operationalBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_functionalInCavity() {
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(2);

        paBlockHandler.connectToExistingBlock(functional, cavityBlock.getCavitySubConnector());

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional.getMainConnector().isConnected());
        assertEquals(cavityBlock, functional.getMainConnector().getConnectedBlock());
        assertEquals(functional, cavityBlock.getCavitySubConnector().getConnectedBlock());
    }

    @Test
    void singleBlock_functionalUnderCavity() {
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(1);

        paBlockHandler.connectToExistingBlock(functional, cavityBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional.getMainConnector().isConnected());
        assertEquals(cavityBlock, functional.getMainConnector().getConnectedBlock());
        assertEquals(functional, cavityBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_cavityInCavity() {
        FunctionalBlock cavity = (FunctionalBlock) paBlockHandler.getFromPalette(2);

        paBlockHandler.connectToExistingBlock(cavity, cavityBlock.getCavitySubConnector());

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(cavity.getMainConnector().isConnected());
        assertEquals(cavityBlock, cavity.getMainConnector().getConnectedBlock());
        assertEquals(cavity, cavityBlock.getCavitySubConnector().getConnectedBlock());
    }

    @Test
    void singleBlock_cavityUnderCavity() {
        CavityBlock cavity = (CavityBlock) paBlockHandler.getFromPalette(5);

        paBlockHandler.connectToExistingBlock(cavity, cavityBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(cavity.getMainConnector().isConnected());
        assertEquals(cavityBlock, cavity.getMainConnector().getConnectedBlock());
        assertEquals(cavity, cavityBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_operational() {
        OperationalBlock operational = (OperationalBlock) paBlockHandler.getFromPalette(4);

        paBlockHandler.connectToExistingBlock(operational, cavityBlock.getConditionalSubConnector());

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(operational.getMainConnector().isConnected());
        assertEquals(cavityBlock, operational.getMainConnector().getConnectedBlock());
        assertEquals(operational, cavityBlock.getConditionalSubConnector().getConnectedBlock());
    }

    @Test
    void singleBlock_statement() {
        PredicateBlock statement = (PredicateBlock) paBlockHandler.getFromPalette(3);

        paBlockHandler.connectToExistingBlock(statement, cavityBlock.getConditionalSubConnector());

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(statement.getMainConnector().isConnected());
        assertEquals(cavityBlock, statement.getMainConnector().getConnectedBlock());
        assertEquals(statement, cavityBlock.getConditionalSubConnector().getConnectedBlock());
    }

    @Test
    void multipleBlocks_underCavity() {
        FunctionalBlock functional1 = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock functional2 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        connectionHandler.connect(functional2, functional1.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(functional1, cavityBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional1.getMainConnector().isConnected());
        assertEquals(cavityBlock, functional1.getMainConnector().getConnectedBlock());
        assertEquals(functional1, cavityBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void multipleBlocks_inCavity() {
        FunctionalBlock functional1 = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock functional2 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        connectionHandler.connect(functional2, functional1.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(functional1, cavityBlock.getCavitySubConnector());

        assertEquals(initialNbBlocks + 2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional1.getMainConnector().isConnected());
        assertEquals(cavityBlock, functional1.getMainConnector().getConnectedBlock());
        assertEquals(functional1, cavityBlock.getCavitySubConnector().getConnectedBlock());
    }

    @Test
    void multipleBlocks_conditionalToCavity() {
        OperationalBlock operational = (OperationalBlock) paBlockHandler.getFromPalette(4);
        PredicateBlock statement = (PredicateBlock) paBlockHandler.getFromPalette(3);
        connectionHandler.connect(statement, operational.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(operational, cavityBlock.getConditionalSubConnector());

        assertEquals(initialNbBlocks + 2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(operational.getMainConnector().isConnected());
        assertEquals(cavityBlock, operational.getMainConnector().getConnectedBlock());
        assertEquals(operational, cavityBlock.getConditionalSubConnector().getConnectedBlock());
    }

    @Test
    void bothInPA() {
        paBlockHandler.connectToExistingBlock(functionalBlock, cavityBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functionalBlock.getMainConnector().isConnected());
        assertEquals(cavityBlock, functionalBlock.getMainConnector().getConnectedBlock());
        assertEquals(functionalBlock, cavityBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_functionalInProcedure() {
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.connectToExistingBlock(functional, procedureBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional.getMainConnector().isConnected());
        assertEquals(procedureBlock, functional.getMainConnector().getConnectedBlock());
        assertEquals(functional, procedureBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_cavityInProcedure() {
        CavityBlock cavity = (CavityBlock) paBlockHandler.getFromPalette(5);

        paBlockHandler.connectToExistingBlock(cavity, procedureBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(cavity.getMainConnector().isConnected());
        assertEquals(procedureBlock, cavity.getMainConnector().getConnectedBlock());
        assertEquals(cavity, procedureBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void multipleBlocks_inProcedure() {
        FunctionalBlock functional1 = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock functional2 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        connectionHandler.connect(functional2, functional1.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(functional1, procedureBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional1.getMainConnector().isConnected());
        assertEquals(procedureBlock, functional1.getMainConnector().getConnectedBlock());
        assertEquals(functional1, procedureBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_functionalToCall() {
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.connectToExistingBlock(functional, callBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional.getMainConnector().isConnected());
        assertEquals(callBlock, functional.getMainConnector().getConnectedBlock());
        assertEquals(functional, callBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void singleBlock_cavityToCall() {
        CavityBlock cavity = (CavityBlock) paBlockHandler.getFromPalette(5);

        paBlockHandler.connectToExistingBlock(cavity, callBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 1, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(cavity.getMainConnector().isConnected());
        assertEquals(callBlock, cavity.getMainConnector().getConnectedBlock());
        assertEquals(cavity, callBlock.getSubConnectorAt(0).getConnectedBlock());
    }

    @Test
    void multipleBlocks_toCall() {
        FunctionalBlock functional1 = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock functional2 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        connectionHandler.connect(functional2, functional1.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(functional1, callBlock.getSubConnectorAt(0));

        assertEquals(initialNbBlocks + 2, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(functional1.getMainConnector().isConnected());
        assertEquals(callBlock, functional1.getMainConnector().getConnectedBlock());
        assertEquals(functional1, callBlock.getSubConnectorAt(0).getConnectedBlock());
    }
}