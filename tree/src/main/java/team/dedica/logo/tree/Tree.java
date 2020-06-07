package team.dedica.logo.tree;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tree image generator
 */
public class Tree extends PApplet {

    List<TreeSegment> paths = new ArrayList<>();

    /**
     * everything must be drawn in the last frame in order to appear in the svg
     */
    boolean svgOutput = false;
    static final float MAX_FOREST_HEIGHT_FACTOR = 0.7f;

    @Override
    public void settings() {
        svgOutput = (args != null && Arrays.asList(args).contains("--svg"));

        if (svgOutput) {
            size(2048, 1024);
        } else {
            fullScreen(SPAN);
        }
    }

    @Override
    public void setup() {
        if (svgOutput) {
            beginRecord(SVG, "tree.svg");
        }

        background(40, 40, 40);
        ellipseMode(CENTER);
        stroke(255, 100, 0, 255);//orange
        smooth();

        final float limit = height * (1 - MAX_FOREST_HEIGHT_FACTOR);
        final int step = 10;
        for (int line = height; line > limit; line -= step) {

            float chance = ((height - line) / limit);
            float test = (random(0f, 1f));
            if (test > chance)
                continue;

            //draw the actual number of trees on this line
            //fewer trees in the front, more in the back
            int seeds = (int) Math.max(1, (test * test * 10));
            for (int seed = 0; seed < seeds; seed++) {
                float x = (random((float) 0, width));
                float scale = 1;
                paths.add(new TreeSegment(new PVector(x, line), scale));
            }

        }
    }

    @Override
    public void draw() {

        boolean allFinished = true;
        for (int i = 0; i < paths.size(); i++) {
            if (!paths.get(i).isFinished) {
                allFinished = false;
                break;
            }
        }

        if (!allFinished) {
            return;
        }

        if (svgOutput) {
            background(40, 40, 40);
        }
        paths.forEach(tree -> {
            drawTwig(tree);
            drawLeave(tree);
        });

        if (svgOutput) {
            endRecord();
            exit();
        }
    }

    public static void main(String[] args) {
        PApplet.main(Tree.class.getName());
    }

    /**
     * recurisve drawing of children
     */
    void drawTwig(TreeSegment treeSegment) {
        for (int i = 0; i < treeSegment.children.size(); i++) {
            drawTwig(treeSegment.children.get(i));
        }
        if (treeSegment.hasDrawn) {
            return;
        }

        strokeWeight(treeSegment.diameter);
        line(treeSegment.lastLocation.x, treeSegment.lastLocation.y, treeSegment.location.x, treeSegment.location.y);
        treeSegment.hasDrawn = true;
    }

    void drawLeave(TreeSegment treeSegment) {
        for (int i = 0; i < treeSegment.children.size(); i++) {
            drawLeave(treeSegment.children.get(i));
        }

        if (treeSegment.isFinished && treeSegment.bloom) {
            noStroke();
            fill(255, 255, 255, 255);
            ellipse(treeSegment.location.x, treeSegment.location.y, 15, 15);
            //orange
            fill(255, 100, 0, 255);
            ellipse(treeSegment.location.x, treeSegment.location.y, 7, 7);
            //orange
            stroke(255, 100, 0, 200);
        }
    }

}