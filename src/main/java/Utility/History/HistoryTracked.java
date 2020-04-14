package GameWorld.History;

import GameWorldAPI.History.Snapshot;

/**
 * An interface for objects of which a history should be tracked.
 */
public interface HistoryTracked {

    /**
     * Create a snapshot of this object.
     *
     * @return A new snapshot of this object, containing the state.
     */
    Snapshot createSnapshot();

    /**
     * Load the given snapshot.
     *
     * @param snapshot The snapshot to load.
     *
     * @effect The given snapshot should be loaded in this object
     *         and it's the state containing should be restored.
     */
    void loadSnapshot(Snapshot snapshot);

    /**
     * Create a backup of this object.
     *
     * @effect A snapshot should be created saved.
     */
    void backup();

    /**
     * Undo the last change to the object.
     *
     * @effect The last backup saved is restored to the object.
     */
    void undo();

    /**
     * Redo the last undo to this object.
     *
     * @effect The last snapshot state which is undone should be restored.
     */
    void redo();

    /**
     * Reset the object.
     *
     * @effect The object should be set to the initial state and all history
     *         should be cleared.
     */
    void reset();
}
