package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldUtility.Actions.TurnLeftAction;
import GameWorldUtility.LevelInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionFunctionalityTest {

    Action action;
    BlockFunctionality functionality;
    GameWorldType type;
    GameWorld gameWorld;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        action = (TurnLeftAction) type.getAllActions().get(1);
        functionality = new ActionFunctionality(action);
    }

    @AfterEach
    void tearDown() {
        type = null;
        gameWorld = null;
        action = null;
        functionality = null;
    }

    @Test
    void actionFunctionality_invalidAction() {
        assertThrows(IllegalArgumentException.class, () -> new ActionFunctionality(null));
    }

    @Test
    void isValidAction() {
        assertTrue(ActionFunctionality.isValidAction(type.getAllActions().get(0)));
        assertTrue(ActionFunctionality.isValidAction(type.getAllActions().get(1)));
        assertTrue(ActionFunctionality.isValidAction(type.getAllActions().get(2)));
        assertFalse(ActionFunctionality.isValidAction(null));
    }

    @Test
    void evaluate() {
        assertEquals(Result.SUCCESS, functionality.evaluate(gameWorld));
    }
}