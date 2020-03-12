# Blockr
## Main goal

This software is designed to help understand basic programming logic.
The program exists out of a simple robot that tries to find its way to a goal in a grid.
The user needs to construct a program to control the robot such that it achieves this goal. 

Following blocks can be used:

* Conditional Blocks.
  * WallInFront-condition: Checks whether or not a wall is in front.
    * True: when a Wall is in front of the robot.
    * False: when no Wall is in front of the robot.
  * Not-condition: Inverts the logic value of the blocks next to it.
    * False: when block next to it is true.
    * True: when block next to it is false.
* Blocks that need a condition.
  * While-block: Run blocks dependent on the state of the condition.
    * All cavityblocks and returns to the while: when the condition is True.
    * Block after While-block: when the condition is False.
  * If-block: Run blocks dependent on the state of the condition.
    * All cavityblocks: when the condition is True.
    * Block after If-block: when the condition is False.
* Blocks that operate the Robot.
  * MoveForward-block: Move the Robot forward with one cell depending on the direction.
  * TurnLeft-block: Turn the Robot to the left, changing its direction.
  * TurnRight-block: Turn the Robot to the right, changing its direction.

## How to run

There are two ways to run this program, one is given a normal IDE, it suffices to import all maven packages (most IDE's will give this message) and run the ProgramGUI.

Another way is using the console with following commands:

To build the software, simply run the command mvn compile. The generated source are placed in the target folder. The simulator can then be run with the following command: mvn exec:java.

Similarly to the previously listed commands, mvn test runs the tests for the project.
