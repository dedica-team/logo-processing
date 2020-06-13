package team.dedica.logo.tree;

import processing.core.PVector;

import static team.dedica.logo.tree.Util.random;

public class GrowthParameters {

    public static final double MIN_DIAMETER_TO_GROW = 0.2;
    public static final double MIN_BRANCH_CAPACITY = 0.3;
    public static final float BRANCH_CAPACITY_REDUCTION = 0.4f;

    /**
     * branch growth velocition reduction per cycle
     */
    public static final float VELOCITY_REDUCTION = 0.8f;
    public static final double ZOOM_THRESHOLD = 0.8;

    /**
     * tree scale ratio (0-1)
     */
    public final float scaleRatio;

    public final float diameter;

    protected PVector velocity;

    public GrowthParameters(PVector velocity, float scaleRatio, float diameter) {
        this.velocity = velocity;
        this.scaleRatio = scaleRatio;
        this.diameter = diameter;
    }

    public boolean canStillGrow() {
        return diameter > MIN_DIAMETER_TO_GROW;
    }

    public float getVelocityReduction() {
        return VELOCITY_REDUCTION;
    }

    public PVector bump() {
        //slight bump into other direction
        float upwards = -1.0f;
        float downwards = 0.5f;
        PVector bump = new PVector(random(-1, 1), random(upwards, downwards));
        bump.mult(0.4f);
        return bump;
    }

    public float getRandomVelocityFactor() {
        return random(20, 70) * scaleRatio;
    }

    public float getZoom() {
        if (scaleRatio > GrowthParameters.ZOOM_THRESHOLD) {
            return 1.1f;
        }
        return 1f;
    }

    public boolean isWithinBounds(PVector location) {
        return location.x > 0 && location.y > 0;
    }

    public float getBranchProbabilityReduction() {
        return BRANCH_CAPACITY_REDUCTION;
    }

    public PVector createGrowthVector() {
        PVector growthVector = new PVector(velocity.x, velocity.y);
        growthVector.normalize();
        growthVector.mult(getVelocityReduction()); //slower growth
        growthVector.add(bump());
        growthVector.mult(getRandomVelocityFactor());
        growthVector.mult(getZoom());
        return growthVector;
    }

    public GrowthParameters branch() {

        float diameterScale = random(0.7f, 0.9f);
        if (scaleRatio > GrowthParameters.ZOOM_THRESHOLD) {
            diameterScale *= 1.07;
        }
        return new GrowthParameters(new PVector(velocity.x, velocity.y), scaleRatio, diameter * diameterScale);
    }
}
