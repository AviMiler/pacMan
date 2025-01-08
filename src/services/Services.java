package services;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Services {

    public static int getRandomInt(int min, int max) {

        if (max<0)
            max = 0;
        if (min > max) {
            throw new IllegalArgumentException("min should be less than or equal to max");
        }

        Random random = new Random();
        return random.nextInt(max - min + 1) + min;

    }

    public static BufferedReader getFileReader(String filePath) throws IOException {
        // ננסה קודם לקרוא כמשאב (resource)
        InputStream inputStream = Services.class.getClassLoader().getResourceAsStream(filePath);

        // אם לא הצלחנו לקרוא כמשאב, ננסה לקרוא כקובץ רגיל
        if (inputStream == null) {
            try {
                inputStream = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                // אם גם זה נכשל, ננסה לקרוא כ-URL
                URL url = Services.class.getClassLoader().getResource(filePath);
                if (url != null) {
                    inputStream = url.openStream();
                } else {
                    throw new FileNotFoundException("הקובץ לא נמצא: " + filePath);
                }
            }
        }

        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    public static Image loadImage(String resourcePath) {;
        try {
            // Use ClassLoader to access the resource
            InputStream inputStream = Services.class.getClassLoader().getResourceAsStream(resourcePath);
            if (inputStream == null) {
                throw new RuntimeException("Image file not found: " + resourcePath);
            }
            return new ImageIcon(ImageIO.read(inputStream)).getImage();
        } catch (Exception e) {
            return null;
        }
    }
}
