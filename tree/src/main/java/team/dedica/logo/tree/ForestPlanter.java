package team.dedica.logo.tree;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static team.dedica.logo.tree.Util.random;

public class ForestPlanter {

    static final float MAX_FOREST_HEIGHT_FACTOR = 0.7f;

    final int height;
    final int width;

    public ForestPlanter(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public List<TreeSegment> plant() {

        List<TreeSegment> paths = new ArrayList<>();
        final float limit = height * (1 - MAX_FOREST_HEIGHT_FACTOR);
        final int step = 10;
        for (int line = height; line > limit; line -= step) {

            float chance = ((height - line) / limit);
            float test = (random(0f, 1f));
            if (test > chance)
                continue;

            //draw the actual number of trees on this line
            //fewer trees in the front, more in the back
            int seeds = (int) Math.max(1, (test * chance * 10));
            for (int seed = 0; seed < seeds; seed++) {
                float x = (random((float) 0, width));
                float scale = (float)line/height;
                paths.add(new TreeSegment(new PVector(x, line), scale));
            }
        }

        return paths;
    }
}
