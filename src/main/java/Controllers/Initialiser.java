package Controllers;

import GUI.Blocks.GUIBlock;
import GUI.Blocks.GUICavityBlock;
import GUI.Blocks.GUIConditionalBlock;
import GUI.Blocks.GUIFunctionalityBlock;
import GameWorldAPI.GameWorldType.*;
import System.BlockStructure.Blocks.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Initialiser {

    private HashMap<GUIBlock, Block> defaultBlocks = new HashMap<>() {{
        put(new GUICavityBlock("If", 0, 0), new IfBlock());
        put(new GUICavityBlock("While", 0, 0), new WhileBlock());
        put(new GUICavityBlock("Not", 0, 0), new NotBlock());
    }};

    public void initialisePalettes(GameWorldType gameWorldType) {

        List<Block> systemPaletteBlocks = new ArrayList<>();
        List<GUIBlock> GUIPaletteBlocks = new ArrayList<>();
        List<Action> actions = gameWorldType.getAllActions();
        List<Predicate> predicates = gameWorldType.getAllPredicates();

        for (Action action : actions) {
            GUIPaletteBlocks.add(new GUIFunctionalityBlock(action.getName(), 0, 0));
            systemPaletteBlocks.add(new FunctionalBlock(null)); // TODO null
        }

        for (Predicate predicate : predicates) {
            GUIPaletteBlocks.add(new GUIConditionalBlock(predicate.getName(), 0, 0));
            systemPaletteBlocks.add(new StatementBlock(null)); // TODO null
        }

        for (Map.Entry<GUIBlock, Block> entry : defaultBlocks.entrySet()) {
            GUIPaletteBlocks.add(entry.getKey().clone());
            systemPaletteBlocks.add(entry.getValue().clone());
        }
    }
}
