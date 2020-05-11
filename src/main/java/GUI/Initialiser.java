package GUI;

import Controllers.IGUI_System_BlockLink;
import Controllers.ControllerClasses.ConnectionController;
import Controllers.ControllerClasses.HistoryController;
import Controllers.ControllerClasses.BlockHandlerController;
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
import System.BlockStructure.Functionality.PredicateFunctionality;
import System.Logic.ProgramArea.PABlockHandler;
import System.Logic.ProgramArea.ProgramArea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to initialise the project.
 */
public class Initialiser {

    /**
     * Variable used to hold the initialised game world.
     */
    private GameWorld gameWorld;

    /**
     * Variable used to hold the initialised system palette blocks.
     */
    private List<Block> systemPaletteBlocks = new ArrayList<>();

    /**
     * Variable used to hold the initialized gui palette blocks.
     */
    private List<GUIBlock> GUIPaletteBlocks = new ArrayList<>();

    /**
     * Variable referring to the default blocks that are always present in the system.
     */
    private HashMap<GUIBlock, Block> defaultBlocks = new HashMap<>() {{
        put(new GUICavityBlock("If", 0, 0), new IfBlock());
        put(new GUICavityBlock("While", 0, 0), new WhileBlock());
        put(new GUIOperatorBlock("Not", 0, 0), new NotBlock());
        put(new GUICavityBlock("Def",0,0), new ProcedureBlock());
    }};

    /**
     * Create a new initialiser.
     *
     * @post The current game world is set to the loaded game world.
     *
     * @effect The default blocks are initialised.
     * @effect The system/gui palettes are initialised.
     *
     * @throws Exception
     *         When there was an exception thrown during the
     *         initialisation of some components.
     */
    public Initialiser() throws Exception {
        JarLoader loader = new JarLoader();
        GameWorldType gameWorldType = loader.load();
        gameWorld = gameWorldType.createNewGameWorld();
        initialisePalettes(gameWorldType);
    }

    /**
     * Create a new Blockr canvas.
     *
     * @effect The canvas panels are set based on the initialised data.
     *
     * @return The Blockr canvas.
     *
     * @throws IOException
     *         When an image could not be loaded while
     *         creating the image library.
     */
    public BlockrCanvas createNewCanvas() throws IOException {

        CommandHistory history = new CommandHistory();
        ProgramArea programArea = new ProgramArea(gameWorld, history);
        HistoryController historyController = new HistoryController(history, programArea);
        PABlockHandler blockHandler = new PABlockHandler(systemPaletteBlocks, programArea);
        IGUI_System_BlockLink converter = new IGUI_System_BlockLink();
        ConnectionController connectionController = new ConnectionController(converter, blockHandler);
        BlockHandlerController blockHandlerController = new BlockHandlerController(converter, blockHandler);
        BlockrCanvas canvas = new BlockrCanvas(initialiseImageLibrary(),
                blockHandlerController,
                connectionController);
        canvas.setPanels(GUIPaletteBlocks, gameWorld, historyController, blockHandler);
        return canvas;
    }

    /**
     * The system/gui palettes are initialised with a given
     * game world type.
     *
     * @param gameWorldType The given game world type.
     *
     * @post The gui palette blocks list is filled with the default
     *       gui blocks together with the additional blocks generated from
     *       the available predicates and actions in the game world.
     * @post The system palette blocks list is filled with the default
     *       system blocks together with the additional blocks generated from
     *       the available predicates and actions in the game world.
     */
    private void initialisePalettes(GameWorldType gameWorldType) {

        for (Action action : gameWorldType.getAllActions()) {
            GUIPaletteBlocks.add(new GUIFunctionalBlock(action.getName(), 0, 0));
            systemPaletteBlocks.add(new FunctionalBlock(new ActionFunctionality(action)));
        }

        for (Predicate predicate : gameWorldType.getAllPredicates()) {
            GUIPaletteBlocks.add(new GUIConditionalBlock(predicate.getName(), 0, 0));
            systemPaletteBlocks.add(new PredicateBlock(new PredicateFunctionality(predicate)));
        }

        for (Map.Entry<GUIBlock, Block> entry : defaultBlocks.entrySet()) {
            GUIPaletteBlocks.add(entry.getKey().clone());
            systemPaletteBlocks.add(entry.getValue().clone());
        }
    }

    /**
     * Initialise the image library.
     *
     * @return The newly created image library.
     *
     * @throws IOException
     *         When an image could not be loaded.
     */
    private ImageLibrary initialiseImageLibrary() throws IOException {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.loadDirectoryImages("Blockr");
        imageLoader.loadDirectoryImages(System.getProperty("GameWorldJar"));
        return imageLoader.createImageLibrary();
    }
}
