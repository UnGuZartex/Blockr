# Use Cases
## Use Case 1: Add Program Block
### Main Success Scenario
1. The user moves the mouse cursor over a block in the Palette, then presses the left mouse key, then moves the mouse 
cursor to the Program Area, and then releases the left mouse key.

2. The system adds a new block of the same type to the Program Area at the position of the mouse.

3. The system adds a new block of the same type to the Palette.

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

2a. When the maximum amount of blocks in the Program Area has been reached.
 1. The system removes all blocks from the Palette.
 
## Use Case 2: Moving Program Block
### Main Success Scenario
 1. The user moves the mouse cursor over a block in the Program Area, then presses the left mouse key. 
 The user then moves the mouse cursor inside the Program Area, and then releases the left mouse key.
 2. The system changes the position of the block from the previous position to the mouse position.
### Extensions
1a. When the user moves the mouse cursor outside the Program Area and Palette
1. The system doesn't change the block's position.

1b. When the user hasn't moved over a block in the Program Area
 1. No action occurs
 
1c. When the user presses another key than the left mouse button
 1. No action considering moving occurs.

1d. When the user presses the mouse key and the block is connected to other blocks
 1. The system disconnects the block and all underlying blocks from the blocks above
 2. The system moves the block and all underlying blocks to the new position

1e. When the user releases the mouse key, one of the block’s 
connectors is near a compatible opposite connector of another block.
 1. The block is inserted into an existing group of connected blocks at the matching connection point.
## Use Case 3: Delete Program Block

### Main Success Scenario
1. The user moves the mouse cursor over a block in the Program Area, then presses the left mouse key. 
 The user then moves the mouse cursor to the Palette, and then releases the left mouse key.
 2. The system deletes the block from the Program Area.
 
### Extensions
2a. When the maximum amount of blocks was inside the Program Area
 1. The system deletes the block from the Program Area
 2. The system adds back all possible blocks to the Palette


