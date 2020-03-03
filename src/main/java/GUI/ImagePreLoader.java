package GUI;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ImagePreLoader {
    private static HashMap<String, Image> cellImages;
    private static Image robotImage;
    private static Image notfoundImage;
    private static Image gameWorldBackground;
    private static String imageDir = System.getProperty("user.dir") + "/util/resources/images/";

    public static void loadImages() {
        Path path = Paths.get(imageDir);

        cellImages = new HashMap<>();

        if (!Files.exists(path)) {
            // TODO THROW EXCEPTION
        }

        //String filePath = imageDir + ;
        //File f = new File("/Path/To/File/or/Directory");

        path = Paths.get(imageDir + "robot.png");
        if (Files.exists(path)) {
            System.err.println("FOUND");
           robotImage = Toolkit.getDefaultToolkit().getImage(imageDir + "robot.png");
        }
        else {
            robotImage = Toolkit.getDefaultToolkit().getImage(imageDir + "noImage.jpg");
        }

        notfoundImage = Toolkit.getDefaultToolkit().getImage(imageDir + "noImage.jpg");

        // TODO ADD ERROR DINGEN WNR DIR NIE BESTAAT
        gameWorldBackground = Toolkit.getDefaultToolkit().getImage(imageDir + "gameWorldBackground.png");

        File[] files = new File(imageDir + "cellImages").listFiles();
        showFiles(files);
    }

    private static void showFiles(File[] files) {
        for (File file : files) {
            if (!file.isDirectory()) {
                Image cellImage = Toolkit.getDefaultToolkit().getImage(imageDir + "cellImages/" + file.getName());
                cellImages.put(file.getName().replaceFirst("[.][^.]+$", ""), cellImage);
                System.err.println("TEST: " + imageDir + file.getName() + " " + file.getName().replaceFirst("[.][^.]+$", ""));
            }
        }
    }

    public static Image getRobotImage() {
        return robotImage;
    }

    public static Image getGameWorldBackgroundImage() {
        return gameWorldBackground;
    }

    public static Image getCellImage(String cellTypeString) {
        if (cellImages.get(cellTypeString) == null) {
            return notfoundImage;
        }

        return cellImages.get(cellTypeString);
    }
}
