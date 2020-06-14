package team.dedica.logo.tree;

import processing.core.PApplet;
import processing.core.PVector;
import team.dedica.logo.tree.plants.TreeParameters;

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
    private final int width;
    private final int height;

    public ForestPlanter(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public List<PlantSegment> plant() {


        TreeParameters treeParameters = new TreeParameters();
        List<PlantSegment> seeds = new ArrayList<>();
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

                seeds.add(treeParameters.getSeed(new PVector(seedX, y), scaleRatio));
            }
        }

        return seeds;
    }

    @Override
    public void draw(PApplet applet, List<PlantSegment> plants) {

        applet.background(40, 40, 40);
        applet.ellipseMode(PApplet.CENTER);
        applet.stroke(255, 100, 0, 255);//orange
        applet.smooth();


        applet.noStroke();
        applet.fill(60, 60, 60, 255);
        float horizon = (1-MAX_FOREST_HEIGHT_FACTOR) * height +10;
        applet.rect(0, 0, width, horizon);

        plants.forEach(tree -> {
            tree.draw(applet);
        });
    }

    private PVector getPath(float scaleRatio, int width) {
        float goldenCut = 1 - 1/GOLDEN_CUT;
        float pathWidth = random(300, 320) * scaleRatio;
        float center = (width * goldenCut);
        return new PVector(center - pathWidth, center + pathWidth);
    }
}
