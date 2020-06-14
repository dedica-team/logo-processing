package team.dedica.logo.tree;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static team.dedica.logo.tree.Util.random;

public class PlantSegment<T> {


    private final GrowthParameters<T> growthParameters;

    private final PVector origin;
    private PVector targetLocation;
    public final float scaleFactor;
    public final float diameter;
    public final PVector velocity;
    boolean isFinished = false;
    boolean bloom = false;

    //paint only once
    boolean hasDrawnTwig = false;
    boolean hasDrawnLeave = false;

    /**
     * Probability of branching, decreased with every iteration
     */
    public float branchProbability = 1;

    /**
     * child branches
     */
    List<PlantSegment<T>> children = new ArrayList<>();

    public PlantSegment(final PVector origin,
                 final PVector velocity,
                 final float diameter,
                 final float scaleFactor,
                 final GrowthParameters<T> growthParameters
    ) {
        this.origin = origin;
        this.velocity = velocity;
        this.diameter = diameter;
        this.scaleFactor = scaleFactor;
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

            if (growthParameters.canStillGrow(this)) {
                targetLocation.add(growthParameters.createGrowthVector(this));
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

        while (growthParameters.canStillBranch(this)) { // control length
            if (random(0, 1) < branchProbability) {

                children.add(
                        new PlantSegment<T>(
                                new PVector(targetLocation.x, targetLocation.y),
                                velocity,
                                diameter * growthParameters.getNewDiameterFactor(),
                                scaleFactor,
                                growthParameters
                        )
                );
            }
            branchProbability *= growthParameters.getBranchProbabilityFactor();
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

        applet.strokeWeight(diameter);
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
            float alpha = 0 + 255 * scaleFactor;
            float alphaBloom = 155 + 100 * scaleFactor;
            applet.fill(255, 255, 255, alphaBloom);
            applet.ellipse(targetLocation.x, targetLocation.y, 15 * scaleFactor, 15 * scaleFactor);
            //orange
            applet.fill(255, 100, 0, alphaBloom);
            applet.ellipse(targetLocation.x, targetLocation.y, 7 * scaleFactor, 7 * scaleFactor);
            //orange
            applet.stroke(255, 100, 0, alpha);
        }

        hasDrawnLeave = true;
    }
}