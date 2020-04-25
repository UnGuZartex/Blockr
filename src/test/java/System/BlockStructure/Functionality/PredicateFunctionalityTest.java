
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
        predicate = (WallInFrontPredicate) type.getAllPredicates().get(0);
        functionality = new PredicateFunctionality(predicate);
    }

    @AfterEach
    void tearDown() {
        type = null;
        gameWorld = null;
        predicate = null;
        functionality = null;
    }

    @Test
    void predicateFunctionality_invalidFunctionality() {
        assertThrows(IllegalArgumentException.class, () -> new PredicateFunctionality(null));
    }

    @Test
    void isValidPredicate() {
        assertTrue(PredicateFunctionality.isValidPredicate(type.getAllPredicates().get(0)));
        assertFalse(PredicateFunctionality.isValidPredicate(null));
    }

    @Test
    void evaluate() {
        assertEquals(Result.SUCCESS, functionality.evaluate(gameWorld));
        assertTrue(functionality.getEvaluation());
    }
}

