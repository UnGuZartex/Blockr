package GUI.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class ImagePreLoader {
    private static String imageDir = System.getProperty("user.dir") + "/util/resources/imagePacks/";
    private static String imageExtension = ".png";

    public static ImageLibrary createImageLibrary() throws IOException {
        return createImageLibrary("default");
    }

    public static ImageLibrary createImageLibrary(String imagePackName) throws IOException {
        HashMap<String, Image> images = new HashMap<>();
        String imagePath = imageDir + imagePackName + "/";
        File file = new File(imagePath);

        if (!file.isDirectory()) {
            throw new FileNotFoundException("The image directory does not exist! Directory: " + imagePath);
        }

        file = new File(imagePath + ImageLibrary.getNotFoundFileName() + imageExtension);
        if (!file.isFile()) {
            throw new FileNotFoundException("The image pack default image does not exist! File: " + file.getPath());
        }

        loadAllFiles(new File(imagePath).listFiles(), images);
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