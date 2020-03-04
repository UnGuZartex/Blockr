package GUI.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class ImagePreLoader {
    private static String imageDir = System.getProperty("user.dir") + "/util/resources/imagePacks/";

    public static ImageLibrary createImageLibrary() throws IOException {
        return createImageLibrary("default");
    }

    public static ImageLibrary createImageLibrary(String imagePackName) throws IOException {
        HashMap<String, Image> images = new HashMap<>();
        String imagePath = imageDir + imagePackName + "/";

        if (!new File(imagePath).isDirectory()) {
            throw new FileNotFoundException("The image directory does not exist! Directory: " + imagePath);
        }

        loadAllFiles(new File(imagePath).listFiles(), images);

        if (images.get(ImageLibrary.getNotFoundFileName()) == null) {
            throw new FileNotFoundException("The image pack default image does not exist! File: " + imagePath + ImageLibrary.getNotFoundFileName() + ".(any image extension)");
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