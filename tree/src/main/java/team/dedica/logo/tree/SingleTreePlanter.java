package team.dedica.logo.tree;

import processing.core.PApplet;
import processing.core.PVector;
import team.dedica.logo.tree.plants.TreeParameters;

import java.util.ArrayList;
import java.util.List;

import static team.dedica.logo.tree.Util.random;

/**
 * Plants one single tree.
 */
public class SingleTreePlanter implements Planter {

    private final int width;
    private final int height;

    public SingleTreePlanter(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public List<PlantSegment> plant() {
        List<PlantSegment> plants = new ArrayList<>();

        float x = (float) (width * 0.375 + random(-10, 10));

        float y = (float) (height * 0.8);

        TreeParameters parameters = new TreeParameters();
        plants.add(parameters.getSeed(new PVector(x, y), 1));

        return plants;
    }

    @Override
    public void draw(PApplet applet, List<PlantSegment> plants) {
        applet.background(40, 40, 40);
        applet.ellipseMode(PApplet.CENTER);
        applet.smooth();


        applet.noStroke();
        applet.fill(60, 60, 60, 255);
        float horizon = (float) (height * 0.4);
        applet.rect(0, 0, width, horizon);

        applet.stroke(255, 100, 0, 255);//orange
        plants.forEach(tree -> {
            tree.draw(applet);
        });
    }
}
