package team.dedica.logo.tree;

import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static team.dedica.logo.tree.Util.random;

/**
 * Plants a forest with a path trough the trees.
 *
 *
 *
 */
public class ForestPlanter implements Planter {

    static final float MAX_FOREST_HEIGHT_FACTOR = 0.385f;

    @Override
    public List<TreeSegment> plant(int height, int width) {

        List<TreeSegment> seeds = new ArrayList<>();
        final float limit = height * (1 - MAX_FOREST_HEIGHT_FACTOR);
        int step = 2;
        for (int line = (int) limit; line < height; line += step) {

            float chance = ((height - line) / limit);
            float test = (random(0f, 1f));
            if (test > chance)
                continue;

            //scale
            float scaleRatio = (line - limit) / (height - limit);
            step = (int) (step * (1.32 + scaleRatio));
            if (scaleRatio < 0.01) {
                continue;
            }

            float scRSq = (1 - scaleRatio) * (1 - scaleRatio/2); //very low for "near" lines
            //draw the actual number of trees on this line
            PVector path = getPath(scaleRatio, width);
            for (int x = 10; x < (width - 10); x += Math.max(1, 100 * scaleRatio)) {
                test = (random(0f, 1f));
                if (test > scRSq) {
                    continue;
                }

                if (x > path.x && x < path.y) {
                    continue;
                }
                float seedX = random(x - 10, x + 10);

                float halfStep = (float) (step / 2.5);
                float y = line + random(-halfStep, halfStep);

                seeds.add(new TreeSegment(new PVector(seedX, y), scaleRatio));
            }
        }

        return seeds;
    }

    private PVector getPath(float scaleRatio, int width) {
        float goldenCut = 1 - 1/GOLDEN_CUT;
        float pathWidth = random(300, 320) * scaleRatio;
        float center = (width * goldenCut);
        return new PVector(center - pathWidth, center + pathWidth);
    }
}
