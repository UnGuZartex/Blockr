# Creating a Game World API Implementation
## GameWorldType

Every implementation needs to define a main class that implements the GameWorldType-Interface, that allows to create a
new GameWorld, load a GameWorld from a file (or a default one) and return the actions and predicates usable in the GameWorld.
The actions and predicates should be returned as a List of Actions or a List of Predicates.

## GameWorld

For a correct implementation, a GameWorld is needed. This GameWorld needs to implement following functions:

- executeAction(Action)
  - Execute the given action on this GameWorld, returning a Result(defined by the Result Enum in the API) whether or not the GameWorld is in a valid state.
- evaluatePredicate(Predicate)
  - Evaluate the given predicate for this GameWorld, returning the result of the evaluation.
- createSnapshot()
  - Create a Snapshot of the current state of the GameWorld
- loadSnapshot(Snapshot)
  - Load a previously created Snapshot to set the state of the GameWorld
- paint(Graphics, ImageLibrary)
  - Paint the GameWorld using a ImageLibrary and Graphics, if not filled in a blank GameWorld will be drawn.

## Action

When Actions are required on the GameWorld this interface needs to be implemented. An Action is defined by its name and an execute function.
The execute function is responsible for changes on the GameWorld.

## Predicate

When knowledge of the GameWorld is required a Predicate can be used. A Predicate is defined by its name and an evaluate function.
This evaluates the GameWorld, to know whether or not it is in a certain state.
The evaluate function returns a boolean.

## Snapshot

A GameWorld needs to be able to create and load snapshots, therefore snapshots need to be defined in any implementation.
For reasons such as user info, a Snapshot needs to atleast have a name and a date of creation. A snapshot keeps track
of the state at moment of creation. Allowing to revert back to this state.

# Creating a Game World API Client

!Every client is responsible for loading in its implementation of the Game World API!

When loading in the implementation it should suffice to load in the class that implements GameWorldType.
This allows for the creation of further classes.

## Creating a GameWorld

To create a GameWorld the Client has 2 options. It can load the GameWorld from a file using GameWorldType.loadFromFile(File).
 
 It is also possible to create a new standard GameWorld using: GameWorldType.createNewGameWorld().

## Accessing Actions and Predicates

Any Client for the API needs access to the actions and/or predicates to be useful.
If this is done, accessing actions is as simple as: GameWorldType.getAllActions(). For predicates this becomes: getAllPredicates().

NOTE: Only access actions and predicates this way, other ways/own implementations of actions in the client are considered illegal.

## Executing and evaluating Actions/Predicates

Now that a GameWorld and all actions/predicates are loaded in, actions can be executed and predicates evaluated. By what means this is done is up to the Client implementation.

Two options are given:
- (RECOMMENDED) GameWorld.executeAction(Action): this is used if you wanna handle the Result from the GameWorld 
- Action.execute(GameWorld): this can be done if you are not interested in the Result, yet wanna execute the Action.

## Backing up and reloading GameWorld

To undo/redo certain actions, it is possible to create Snapshots and load Snapshots. The snapshot to load should be a snapshot of the GameWorld this is passed to.
Otherwise the program is in an illegal state and will stop working.

