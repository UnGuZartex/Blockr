# Use Cases


## Use Case 1: Add Program Block 

### Precondition
There are still blocks in the Palette for the user to select.

### Success Guarantee (Postcondition)
The user is aware of the position of the block in the Program Area. List of blocks in the Program Area is updated to include the new block. The number of blocks which can be placed is updated.

### Main Success Scenario
1. The user moves the mouse cursor over a block in the Palette, then presses the left mouse key, then moves the mouse cursor to the Program Area with the block visually following the cursor, and then releases the left mouse key.
2. The system adds a new block of the same type to the Program Area at the position of the mouse.
3. The system decreases the amount of blocks which still can be used.
4. The system adds a new block of the same type to the Palette.

### Extensions
1a. When the user releases the mouse key, one of the block's connectors is near a compatible opposite connector of another block
 1. The system adds a new block of the same type to the Program Area; the new block is inserted into an existing group of connected blocks at the matching connection point.
 
4a. When no more blocks can be placed in the Program Area
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
 1. The system inserts the block into an existing group of connected blocks at the matching connection point.

1b. When the user moves the cursor over a block which is connected to another block through the socket and clicks the left mouse key
 1. The system disconnects the socket of the clicked block from the plug of the block above.
 2. The system keeps all other connections.
 3. The system moves the group of blocks as one entity. 
 
1c. When the user moves the cursor over a block which is connected to other blocks but not in on the top socket and clicks the left mouse key
 1. The system moves the group of blocks as one entity.

1d. When the user moves the cursor over a block that is in the cavity of a/an while/if block and clicks the left mouse key
 1. The system disconnects the socket of the clicked block from the plug of the block above (the loop block or a different block in the cavity).
 2. The system disconnects the socket of the cavity from the plug of the connected block.
 3. The system keeps all other connections of the clicked block.
 4. The system connects the socket from the cavity to the plug that was connected to the clicked block.
 5. The system moves the group of blocks as one entity. 

1e. When the user moves the cursor over a conditional block of a/an while/if block and clicks the left mouse key
 1. The system disconnects the socket of the conditional block from the corresponding plug of the while/if block. 
 

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
1a. When the user moves the cursor over a block which is connected to another block through the socket and clicks the left mouse key
 1. The system disconnects the block on it's socket.
 2. The system system drags all blocks which are connected through the socket as one entity.
 3. The system sees all the blocks as one entity.
 
1b. When the user moves the cursor over a block which is connected to another block through the socket and clicks the left mouse key
 1. The system disconnects the socket of the clicked block from the plug of the block above.
 2. The system keeps all other connections.
 3. The system moves the group of blocks as one entity. 
 
1c. When the user moves the cursor over a block which is connected through other blocks but not in on the top socket and clicks the left mouse key
 1. The system moves the group of blocks as one entity.

1d. When the user moves the cursor over a block which is in the cavity of a/an while/if block and clicks the left mouse key
 1. The system disconnects the the socket of the clicked block from the plug of the block above (the loop block or a different block in the cavity).
 2. The system disconnects the socket of the cavity from plug of the connected block.
 3. The system keeps all other connections of the clicked block.
 4. The system connects the socket from the cavity to the plug which was connected to the clicked block.
 5. The system moves the group of blocks as one entity. 

1e. When the user moves the cursor over a conditional block of a/an while/if block and clicks the left mouse key
 1. The system disconnects the socket of the conditional block from the corresponding plug of the if/while block. 
  
3a. When the maximum amount of blocks was inside the Program Area
 1. The system adds back all possible blocks to the Palette.


## Use Case 4: Run Program

### Precondition
There is only one group of connected blocks in the Program Area. All if and while blocks in the group must have a condition connected to it.

### Success Guarantee (Postcondition)
The user is aware that the program is running. The Game World gets updated based on the program. 

### Main Success Scenario
1. The user presses F5.
2. The system unhighlights the last block which has been executed.
3. The system evaluates the next block to execute in the program and highlights it.
4. The system changes the state of the robot in the Game World according to the block.

Repeat 1-4 untill the last block has been executed.
5. The system shows a message that the robot reached the goal.

### Extensions
*a. At any time, when the user presses escape.
 1. The system sets the state of the Game World to the initial state.
 2. The system unhighlights all blocks which are highlighted. 

*b. At any time, when the user moves, adds or deletes a block
 1. The system sets the state of the Game World to the initial state.
 2. The system unhighlights all blocks which are highlighted. 

1a. When the system is already busy executing a step in the program
 1. The system ignores the second call from the user.

2-3a. When no block has been executed yet
 1. The system evaluates the first block of the program and highlights it

3a. When the block to execute is a/an while/if block
 1. The system highlights the while/if block and the condition connected to it.
 2. The system evaluates the condition. 
 3. The system determines the block to go to for the next iteration. 

3b. When the block to execute is contained in the cavity of a/an while/if block
 1. The system highlights the while/if block in which the block to execute is contained and evaluates the block.

3c. When the block to execute is the last block contained in the cavity of a while block
 1. The system chooses the while block itself again as next block to execute.

3d. When the block to execute is the last block contained in the cavity of an if block
 1. The system chooses the block connected to the plug of the if statement as next block to execute.

4a. When the block doesn't affect the robot's state
 1. The system doesn't change the robot's state in the Game World.

4b. When the robot's position is changed to an invalid place in the Game World
 1. The system shows an error message to the user.
