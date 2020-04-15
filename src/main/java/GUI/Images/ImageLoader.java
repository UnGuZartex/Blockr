package GameWorldAPI.Images;

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
public class ImageLoader {

    /**
     * Variable referring to the image directory of the images.
     */
    private static String imageDir = System.getProperty("user.dir") + "/util/resources/imagePacks/";

    /**
     * Create a new image library.
     *
     * @return  A new default image library.
     *
     * @throws IOException
     *         If no valid library is found.
     */
    public ImageLibrary createImageLibrary() throws IOException {
        return createImageLibrary("default");
    }

    /**
     * Create a new image library with given name.
     *
     * @param imagePackName The name of the image pack.
     *
     * @return A new image library with the given name.
     *
     * @throws FileNotFoundException
     *         If the given image pack name does not exist.
     * @throws FileNotFoundException
     *         If the default image does not exist.
     * @throws IOException
     *         If an invalid file is read.
     */
    public ImageLibrary createImageLibrary(String imagePackName) throws IOException, FileNotFoundException {
        HashMap<String, Image> images = new HashMap<>();
        String imagePath = imageDir + imagePackName + "/";

        if (!new File(imagePath).isDirectory()) {
            throw new FileNotFoundException("The image directory does not exist! Directory: " + imagePath);
        }

        try {
            ImageIO.read(new File(imageDir + "/" + "notFound.png"));
        }
        catch (IOException e) {
            throw new FileNotFoundException("The default 'not found' image does not exist or is corrupt! " +
                    "An image named 'notFound' should be present here: " + imageDir + "/" + "notFound.png");
        }

        loadAllFiles(new File(imagePath).listFiles(), images);
        return new ImageLibrary(images);
    }

    /**
     * Load all given files into the given image map.
     *
     * @param files the given file array. This array may contain directories with more files.
     * @param images the given image map.
     *
     * @effect All files contained (in)directly in the given file array are loaded into the image map.
     *
     * @throws IOException
     *         If an invalid file is read.
     */
    private void loadAllFiles(File[] files, HashMap<String, Image> images) throws IOException {
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