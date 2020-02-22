# Use Cases


## Use Case 1: Add Program Block 

### Precondition
There are still blocks in the Palette for the user to use.

### Success Guarantee (Postcondition)
The user is aware of the position of the block in the Program Area. List of blocks in the Program Area is updated to include the new block. The number of blocks which can be placed is updated.

### Main Success Scenario
1. The user moves the mouse cursor over a block in the Palette, then presses the left mouse key, then moves the mouse cursor to the Program Area with the block visually following the cursor, and then releases the left mouse key.
2. The system adds a new block of the same type to the Program Area at the position of the mouse.
3. The system decrements the amount of blocks which still can be used.
4. The system adds a new block of the same type to the Palette.

### Extensions
1a. When the user releases the mouse key, one of the block's connectors is near a compatible opposite connector of another block
 1. The system adds a new block of the same type to the Program Area; the new block is inserted into an existing group of connected blocks at the matching connection point.
         
1b. When the user releases the mouse key outside of the Program Area
 1. No further action occurs, no block is added to the Program Area.

1c. When the mouse isn't on a block in the Palette at the moment the user presses the left mouse key
 1. No further action occurs.
 
1d. When the user presses another key than the left mouse button
 1. No further action considering adding occurs.

4a. When there can't be placed any more blocks in the Program Area
 1. The system doesn't add the block back to the Palette.
 2. The system removes all other blocks from the Palette. 


## Use Case 2: Move Program Block

### Precondition
There is at least one block present in the Program Area.

### Success Guarantee (Postcondition)
The user is aware of the new position of the block. The position of the block gets updated to the new position.

### Main Success Scenario
 1. The user moves the mouse cursor over a block in the Program Area, then presses the left mouse key. The user then moves the mouse cursor inside the Program Area with the block visually following the cursor, and then releases the left mouse key.
 2. The system changes the position of the block from the previous position to the mouse position.
 
### Extensions
1a. When the user releases the mouse key, one of the block's connectors is near a compatible opposite connector of another block
 1. The system adds a new block of the same type to the Program Area; the new block is inserted into an existing group of connected blocks at the matching connection point.
         
1b. When the user releases the left mouse key outside the Program Area and Palette
 1. The system doesn't change the block's position.

1c. When the user releases the left mouse key inside the Palette.
 1. The system executes <ins>Delete Program Block</ins>.
 
1d. When the user presses another key than the left mouse key
 1. No action considering moving occurs.

1e. When the user moves the cursor over a block which is connected to another through the socket and clicks the left mouse key
 1. The system disconnects the socket of the clicked block from the plug of the block above.
 2. The system keeps all other connections.
 3. The system moves the group of blocks as one entity. 
 
1f. When the user moves the cursor over a block which is connected through other blocks but not in on the top socket and clicks the left mouse key
 1. The system moves the group of blocks as one entity.

1g. When the user moves the cursor over a block which is in the cavity of the if/while block and clicks the left mouse key
 1. The system disconnects the the socket of the clicked block from the plug of the plug above (the loop block or a different block in the cavity).
 2. The system disconnects the socket of the cavity from plug of the connected block.
 3. The system keeps all other connections of the clicked block.
 4. The system connects the socket from the cavity to the plug which was connected to the clicked block.
 5. The system moves the group of blocks as one entity. 

1h. When the user moves the cursor over a conditional block of an if/while block and clicks the left mouse key
 1. The system disconnects the socket of the conditional block from the corresponding plug of the if/while block. 


## Use Case 3: Delete Program Block

### Precondition
There is at least one block present in the Program Area.

### Success Guarantee (Postcondition)
The user is aware that the block is deleted. The block gets removed from the Program Area and the other blocks correctly updated. The amount of blocks which can still be placed is updated.

### Main Success Scenario
1. The user moves the mouse cursor over a block in the Program Area, then presses the left mouse key. The user then moves the mouse cursor to the Palette, and then releases the left mouse key.
2. The system deletes the block from the Program Area.
3. The system increments the amount of blocks which can still be placed.
 
### Extensions
1a. When the user hasn't moved over a block in the Program Area
 1. No further action occurs.
 
1b. When the user presses another key than the left mouse button
 1. No further action considering deleting occurs.
 
1c. When the user releases the mouse cursor outside the Program Area and Palette
 1. No further action occurs.
 
1d. When the user presses the mouse key and the block is connected to other blocks
 1. The system disconnects the block on it's socket.
 2. The system system drags all blocks which are connected through the socket as one entity.
 3. The system sees all the blocks as one entity..
 
1e. When the user moves the cursor over a block which is connected to another through the socket and clicks the left mouse key
 1. The system disconnects the socket of the clicked block from the plug of the block above.
 2. The system keeps all other connections.
 3. The system moves the group of blocks as one entity. 
 
1f. When the user moves the cursor over a block which is connected through other blocks but not in on the top socket and clicks the left mouse key
 1. The system moves the group of blocks as one entity.

1g. When the user moves the cursor over a block which is in the cavity of the if/while block and clicks the left mouse key
 1. The system disconnects the the socket of the clicked block from the plug of the plug above (the loop block or a different block in the cavity).
 2. The system disconnects the socket of the cavity from plug of the connected block.
 3. The system keeps all other connections of the clicked block.
 4. The system connects the socket from the cavity to the plug which was connected to the clicked block.
 5. The system moves the group of blocks as one entity. 

1h. When the user moves the cursor over a conditional block of an if/while block and clicks the left mouse key
 1. The system disconnects the socket of the conditional block from the corresponding plug of the if/while block. 
 
2a. When the maximum amount of blocks was inside the Program Area
 1. The system deletes the block from the Program Area.
 2. The system adds back all possible blocks to the Palette.


## Use Case 4: Start Program Step

### Precondition
There is only one set of connected blocks in the Program Area.

### Success Guarantee (Postcondition)
The user is aware that a step of the program is executed. The Game World gets updated based on the block used in following the step. 

### Main Success Scenario
1. The user presses F5.
2. The system identifies the program in the Program Area.
3. The system evaluates the first block and highlights it.
4. The system changes the state of the robot in the Game World accordingly to the executed block.  

### Extensions
1a. When the system is already busy executing a step in the program
 1. The system ignores the second call from the user.

3-4a. When the block to evaluate is the last block of the program.
 1. The system evaluates this block and highlights it. 
 2. The system changes the state of the robot in the Game World accordingly to the executed block.
 3. The system shows a message according to whether or not the robot has reached the goal. 

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

4a. When the block doesn't affect the robot's state
 1. The system doesn't change the robot's state in the Game World.

4b. When the robot's position is changed to an invalid place in the Game World
 1. The system shows an error message to the user.
 2. The system executes <ins> Reset Program </ins> manually (without the user pressing escape).


## Use Case 5: Reset Program

### Precondition
The application itself is fully loaded in and available for the user.

### Success Guarantee (Postcondition)
The user is aware the reset has occurred. The program gets set back to the initial state (this being no block evaluated). The Game World gets reset to the initial state.

### Main Success Scenario
1. The user presses Escape.
2. The system places the robot on the starting position in the Game World.
3. The system unhighlights the highlighted block in the Program Area.

### Extensions
2a. When the robot is already in the starting position in the Game World 
 1. The system doesn't change the Game World.

3a. When there is no highlighted block in the Program Area
 1. The system keeps all blocks unhighlighted.
