To run the application, another jar dependency is needed.
This is the ImageLibrary. This is necessary for using Images.
This library preloads the images instead of loading them at each paint.

Use following command to run:

`java -cp ImageLibrary.jar;GameWorldAPI.jar;impl.jar;client.jar client.main.package.ClientMainClassimpl.root.package.ImplRootClass`

These jars need to be accessed by their directory, only if the jars are in the same directory above command can be used.

Otherwise if ImageLibrary, GameWorldAPI, ... etc are in a directory called D:/Project/SWOP/Jars/

change the commmandline command to:

`java -cp D:/Project/SWOP/Jars/ImageLibrary.jar;D:/Project/SWOP/Jars/GameWorldAPI.jar;D:/Project/SWOP/Jars/impl.jar;D:/Project/SWOP/Jars/client.jar client.main.package.ClientMainClassimpl.root.package.ImplRootClass`

For Blockr the main class is GUI.ProgramGUI

For RobotGameWorld the main class is GameWorldUtility.LevelInitializer

For mygame (or LineJumper) the main class is LineJumper.LineWorldInitializer
