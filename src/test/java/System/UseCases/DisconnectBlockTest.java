package System.UseCases;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.Actions.MoveForwardAction;
import GameWorldUtility.Actions.TurnLeftAction;
import GameWorldUtility.Actions.TurnRightAction;
import GameWorldUtility.LevelInitializer;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.NotFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.ProgramArea.ConnectionHandler;
import System.Logic.ProgramArea.PABlockHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DisconnectBlockTest {

    ConnectionHandler connectionHandler;
    PABlockHandler paBlockHandler;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
    FunctionalBlock functionalBlockUp, functionalBlockUnder, functionalBlockIn;
    CavityBlock cavityBlock, cavityBlockIn, cavityBlockUnder;
    OperationalBlock operationalBlock1, operationalBlock2;
    StatementBlock statementBlock1, statementBlock2;
    int initialNbBlocks;

    @BeforeEach
    void setUp() {
        connectionHandler = new ConnectionHandler();

        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();

        moveForward = new FunctionalBlock(new ActionFunctionality((MoveForwardAction) type.getAllActions().get(0), gameWorld));
        turnLeft = new FunctionalBlock(new ActionFunctionality((TurnLeftAction) type.getAllActions().get(1), gameWorld));
        turnRight = new FunctionalBlock(new ActionFunctionality((TurnRightAction) type.getAllActions().get(2), gameWorld));
        wallInFront = new StatementBlock(new PredicateFunctionality((WallInFrontPredicate) type.getAllPredicates().get(0), gameWorld));
        notBlock = new NotBlock(new NotFunctionality(gameWorld));
        whileBlock = new WhileBlock(new CavityFunctionality(gameWorld));
        ifBlock = new IfBlock(new CavityFunctionality(gameWorld));

        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock)));

        functionalBlockUp = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        functionalBlockUnder = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        functionalBlockIn = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        cavityBlock = (CavityBlock) paBlockHandler.getFromPalette(5);
        cavityBlockIn = (CavityBlock) paBlockHandler.getFromPalette(6);
        cavityBlockUnder = (CavityBlock) paBlockHandler.getFromPalette(5);
        operationalBlock1 = (OperationalBlock) paBlockHandler.getFromPalette(4);
        operationalBlock2 = (OperationalBlock) paBlockHandler.getFromPalette(4);
        statementBlock1 = (StatementBlock) paBlockHandler.getFromPalette(3);
        statementBlock2 = (StatementBlock) paBlockHandler.getFromPalette(3);

//                functionalBlockUp
//                cavityBlock : operationalBlock1 : operationalBlock2 : statementBlock1
//                | cavityBlockIn : statementBlock2
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
        paBlockHandler.connectToExistingBlock(statementBlock1, operationalBlock2.getSubConnectorAt(0));

        paBlockHandler.connectToExistingBlock(cavityBlockIn, cavityBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(statementBlock2, cavityBlockIn.getConditionalSubConnector());

        paBlockHandler.connectToExistingBlock(functionalBlockIn, cavityBlockIn.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(functionalBlockUnder, cavityBlockIn.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(cavityBlockUnder, functionalBlockUnder.getSubConnectorAt(0));

        initialNbBlocks = 10;
        assertEquals(initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }

    @AfterEach
    void tearDown() {
        assertEquals(initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
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
        statementBlock1 = null;
        statementBlock2 = null;
    }

    @Test
    void underFunctional() {
        paBlockHandler.disconnectInPA(cavityBlock);

        assertFalse(cavityBlock.getMainConnector().isConnected());
        assertFalse(functionalBlockUp.getSubConnectorAt(0).isConnected());
    }

    @Test
    void functionalInCavity() {
        paBlockHandler.disconnectInPA(functionalBlockIn);

        assertFalse(functionalBlockIn.getMainConnector().isConnected());
        assertFalse(cavityBlockIn.getCavitySubConnector().isConnected());
    }

    @Test
    void cavityInCavity() {
        paBlockHandler.disconnectInPA(cavityBlockIn);

        assertFalse(cavityBlockIn.getMainConnector().isConnected());
        assertFalse(cavityBlock.getSubConnectorAt(0).isConnected());
    }

    @Test
    void functionalUnderCavity() {
        paBlockHandler.disconnectInPA(functionalBlockUnder);

        assertFalse(functionalBlockUnder.getMainConnector().isConnected());
        assertFalse(cavityBlockIn.getSubConnectorAt(0).isConnected());
    }

    @Test
    void cavityUnderFunctional() {
        paBlockHandler.disconnectInPA(cavityBlockUnder);

        assertFalse(cavityBlockUnder.getMainConnector().isConnected());
        assertFalse(functionalBlockUnder.getSubConnectorAt(0).isConnected());
    }

    @Test
    void operationalFromCavity() {
        paBlockHandler.disconnectInPA(operationalBlock1);

        assertFalse(operationalBlock1.getMainConnector().isConnected());
        assertFalse(cavityBlock.getConditionalSubConnector().isConnected());
    }

    @Test
    void operationalFromOperational() {
        paBlockHandler.disconnectInPA(operationalBlock2);

        assertFalse(operationalBlock2.getMainConnector().isConnected());
        assertFalse(operationalBlock1.getSubConnectorAt(0).isConnected());
    }

    @Test
    void statementFromOperational() {
        paBlockHandler.disconnectInPA(statementBlock1);

        assertFalse(statementBlock1.getMainConnector().isConnected());
        assertFalse(operationalBlock2.getSubConnectorAt(0).isConnected());
    }

    @Test
    void statementFromCavity() {
        paBlockHandler.disconnectInPA(statementBlock2);

        assertFalse(statementBlock2.getMainConnector().isConnected());
        assertFalse(cavityBlockIn.getConditionalSubConnector().isConnected());
    }
}