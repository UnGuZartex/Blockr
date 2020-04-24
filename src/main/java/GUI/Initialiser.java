package GUI;

import Controllers.BlockLinkDatabase;
import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.HistoryController;
import Controllers.ControllerClasses.ProgramController;
import Controllers.JarLoader;
import GUI.Blocks.*;
import System.Logic.CommandHistory;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldAPI.GameWorldType.Predicate;
import Images.ImageLibrary;
import Images.ImageLoader;
import System.BlockStructure.Blocks.*;
import System.BlockStructure.Functionality.ActionFunctionality;
import System.BlockStructure.Functionality.CavityFunctionality;
import System.BlockStructure.Functionality.NotFunctionality;
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Initialiser {

    private GameWorld gameWorld;
    private HashMap<GUIBlock, Block> defaultBlocks = new HashMap<>();
    private List<Block> systemPaletteBlocks = new ArrayList<>();
    private List<GUIBlock> GUIPaletteBlocks = new ArrayList<>();


    public Initialiser() throws NoSuchMethodException, IllegalAccessException, InstantiationException, IOException, InvocationTargetException, ClassNotFoundException {
        JarLoader loader = new JarLoader();
        GameWorldType gameWorldType = loader.load();
        gameWorld = gameWorldType.createNewGameWorld();
        initialiseDefaultBlocks();
        initialisePalettesAndGameWorld(gameWorldType);
    }

    public BlockrCanvas createNewCanvas() throws IOException {

        CommandHistory history = new CommandHistory();
        HistoryController historyController = new HistoryController(history);
        ProgramArea programArea = new ProgramArea(gameWorld, history);
        PABlockHandler blockHandler = new PABlockHandler(systemPaletteBlocks, programArea);
        BlockLinkDatabase converter = new BlockLinkDatabase();
        ConnectionController connectionController = new ConnectionController(converter, blockHandler);
        ProgramController programController = new ProgramController(converter, blockHandler);
        BlockrCanvas canvas = new BlockrCanvas(initialiseImageLibrary(),
                programController,
                connectionController);
        canvas.setPanels(GUIPaletteBlocks, gameWorld, historyController);
        return canvas;
    }

    private void initialiseDefaultBlocks() {
        defaultBlocks.put(new GUICavityBlock("If", 0, 0), new IfBlock(new CavityFunctionality()));
        defaultBlocks.put(new GUICavityBlock("While", 0, 0), new WhileBlock(new CavityFunctionality()));
        defaultBlocks.put(new GUIOperatorBlock("Not", 0, 0), new NotBlock(new NotFunctionality()));
    }

    private void initialisePalettesAndGameWorld(GameWorldType gameWorldType) {

        for (Action action : gameWorldType.getAllActions()) {
            GUIPaletteBlocks.add(new GUIFunctionalBlock(action.getName(), 0, 0));
            systemPaletteBlocks.add(new FunctionalBlock(new ActionFunctionality(action)));
        }

        for (Predicate predicate : gameWorldType.getAllPredicates()) {
            GUIPaletteBlocks.add(new GUIConditionalBlock(predicate.getName(), 0, 0));
            systemPaletteBlocks.add(new StatementBlock(new PredicateFunctionality(predicate)));
        }

        for (Map.Entry<GUIBlock, Block> entry : defaultBlocks.entrySet()) {
            GUIPaletteBlocks.add(entry.getKey().clone());
            systemPaletteBlocks.add(entry.getValue().clone());
        }
    }

    private ImageLibrary initialiseImageLibrary() throws IOException {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadDirectoryImages("Blockr");
        imageLoader.loadDirectoryImages(System.getProperty("GameWorldJar"));
        return imageLoader.createImageLibrary();
    }
}
