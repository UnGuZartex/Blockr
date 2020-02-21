# Use Cases
## Use Case 1: Add Program Block
### Precondition
There are still blocks in the Palette for the user to use.
### Success Guarantee (Postcondition)
The user is aware of the position of the block in the Program Area. List of blocks in the Program Area is updated to include the new one. The amount of blocks to be placed is updated.
### Main Success Scenario
1. The user moves the mouse cursor over a block in the Palette, then presses the left mouse key, then moves the mouse 
cursor to the Program Area, and then releases the left mouse key.

2. The system adds a new block of the same type to the Program Area at the position of the mouse.

3. The system updates the amount of blocks that can be used

4. The system adds a new block of the same type to the Palette.

### Extensions
1a. When the user releases the mouse key, one of the block’s 
connectors is near a compatible opposite connector of another block.
 1. The system adds a new block of the same type to the ProgramArea;
 the new block is inserted into an existing group of connected blocks at the matching connection point.

1b. When the user releases the mouse key outside of the Program Area
 1. No block is added to the Program Area

1c. When the user hasn't moved over a block in the Palette
 1. No action occurs

1d. When the user presses another key than the left mouse button
 1. No action considering adding occurs.

4a. When the maximum amount of blocks in the Program Area has been reached.
 1. The system doesn't add the block to the Palette.
 2. The system removes all other blocks from the Palette. 
 
## Use Case 2: Move Program Block
### Precondition
There is a block in the Program Area.
### Success Guarantee (Postcondition)
The user is aware of the new position of the block. The position of the block gets updated to the new position.
### Main Success Scenario
 1. The user moves the mouse cursor over a block in the Program Area, then presses the left mouse key. 
 The user then moves the mouse cursor inside the Program Area, and then releases the left mouse key.
 2. The system changes the position of the block from the previous position to the mouse position.
### Extensions
1a. When the user releases the mouse cursor outside the Program Area and Palette
1. The system doesn't change the block's position.

1b. When the user hasn't moved over a block in the Program Area
 1. No action occurs
 
1c. When the user presses another key than the left mouse button
 1. No action considering moving occurs.

1d. When the user presses the mouse key and the block is connected to other blocks
  1. The system disconnects the block on it's socket.
  2. The system system drags all blocks which are connected through the socket as one entity.
  3. The system sees all the blocks as one entity.
  
1e. When the user releases the mouse key, the socket of the block is near the plug of another block.
 1. The system connects the socket of the moving block with the plug of the other block.
 
  
## Use Case 3: Delete Program Block
### Precondition
There is a block in the Program Area.
### Success Guarantee (Postcondition)
The user is aware that the block is deleted. The block gets deleted from the Program Area. The amount of blocks to be placed is updated.
### Main Success Scenario
1. The user moves the mouse cursor over a block in the Program Area, then presses the left mouse key. 
 The user then moves the mouse cursor to the Palette, and then releases the left mouse key.
 2. The system deletes the block from the Program Area.
 
### Extensions
1a. When the user hasn't moved over a block in the Program Area
 1. No action occurs.
 
1b. When the user presses another key than the left mouse button
 1. No action considering deleting occurs.
 
1c. When the user releases the mouse cursor outside the Program Area and Palette
 1. No action occurs.
 
1d. When the user presses the mouse key and the block is connected to other blocks
 1. The system disconnects the block on it's socket.
 2. The system system drags all blocks which are connected through the socket as one entity.
 3. The system sees all the blocks as one entity.
 
2a. When the maximum amount of blocks was inside the Program Area
 1. The system deletes the block from the Program Area.
 2. The system adds back all possible blocks to the Palette.



## Use Case 4: Start Program Step
### Precondition
The application itself is fully loaded in.
### Success Guarantee (Postcondition)
The user is aware that a step of the program is executed. The Game World gets updated with the commands in following the step. 
### Main Success Scenario

1. The user presses F5.
2. The system identifies the program in the Program Area.
3. The system evaluates the first block and highlights it.
4. The system changes the state of the robot in the Game World accordingly to the executed block.  

### Extensions

1a. When the system is already busy executing a step in the program
1. The system ignores the second call from the user.

2a. When multiple sets of connected blocks are present in the Program Area
1. The system gives an error message telling that all blocks in the Program Area should be connected.
2. The system doesn't evaluate any set of blocks.

3a. When the execution of the program has already started
1. The system unhighlights the block which has been executed last.
2. The system executes the block which is connected to the plug of the last block which has been executed.
3. The system highlights the block which has been executed. 

3b. When the block to execute is a loop block
1. The system highlights the loop block.
2. The system evaluates the loop condition. 
3. The system determines the block to go to for the next iteration. 

3c. When the block to execute is contained in a loop block
1. The system highlights the loop block in which the block to execute is contained. 

3d. When the block which has been executed last is the end of a loop block
1. The system unhighlights this block. 
2. The system system chooses the loop block as next block to execute.

4a. When the the block doesn't affect the robot's state
1. The system doesn't change the robot's state in the Game World.

4b. When the robots position is changed to an invalid place in the Game World
1. The system shows an error message to the user.
2. The system resets the execution of the program.


## Use Case 5: Reset Program
### Precondition
The application itself is fully loaded in.
### Success Guarantee (Postcondition)
The user is aware the reset has occurred. The program gets set back to the initial state (this being the first step). The Game World gets reset to the initial state.
### Main Success Scenario

1. The user presses Escape.
2. The system places the robot on the starting position in the Game World.
3. The system unhighlights the highlighted block in the Program Area.

### Extensions

2a. When the robot is already in the starting position in the Game World 
1. The system doesn't change the Game World.

3a. When there is no highlighted block in the Program Area
1. The system keeps all blocks unhighlighted.

