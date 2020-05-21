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
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.CommandHistory;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteBlockTest {

    PABlockHandler paBlockHandler;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock;
    FunctionalBlock functionalBlockUp, functionalBlockUnder, functionalBlockIn;
    CavityBlock cavityBlock, cavityBlockIn, cavityBlockUnder;
    OperationalBlock operationalBlock1, operationalBlock2;
    PredicateBlock predicateBlock1, predicateBlock2;
    ProcedureBlock procedure;
    int initialNbBlocks;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();

        moveForward = new FunctionalBlock(new ActionFunctionality((MoveForwardAction) type.getAllActions().get(0)));
        turnLeft = new FunctionalBlock(new ActionFunctionality((TurnLeftAction) type.getAllActions().get(1)));
        turnRight = new FunctionalBlock(new ActionFunctionality((TurnRightAction) type.getAllActions().get(2)));
        wallInFront = new PredicateBlock(new PredicateFunctionality((WallInFrontPredicate) type.getAllPredicates().get(0)));
        notBlock = new NotBlock();
        whileBlock = new WhileBlock();
        ifBlock = new IfBlock();
        procedure = new ProcedureBlock();

        paBlockHandler = new PABlockHandler(new ArrayList<>(Arrays.asList(moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock, procedure)), new ProgramArea(gameWorld, new CommandHistory()));


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

        paBlockHandler.addToPA(paBlockHandler.getFromPalette(0)); // Dummy programs
        paBlockHandler.addToPA(paBlockHandler.getFromPalette(4));

        initialNbBlocks = 2;
        assertEquals(initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }

    @AfterEach
    void tearDown() {
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
    }

    @Test
    void removeFullProgram() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        assertEquals(4 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());

        paBlockHandler.deleteProgram(whileBlock);

        assertEquals(initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void removeTopFromCavity() {
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight1 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock turnRight2 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock turnRight3 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight1, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(turnRight2, turnRight1.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(turnRight3, turnRight2.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, turnRight3.getSubConnectorAt(0));

        assertEquals(6 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());

        paBlockHandler.deleteProgram(turnRight1);

        assertEquals(2 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void removeFromInsideCavity() {
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight1 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock turnRight2 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock turnRight3 = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(ifBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight1, ifBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(turnRight2, turnRight1.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(turnRight3, turnRight2.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, turnRight3.getSubConnectorAt(0));

        assertEquals(6 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());

        paBlockHandler.deleteProgram(turnRight3);

        assertEquals(4 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void removeCavity() {
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        PredicateBlock wallInFront1 = (PredicateBlock) paBlockHandler.getFromPalette(3);
        PredicateBlock wallInFront2 = (PredicateBlock) paBlockHandler.getFromPalette(3);
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        IfBlock ifBlock = (IfBlock) paBlockHandler.getFromPalette(6);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront1, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(ifBlock, turnRight.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(wallInFront2, ifBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnLeft, ifBlock.getCavitySubConnector());

        assertEquals(6 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());

        paBlockHandler.deleteProgram(ifBlock);

        assertEquals(3 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void removeFullConditionalFromCavity() {
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        NotBlock notBlock = (NotBlock) paBlockHandler.getFromPalette(4);
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);

        paBlockHandler.addToPA(notBlock);
        paBlockHandler.connectToExistingBlock(notBlock, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(wallInFront, notBlock.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(turnLeft, whileBlock.getSubConnectorAt(0));

        assertEquals(4 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());

        paBlockHandler.deleteProgram(notBlock);

        assertEquals(2 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void removePartOfConditional() {
        FunctionalBlock turnLeft = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        NotBlock notBlock = (NotBlock) paBlockHandler.getFromPalette(4);
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);

        paBlockHandler.addToPA(notBlock);
        paBlockHandler.connectToExistingBlock(notBlock, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(wallInFront, notBlock.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(turnLeft, whileBlock.getSubConnectorAt(0));

        assertEquals(4 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());

        paBlockHandler.deleteProgram(wallInFront);

        assertEquals(3 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }


    @Test
    void removeProcedure_noCall() {
        WhileBlock whileBlock = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock wallInFront = (PredicateBlock) paBlockHandler.getFromPalette(3);
        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        Block block = paBlockHandler.getFromPalette(0);

        paBlockHandler.addToPA(whileBlock);
        paBlockHandler.connectToExistingBlock(wallInFront, whileBlock.getConditionalSubConnector());
        paBlockHandler.connectToExistingBlock(turnRight, whileBlock.getCavitySubConnector());
        paBlockHandler.connectToExistingBlock(moveForward, whileBlock.getSubConnectorAt(0));

        paBlockHandler.addToPA(procedure);
        paBlockHandler.connectToExistingBlock(block, procedure.getSubConnectorAt(0));

        assertEquals(6 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());

        paBlockHandler.deleteProgram(procedure);

        assertEquals(4 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }


    @Test
    void removeProcedure_call() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        paBlockHandler.addToPA(procedure);
        ProcedureCall call1 = (ProcedureCall) paBlockHandler.getFromPalette(8);
        ProcedureCall call2 = (ProcedureCall) paBlockHandler.getFromPalette(8);
        ProcedureCall call3 = (ProcedureCall) paBlockHandler.getFromPalette(8);
        paBlockHandler.connectToExistingBlock(call2, call1.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(call3, call2.getSubConnectorAt(0));

        FunctionalBlock turnRight = (FunctionalBlock) paBlockHandler.getFromPalette(2);
        FunctionalBlock moveForward = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        paBlockHandler.connectToExistingBlock(turnRight, procedure.getSubConnectorAt(0));
        paBlockHandler.connectToExistingBlock(moveForward, call3.getSubConnectorAt(0));

        assertEquals(6 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
        assertTrue(paBlockHandler.hasProperNbBlocks());

        paBlockHandler.deleteProgram(procedure);

        assertEquals(1 + initialNbBlocks, paBlockHandler.getPA().getAllBlocksCount());
    }
}