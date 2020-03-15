package GUI.Images;

import java.awt.*;
import java.util.HashMap;

/**
 * A class for the library of images.
 *
 * @author Alpha-team
 */
public class ImageLibrary {

    /**
     * Variable referring to the images in this library.
     */
    private final HashMap<String, Image> images;
    /**
     * Variables referring to the different names file names.
     */
    private static final String gameWorldBackGroundFileName = "gameWorldBackground";
    private static final String programAreaBackgroundFileName = "programAreaBackground";
    private static final String paletteBackground = "paletteBackground";
    private static final String notFoundFileName = "notFound";
    private static final String robotFileName = "robot";

    /**
     * Initialise a new library with given images.
     *
     * @param images The images for this library.
     *
     * @post The images of this library are set to the given library.
     *
     * @throws IllegalArgumentException
     *         If the given images are none effective.
     *
     */
    public ImageLibrary(HashMap<String, Image> images) throws IllegalArgumentException {
        if (images == null) {
            throw new IllegalArgumentException("The given image map is null!");
        }
        this.images = images;
    }

    /**
     * Get the image for the game world background.
     *
     * @return The image for the game world background.
     */
    public Image getGameWorldBackgroundImage() {
        return getImage(gameWorldBackGroundFileName);
    }

    /**
     * Get the image for the program area background.
     *
     * @return The image for the program area background.
     */
    public Image getProgramAreaBackgroundImage() {
        return getImage(programAreaBackgroundFileName);
    }

    /**
     * Get the image for the palette background.
     *
     * @return The image for the palette background.
     */
    public Image getPaletteBackgroundImage() {
        return getImage(paletteBackground);
    }

    /**
     * Get the image for the robot.
     *
     * @param directionName The direction of the robot.
     *
     * @return An image of the robot directed in the given direction.
     */
    public Image getRobotImage(String directionName) {
        return getImage(robotFileName + directionName);
    }

    /**
     * Get the image with the given name.
     *
     * @param imageName The name of the image to get.
     *
     * @return If the given image exists, then it is returned, otherwise
     *         the not found image is returned.
     */
    public Image getImage(String imageName) {

        if (images.get(imageName) == null) {
            imageName = notFoundFileName;
        }

        return images.get(imageName);
    }

    /**
     * Get the not found image.
     *
     * @return The not found image.
     */
    public static String getNotFoundFileName() {
        return notFoundFileName;
    }
}
