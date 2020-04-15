package GameWorldAPI.Images;

import java.awt.*;
import java.util.HashMap;

/**
 * A class for holding and using images.
 *
 * @author Alpha-team
 */
public class ImageLibrary {

    /**
     * Variable referring to the images in this library.
     */
    private final HashMap<String, Image> images;

    /**
     * Initialise a new library with given images.
     *
     * @param images The images for this library.
     *
     * @post The images of this library are set to the given library.
     *
     * @throws IllegalArgumentException
     *         If the given images are null.
     *
     */
    protected ImageLibrary(HashMap<String, Image> images) throws IllegalArgumentException {
        if (images == null) {
            throw new IllegalArgumentException("The given image map is null!");
        }

        this.images = images;
    }

    /**
     * Get the image with the given name.
     *
     * @param imageName The name of the image to get.
     *
     * @return If the given image exists, then it is returned, otherwise
     *         the default image is returned.
     */
    public Image getImage(String imageName) {

        if (images.get(imageName) == null) {
            imageName = "notFound";
        }

        return images.get(imageName);
    }
}
