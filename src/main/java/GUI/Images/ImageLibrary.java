package GUI.Images;

import java.awt.*;
import java.util.HashMap;

public class ImageLibrary {
    private final HashMap<String, Image> images;
    private static final String gameWorldBackGroundFileName = "gameWorldBackground";
    private static final String programAreaBackgroundFileName = "programAreaBackground";
    private static final String paletteBackground = "paletteBackground";
    private static final String notFoundFileName = "notFound";
    private static final String robotFileName = "robot";

    public ImageLibrary(HashMap<String, Image> images) throws IllegalArgumentException {

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

    public Image getProgramAreaBackgroundImage() {
        return getImage(programAreaBackgroundFileName);
    }

    public Image getPaletteBackgroundImage() {
        return getImage(paletteBackground);
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
