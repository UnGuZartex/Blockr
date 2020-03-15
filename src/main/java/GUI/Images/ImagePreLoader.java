package GUI.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * A class to load image libraries.
 *
 * @author Alpha-team
 */
public class ImagePreLoader {

    /**
     * Variable referring to the image directory of the images.
     */
    private static String imageDir = System.getProperty("user.dir") + "/util/resources/imagePacks/";

    /**
     * Create a new image library.
     *
     * @return A new default image library.
     *
     * @throws IOException
     *         If no valid library is found.
     */
    public static ImageLibrary createImageLibrary() throws IOException {
        return createImageLibrary("default");
    }

    /**
     * Create a new image library with given name.
     *
     * @param imagePackName The name of the image pack.
     *
     * @return A new image library with the given name.
     *
     * @throws IOException
     *         If the given image pack name does not exist.
     * @throws IOException
     *         If the default image does not exist.
     */
    public static ImageLibrary createImageLibrary(String imagePackName) throws IOException {
        HashMap<String, Image> images = new HashMap<>();
        String imagePath = imageDir + imagePackName + "/";

        if (!new File(imagePath).isDirectory()) {
            throw new FileNotFoundException("The image directory does not exist! Directory: " + imagePath);
        }

        loadAllFiles(new File(imagePath).listFiles(), images);

        if (!images.containsKey(ImageLibrary.getNotFoundFileName())) {
            throw new FileNotFoundException("The image pack default image does not exist! File: "
                    + imagePath + ImageLibrary.getNotFoundFileName() + ".(any image extension)");
        }

        return new ImageLibrary(images);
    }

    private static void loadAllFiles(File[] files, HashMap<String, Image> images) throws IOException {
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    images.put(file.getName().replaceFirst("[.][^.]+$", ""), ImageIO.read(file));
                }
                else if (file.isDirectory()) {
                    loadAllFiles(file.listFiles(), images);
                }
            }
        }
    }
}