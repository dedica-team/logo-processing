package team.dedica.logo.tree;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static team.dedica.logo.tree.Util.random;

public class ForestPlanter {

    static final float MAX_FOREST_HEIGHT_FACTOR = 0.625f;

    final int height;
    final int width;

    public ForestPlanter(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public List<TreeSegment> plant() {

        List<TreeSegment> paths = new ArrayList<>();
        final float limit = height * (1 - MAX_FOREST_HEIGHT_FACTOR);
        final int step = 8;
        float halfStep = step / 2;
        for (int line = (int)limit; line < height; line += step) {

            float chance = ((height - line) / limit);
            float test = (random(0f, 1f));
            if (test > chance)
                continue;

            //scale
            float scaleRatio = (line - limit) / (height - limit);
            if (scaleRatio < 0.01)
                continue;

            //fewer trees in the front, more in the back
            int seeds = (int) Math.max(1, (test * chance * 10) * (1 / (scaleRatio)));

            //draw the actual number of trees on this line
            for (int seed = 0; seed < seeds; seed++) {
                float x = (random((float) 0, width));
                float y = line + random(-halfStep, halfStep);
                paths.add(new TreeSegment(new PVector(x, y), scaleRatio));
            }
        }

        return paths;
    }
}
