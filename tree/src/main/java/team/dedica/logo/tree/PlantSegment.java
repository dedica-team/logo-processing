package team.dedica.logo.tree;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static team.dedica.logo.tree.Util.random;

public class PlantSegment {

    private final GrowthParameters growthParameters;

    PVector origin;
    PVector targetLocation;
    boolean isFinished = false;
    boolean bloom = false;

    //paint only once
    boolean hasDrawnTwig = false;
    boolean hasDrawnLeave = false;

    /**
     * Probability of branching, decreased with every iteration
     */
    float branchProbability = 1;

    /**
     * child branches
     */
    List<PlantSegment> children = new ArrayList<>();

    PlantSegment(PVector origin, GrowthParameters growthParameters) {
        this.origin = origin;
        this.growthParameters = growthParameters;
        this.grow();
        this.branch();
    }

    /**
     * computes the grow destination
     */
    private void grow() {

        targetLocation = new PVector(origin.x, origin.y);
        if (growthParameters.isWithinBounds(targetLocation)) {

            if (growthParameters.canStillGrow()) {
                targetLocation.add(growthParameters.createGrowthVector());
            } else {
                //too small
                isFinished = true;
                bloom = true;
            }
        } else {
            //out of bounds
            isFinished = true;
        }
    }

    /**
     * creates child branches
     */
    void branch() {

        if (isFinished)
            return;

        while (branchProbability > GrowthParameters.MIN_BRANCH_CAPACITY) { // control length
            if (random(0, 1) < branchProbability) {
                children.add(
                        new PlantSegment(new PVector(targetLocation.x, targetLocation.y), growthParameters.branch())
                );
            }
            branchProbability *= growthParameters.getBranchProbabilityReduction();
        }

        isFinished = true;
    }

    public void draw(PApplet applet) {
        drawTwig(applet);
        drawLeave(applet);
    }


    /**
     * recursive drawing of children
     */
    void drawTwig(PApplet applet) {
        for (int i = 0; i < children.size(); i++) {
            children.get(i).drawTwig(applet);
        }
        if (hasDrawnTwig) {
            return;
        }

        applet.strokeWeight(growthParameters.diameter);
        applet.line(origin.x, origin.y, targetLocation.x, targetLocation.y);
        hasDrawnTwig = true;
    }

    void drawLeave(PApplet applet) {
        for (int i = 0; i < children.size(); i++) {
            children.get(i).drawLeave(applet);
        }

        if (hasDrawnLeave) {
            return;
        }

        if (isFinished && bloom) {
            applet.noStroke();
            float alpha = 0 + 255 * growthParameters.scaleRatio;
            float alphaBloom = 155 + 100 * growthParameters.scaleRatio;
            applet.fill(255, 255, 255, alphaBloom);
            applet.ellipse(targetLocation.x, targetLocation.y, 15 * growthParameters.scaleRatio, 15 * growthParameters.scaleRatio);
            //orange
            applet.fill(255, 100, 0, alphaBloom);
            applet.ellipse(targetLocation.x, targetLocation.y, 7 * growthParameters.scaleRatio, 7 * growthParameters.scaleRatio);
            //orange
            applet.stroke(255, 100, 0, alpha);
        }

        hasDrawnLeave = true;
    }
}