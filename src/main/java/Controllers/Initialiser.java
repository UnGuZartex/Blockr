package Controllers;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;
import GUI.Blocks.GUIConditionalBlock;
import GUI.Blocks.GUIFunctionalBlock;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.*;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.NotFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Initialiser {

    private GameWorld gameWorld;
    private HashMap<GUIBlock, Block> defaultBlocks = new HashMap<>();
    private List<Block> systemPaletteBlocks = new ArrayList<>();
    private List<GUIBlock> GUIPaletteBlocks = new ArrayList<>();

    public Initialiser() throws NoSuchMethodException, IllegalAccessException, InstantiationException, MalformedURLException, InvocationTargetException, ClassNotFoundException {
        JarLoader loader = new JarLoader();
        GameWorldType gameWorldType = loader.load();
        initialiseDefaultBlocks();
        initialisePalettesAndGameWorld(gameWorldType);
    }

    public List<Block> getSystemPaletteBlocks() {
        return systemPaletteBlocks;
    }

    public List<GUIBlock> getGUIPaletteBlocks() {
        return GUIPaletteBlocks;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    private void initialiseDefaultBlocks() {
        defaultBlocks.put(new GUICavityBlock("If", 0, 0), new IfBlock(new CavityFunctionality(gameWorld)));
        defaultBlocks.put(new GUICavityBlock("While", 0, 0), new WhileBlock(new CavityFunctionality(gameWorld)));
        defaultBlocks.put(new GUICavityBlock("Not", 0, 0), new NotBlock(new NotFunctionality(gameWorld)));
    }

    private void initialisePalettesAndGameWorld(GameWorldType gameWorldType) {

        gameWorld = gameWorldType.createNewGameworld();

        for (Action action : gameWorldType.getAllActions()) {
            GUIPaletteBlocks.add(new GUIFunctionalBlock(action.getName(), 0, 0));
            systemPaletteBlocks.add(new FunctionalBlock(new ActionFunctionality(action, gameWorld)));
        }

        for (Predicate predicate : gameWorldType.getAllPredicates()) {
            GUIPaletteBlocks.add(new GUIConditionalBlock(predicate.getName(), 0, 0));
            systemPaletteBlocks.add(new StatementBlock(new PredicateFunctionality(predicate, gameWorld)));
        }

        for (Map.Entry<GUIBlock, Block> entry : defaultBlocks.entrySet()) {
            GUIPaletteBlocks.add(entry.getKey().clone());
            systemPaletteBlocks.add(entry.getValue().clone());
        }
    }
}
