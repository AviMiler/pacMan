package services;

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

}
