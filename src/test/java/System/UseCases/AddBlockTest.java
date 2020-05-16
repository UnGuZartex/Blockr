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
import System.Logic.ProgramArea.ConnectionHandler;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class AddBlockTest {

    ConnectionHandler connectionHandler;
    PABlockHandler paBlockHandler;
    GameWorldType type;
    GameWorld gameWorld;
    Block moveForward, turnLeft, turnRight, wallInFront, notBlock, whileBlock, ifBlock, procedure;

    @BeforeEach
    void setUp() {
        connectionHandler = new ConnectionHandler();

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
        procedure = null;
    }

    @Test
    void singleBlocks() {
        CavityBlock cavity = (WhileBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock statement = (PredicateBlock) paBlockHandler.getFromPalette(3);
        OperationalBlock operational = (NotBlock) paBlockHandler.getFromPalette(4);
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(cavity);
        assertEquals(1, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(statement);
        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(operational);
        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(functional);
        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(procedure);
        assertEquals(5, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void connectedBlocks() {
        CavityBlock cavity = (CavityBlock) paBlockHandler.getFromPalette(5);
        PredicateBlock statement = (PredicateBlock) paBlockHandler.getFromPalette(3);
        OperationalBlock operational = (NotBlock) paBlockHandler.getFromPalette(4);
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);

        connectionHandler.connect(functional, procedure.getSubConnectorAt(0));
        connectionHandler.connect(cavity, functional.getSubConnectorAt(0));
        connectionHandler.connect(statement, operational.getSubConnectorAt(0));

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(procedure);
        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(operational);
        assertEquals(5, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void sameBlock() {
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(functional);
        assertEquals(1, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(functional);
        assertEquals(1, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void tooMuchBlocks() {
        int MAX_BLOCKS = 10;
        for (int i = 0; i < MAX_BLOCKS; i++) {
            assertEquals(i, paBlockHandler.getPA().getAllBlocksCount());
            paBlockHandler.addToPA(paBlockHandler.getFromPalette(i % 7)); // modulo for variety of blocks
            assertEquals(i + 1, paBlockHandler.getPA().getAllBlocksCount());
            assertTrue(paBlockHandler.hasProperNbBlocks());
        }

        assertEquals(MAX_BLOCKS, paBlockHandler.getPA().getAllBlocksCount());
        assertThrows(IllegalStateException.class, () -> paBlockHandler.addToPA(paBlockHandler.getFromPalette(0)));
    }

    @Test
    void addHighest_functionalOnly() {
        FunctionalBlock functional1 = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock functional2 = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock functional3 = (FunctionalBlock) paBlockHandler.getFromPalette(2);

        connectionHandler.connect(functional2, functional1.getSubConnectorAt(0));
        connectionHandler.connect(functional3, functional2.getSubConnectorAt(0));

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(functional3);
        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void addHighest_conditionalOnly() {
        OperationalBlock operationalBlock = (OperationalBlock) paBlockHandler.getFromPalette(4);
        PredicateBlock predicateBlock = (PredicateBlock) paBlockHandler.getFromPalette(3);

        connectionHandler.connect(predicateBlock, operationalBlock.getSubConnectorAt(0));

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(predicateBlock);
        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void addHighest_toCavity_fromConditional() {
        CavityBlock cavityBlock = (CavityBlock) paBlockHandler.getFromPalette(5);
        OperationalBlock operationalBlock = (OperationalBlock) paBlockHandler.getFromPalette(4);
        PredicateBlock predicateBlock = (PredicateBlock) paBlockHandler.getFromPalette(3);

        connectionHandler.connect(operationalBlock, cavityBlock.getConditionalSubConnector());
        connectionHandler.connect(predicateBlock, operationalBlock.getSubConnectorAt(0));

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(predicateBlock);
        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void addHighest_toCavity_fromFunctional_inCavity() {
        CavityBlock cavityBlock = (CavityBlock) paBlockHandler.getFromPalette(6);
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        connectionHandler.connect(functional, cavityBlock.getCavitySubConnector());

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(functional);
        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void addHighest_toCavity_fromFunctional_under() {
        CavityBlock cavityBlock = (CavityBlock) paBlockHandler.getFromPalette(5);
        FunctionalBlock functional = (FunctionalBlock) paBlockHandler.getFromPalette(0);

        connectionHandler.connect(functional, cavityBlock.getSubConnectorAt(0));

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(functional);
        assertEquals(2, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void addHighest_overCavity() {
        CavityBlock cavityBlock = (CavityBlock) paBlockHandler.getFromPalette(6);
        FunctionalBlock functional1 = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock functional2 = (FunctionalBlock) paBlockHandler.getFromPalette(1);

        connectionHandler.connect(cavityBlock, functional1.getSubConnectorAt(0));
        connectionHandler.connect(functional2, cavityBlock.getSubConnectorAt(0));

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(functional2);
        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void addHighest_procedure() {
        ProcedureBlock procedure = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        FunctionalBlock functional1 = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock functional2 = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock functional3 = (FunctionalBlock) paBlockHandler.getFromPalette(2);

        connectionHandler.connect(functional1, procedure.getSubConnectorAt(0));
        connectionHandler.connect(functional2, functional1.getSubConnectorAt(0));
        connectionHandler.connect(functional3, functional2.getSubConnectorAt(0));

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(functional3);
        assertEquals(4, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void addHighest_fromMiddle() {
        FunctionalBlock functional1 = (FunctionalBlock) paBlockHandler.getFromPalette(0);
        FunctionalBlock functional2 = (FunctionalBlock) paBlockHandler.getFromPalette(1);
        FunctionalBlock functional3 = (FunctionalBlock) paBlockHandler.getFromPalette(2);

        connectionHandler.connect(functional2, functional1.getSubConnectorAt(0));
        connectionHandler.connect(functional3, functional2.getSubConnectorAt(0));

        assertEquals(0, paBlockHandler.getPA().getAllBlocksCount());
        paBlockHandler.addToPA(functional2);
        assertEquals(3, paBlockHandler.getPA().getAllBlocksCount());
    }

    @Test
    void getProcedureFromPalette() {
        assertThrows(IndexOutOfBoundsException.class, () -> paBlockHandler.getFromPalette(8));
        ProcedureBlock procedure1 = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        assertThrows(IndexOutOfBoundsException.class, () -> paBlockHandler.getFromPalette(8));
        paBlockHandler.addToPA(procedure1);
        ProcedureCall call11 = (ProcedureCall) paBlockHandler.getFromPalette(8);
        ProcedureCall call12 = (ProcedureCall) paBlockHandler.getFromPalette(8);
        assertEquals(procedure1, call11.getProcedure());
        assertEquals(procedure1, call12.getProcedure());
        paBlockHandler.addToPA(call11);
        paBlockHandler.addToPA(call12);

        assertThrows(IndexOutOfBoundsException.class, () -> paBlockHandler.getFromPalette(9));
        ProcedureBlock procedure2 = (ProcedureBlock) paBlockHandler.getFromPalette(7);
        assertThrows(IndexOutOfBoundsException.class, () -> paBlockHandler.getFromPalette(9));
        paBlockHandler.addToPA(procedure2);
        ProcedureCall call2 = (ProcedureCall) paBlockHandler.getFromPalette(9);
        assertEquals(procedure2, call2.getProcedure());
        paBlockHandler.addToPA(call2);

        assertEquals(5, paBlockHandler.getPA().getAllBlocksCount());
    }
}