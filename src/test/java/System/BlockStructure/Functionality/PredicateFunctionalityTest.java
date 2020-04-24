package System.BlockStructure.Functionality;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldAPI.GameWorldType.Predicate;
import GameWorldUtility.LevelInitializer;
import GameWorldUtility.Predicates.WallInFrontPredicate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PredicateFunctionalityTest {

    Predicate predicate;
    BlockFunctionality functionality;
    GameWorldType type;
    GameWorld gameWorld;

    @BeforeEach
    void setUp() {
        type = new LevelInitializer();
        gameWorld = type.createNewGameWorld();
        predicate = new WallInFrontPredicate();
        functionality = new PredicateFunctionality(predicate, gameWorld);
    }

    @AfterEach
    void tearDown() {
        type = null;
        gameWorld = null;
        predicate = null;
        functionality = null;
    }

    @Test
    void evaluate() {
        assertEquals(Result.SUCCESS, functionality.evaluate());
        assertTrue(functionality.getEvaluation());
    }

    @Test
    void copy() {
        BlockFunctionality func = functionality.copy();
        assertNotEquals(func, functionality);
        assertTrue(func instanceof PredicateFunctionality);
        assertEquals(func.getGameWorld(), functionality.getGameWorld());
    }
}