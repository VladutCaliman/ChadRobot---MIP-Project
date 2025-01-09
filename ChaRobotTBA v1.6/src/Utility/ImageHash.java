package Utility;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class ImageHash {
    private HashMap<String, Image> imageMap;

    public ImageHash() {
        imageMap = new HashMap<>();
        loadImages();
    }

    private void loadImages() {
        String[] names = {"0", "1", "2", "3", "4", "-1", "9", "buff_robot", "menu", "norm_robot"};
        String basePath = "src\\Resources\\Images\\";

        for (String name : names) {
            try {
                Image image = ImageIO.read(new File(basePath + name + ".jpg"));
                imageMap.put(name, image);
            } catch (IOException e) {
                System.err.println("Error loading image: " + name + ".jpg");
                e.printStackTrace();
            }
        }
    }

    public Image getImage(String name) {
        return imageMap.get(name);
    }
}
