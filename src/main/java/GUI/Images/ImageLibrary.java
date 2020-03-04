package GUI.Images;

import java.awt.*;
import java.util.HashMap;

public class ImageLibrary {
    private final HashMap<String, Image> images;
    private static String gameWorldBackGroundFileName = "gameWorldBackground";
    private static String notFoundFileName = "notFound";
    private static String robotFileName = "robot";

    public ImageLibrary(HashMap<String, Image> images) throws IllegalArgumentException {

        // TODO NOG MEER EXCEPTIONS? BVB ALS SOMMIGE AFBEELDINGEN ER NIET INZITTEN?
        if (images == null) {
            throw new IllegalArgumentException("The given image map is null!");
        }

        this.images = images;
    }

    public Image getRobotImage() {
        return getImage(robotFileName);
    }

    public Image getGameWorldBackgroundImage() {
        return getImage(gameWorldBackGroundFileName);
    }

    public Image getImage(String imageName) {

        if (images.get(imageName) == null) {
            imageName = notFoundFileName;
        }

        return images.get(imageName);
    }

    public static String getNotFoundFileName() {
        return notFoundFileName;
    }
}
