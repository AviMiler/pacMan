package services;

import java.io.*;
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

    public static BufferedReader getReader(String fileName) {

        BufferedReader reader;
        try (InputStream inputStream = Services.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found: " + fileName);
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file ", e);
        }
        return reader;
    }

}
