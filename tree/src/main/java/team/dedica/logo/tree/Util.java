package team.dedica.logo.tree;

import java.util.Random;

public class Util {
    static Random r = new Random();

    public static float random(float min, float max) {
        return min + r.nextFloat() * (max - min);
    }
}
