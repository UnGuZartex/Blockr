package Controllers;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;
import GUI.Blocks.GUIConditionalBlock;
import GUI.Blocks.GUIFunctionalityBlock;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.*;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.NotFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Initialiser {

    private final List<Action> actions;
    private final List<Predicate> predicates;
    private final GameWorld currentGameWorld;
    private HashMap<GUIBlock, Block> defaultBlocks = new HashMap<>();

    private List<Block> systemPaletteBlocks = new ArrayList<>();

    private List<GUIBlock> GUIPaletteBlocks = new ArrayList<>();

    public Initialiser(GameWorldType gameWorldType, GameWorld gameWorld) {
        actions = gameWorldType.getAllActions();
        predicates = gameWorldType.getAllPredicates();
        currentGameWorld = gameWorld;
        defaultBlocks.put(new GUICavityBlock("If", 0, 0), new IfBlock(new CavityFunctionality(currentGameWorld)));
        defaultBlocks.put(new GUICavityBlock("While", 0, 0), new WhileBlock(new CavityFunctionality(currentGameWorld)));
        defaultBlocks.put(new GUICavityBlock("Not", 0, 0), new NotBlock(new NotFunctionality(currentGameWorld)));
        initialisePalettes();
    }


    public List<Block> getSystemPaletteBlocks() {
        return systemPaletteBlocks;
    }

    public List<GUIBlock> getGUIPaletteBlocks() {
        return GUIPaletteBlocks;
    }

    public void initialisePalettes() {

        for (Action action : actions) {
            GUIPaletteBlocks.add(new GUIFunctionalityBlock(action.getName(), 0, 0));
            systemPaletteBlocks.add(new FunctionalBlock(new ActionFunctionality(action, currentGameWorld)));
        }

        for (Predicate predicate : predicates) {
            GUIPaletteBlocks.add(new GUIConditionalBlock(predicate.getName(), 0, 0));
            systemPaletteBlocks.add(new StatementBlock(new PredicateFunctionality(predicate, currentGameWorld)));
        }

        for (Map.Entry<GUIBlock, Block> entry : defaultBlocks.entrySet()) {
            GUIPaletteBlocks.add(entry.getKey().clone());
            systemPaletteBlocks.add(entry.getValue().clone());
        }
    }
}
