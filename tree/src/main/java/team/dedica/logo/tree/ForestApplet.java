package team.dedica.logo.tree;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Tree image generator
 */
public class ForestApplet extends PApplet {

    List<TreeSegment> paths = new ArrayList<>();

    /**
     * everything must be drawn in the last frame in order to appear in the svg
     */
    boolean svgOutput = false;

    public static void main(String[] args) {
        PApplet.main(ForestApplet.class.getName(), args);
    }

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

        ForestPlanter forestPlanter = new ForestPlanter(height, width);
        paths.addAll(forestPlanter.plant());
    }

    @Override
    public void draw() {

        /*
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

         */

        if (svgOutput) {
            background(40, 40, 40);
        }
        //Collections.reverse(paths);
        paths.forEach(tree -> {
            drawTwig(tree);
            drawLeave(tree);
        });

        if (svgOutput) {
            endRecord();
            exit();
        }
    }

    /**
     * recurisve drawing of children
     */
    void drawTwig(TreeSegment treeSegment) {
        for (int i = 0; i < treeSegment.children.size(); i++) {
            drawTwig(treeSegment.children.get(i));
        }
        if (treeSegment.hasDrawnTwig) {
            return;
        }

        strokeWeight(treeSegment.diameter);
        line(treeSegment.lastLocation.x, treeSegment.lastLocation.y, treeSegment.location.x, treeSegment.location.y);
        treeSegment.hasDrawnTwig = true;
    }

    void drawLeave(TreeSegment treeSegment) {
        for (int i = 0; i < treeSegment.children.size(); i++) {
            drawLeave(treeSegment.children.get(i));
        }

        if (treeSegment.hasDrawnLeave) {
            return;
        }

        if (treeSegment.isFinished && treeSegment.bloom) {
            noStroke();
            fill(255, 255, 255, 255);
            ellipse(treeSegment.location.x, treeSegment.location.y, 15 * treeSegment.scaleRatio, 15 * treeSegment.scaleRatio);
            //orange
            fill(255, 100, 0, 255);
            ellipse(treeSegment.location.x, treeSegment.location.y, 7 * treeSegment.scaleRatio, 7 * treeSegment.scaleRatio);
            //orange
            stroke(255, 100, 0, 200);
        }
        treeSegment.hasDrawnLeave = true;
    }

}