package team.dedica.logo.tree;

import processing.core.PApplet;
import processing.core.PGraphics;
import team.dedica.logo.tree.items.PlantSegment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Tree image generator
 */
public class ForestApplet extends PApplet {

    /**
     * The planter responsible for seeding the plants.
     */
    List<Planter> planters = new ArrayList<>();

    /**
     * The plants.
     */
    List<PlantSegment> plants = new ArrayList<>();

    /**
     * everything must be drawn in the last frame in order to appear in the svg
     */
    boolean svgOutput = false;
    boolean pngOutput = false;

    /**
     * @param args use "--svg" to create svg output
     */
    public static void main(String[] args) {
        PApplet.main(ForestApplet.class.getName(), args);
    }

    @Override
    public void settings() {
        svgOutput = (args != null && Arrays.asList(args).contains("--svg"));
        pngOutput = (args != null && Arrays.asList(args).contains("--png"));

        String rendererName = null;
        if (svgOutput) {
            println("recording SVG output");
            rendererName = SVG;
        }

        if (pngOutput) {
            println("recording png output");
            g = new PGraphics();
            rendererName = JAVA2D;
        }

        if (svgOutput || pngOutput) {
            createGraphics(width, height, rendererName);
            println("Rendered output enabled");
            size(1920, 1080, rendererName);
            if (pngOutput) {
                beginRecord(g);
            }
            if (svgOutput) {
                beginRecord("SVG", "forest.svg");
            }
        } else {
            fullScreen(SPAN);
        }
    }

    @Override
    public void setup() {

        planters.add(new HorizonPlanter(width, height));
        planters.add(new SingleTreePlanter(width, height));
        planters.forEach(planter -> planter.plant());

        println("Planters planted " + plants.size() + " objects.");
    }

    @Override
    public void draw() {

        /*
        boolean allFinished = true;
        for (int i = 0; i < plants.size(); i++) {
            if (!plants.get(i).isFinished) {
                allFinished = false;
                break;
            }
        }

        if (!allFinished) {
            println("Waiting for trees to have finished growing.");
            return;
        }

         */

        if (svgOutput || pngOutput) {
            background(40, 40, 40);
        }

        planters.forEach(planter -> planter.draw(this));

        if (svgOutput || pngOutput) {
            println("stopping output");
            endRecord();
            if (pngOutput)
                save("forest.png");
            println("completed output");
            exit();
        }
    }
}