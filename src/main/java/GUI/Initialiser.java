package GUI;

import Controllers.BlockLinkDatabase;
import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.ProgramController;
import Controllers.JarLoader;
import GUI.BlockrCanvas;
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
import System.Logic.ProgramArea.PABlockHandler;

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

    public BlockrCanvas createNewCanvas() {
        PABlockHandler blockHandler = new PABlockHandler(systemPaletteBlocks);
        BlockLinkDatabase converter = new BlockLinkDatabase();
        ConnectionController connectionController = new ConnectionController(converter, blockHandler);
        ProgramController programController = new ProgramController(converter, blockHandler, gameWorld);
        return new BlockrCanvas(GUIPaletteBlocks,
                programController,
                connectionController);
    }

    private void initialiseDefaultBlocks() {
        defaultBlocks.put(new GUICavityBlock("If", 0, 0), new IfBlock(new CavityFunctionality(gameWorld)));
        defaultBlocks.put(new GUICavityBlock("While", 0, 0), new WhileBlock(new CavityFunctionality(gameWorld)));
        defaultBlocks.put(new GUICavityBlock("Not", 0, 0), new NotBlock(new NotFunctionality(gameWorld)));
    }

    private void initialisePalettesAndGameWorld(GameWorldType gameWorldType) {

        gameWorld = gameWorldType.createNewGameWorld();

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
