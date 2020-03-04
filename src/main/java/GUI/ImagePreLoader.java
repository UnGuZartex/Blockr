package GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

// TODO MAAK KLASSE NIET STATIC?
public class ImagePreLoader {
    private static HashMap<String, Image> cellImages;
    private static Image robotImage;
    private static Image notfoundImage;
    private static Image gameWorldBackground;
    private static String imageDir = System.getProperty("user.dir") + "/util/resources/images/";

    public static void loadImages() {
        // TODO ADD ERROR DINGEN WNR DIR NIE BESTAAT
        Path path = Paths.get(imageDir);

        cellImages = new HashMap<>();

        if (!Files.exists(path)) {
            // TODO THROW EXCEPTION
        }

        try {
            notfoundImage = ImageIO.read(new File(imageDir + "noImage.jpg"));
        } catch (IOException e) {
            // Handle exception if there is one.
        }

        path = Paths.get(imageDir + "robot.png");
        if (Files.exists(path)) {
            try {
                robotImage = ImageIO.read(new File(imageDir + "robot.png"));
            } catch (IOException e) {
                // Handle exception if there is one.
            }
        }
        else {
            robotImage = notfoundImage;
        }

        try {
            gameWorldBackground = ImageIO.read(new File(imageDir + "gameWorldBackground.png"));
        } catch (IOException e) {
            // Handle exception if there is one.
        }

        File[] files = new File(imageDir + "cellImages").listFiles();
        showFiles(files);
    }

    private static void showFiles(File[] files) {
        for (File file : files) {
            if (!file.isDirectory()) {

                Image cellImage = null;

                try {
                    cellImage = ImageIO.read(new File(imageDir + "cellImages/" + file.getName()));
                } catch (IOException e) {
                    // Handle exception if there is one.
                }

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