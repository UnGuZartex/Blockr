package System.Logic.ProgramArea;

import GameWorldAPI.GameWorld.Result;

public interface Memento {

    Result execute();

    void undo();

    void redo();
}
