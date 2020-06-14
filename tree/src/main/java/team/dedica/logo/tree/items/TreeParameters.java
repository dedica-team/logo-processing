package team.dedica.logo.tree.items;

import processing.core.PVector;
import team.dedica.logo.tree.GrowthParameters;

import static team.dedica.logo.tree.Util.random;

public class TreeParameters implements GrowthParameters<Tree> {

    public static final double MIN_DIAMETER_TO_GROW = 0.5;
    public static final double MIN_BRANCH_PROBABILITY = 0.25;

    /**
     * branch probability is multiplied with this factor each turn
     * influences how many child branches a segment can have (the higher the more)
     */
    public static final float BRANCH_PROBABILITY_FACTOR = 0.4f;

    /**
     * branch growth velocity reduction per cycle
     */
    public static final float VELOCITY_REDUCTION = 0.75f;
    public static final int MAX_STEM_DIAMETER = 20;
    public static final int MIN_ITERATION_TO_BRANCH = 6;

    public PlantSegment<Tree> getSeed(PVector origin, float scaleFactor) {
        return new PlantSegment<>(origin,
                new PVector(0, random(9, 12) * -1 * scaleFactor),
                ((random(15, MAX_STEM_DIAMETER)) * scaleFactor),
                scaleFactor,
                0,
                this
        );
    }

    public boolean canStillGrow(PlantSegment<Tree> plantSegment) {
        return plantSegment.diameter > MIN_DIAMETER_TO_GROW;
    }

    public float getVelocityReduction() {
        return VELOCITY_REDUCTION;
    }

    /**
     * slight bump into other direction
     *
     * @return bump vector
     */
    public PVector bump() {
        float upwards = -0.5f;
        float downwards = 0.5f;
        PVector bump = new PVector(random(-1, 1), random(upwards, downwards));
        bump.mult(0.5f);
        return bump;
    }

    public float getRandomVelocityFactor(PlantSegment plantSegment) {
        return random(20, 40) * plantSegment.scaleFactor;
    }

    public boolean isWithinBounds(PVector location) {
        return location.x > 0 && location.y > 0;
    }

    public float getBranchProbabilityFactor() {
        return BRANCH_PROBABILITY_FACTOR;
    }

    public float getNewDiameterFactor() {
        return random(0.7f, 0.9f);
    }

    @Override
    public boolean canBranch(PlantSegment<Tree> segment) {
        //first segment cannot branch
        if (segment.iteration < MIN_ITERATION_TO_BRANCH && segment.children.size() > 1)
            return false;
        return segment.branchProbability > MIN_BRANCH_PROBABILITY;
    }

    public PVector createGrowthVector(PlantSegment<Tree> plant) {
        PVector growthVector = new PVector(plant.velocity.x, plant.velocity.y);
        growthVector.normalize();
        growthVector.add(bump());
        growthVector.normalize();
        growthVector.mult(getVelocityReduction()); //slower growth
        growthVector.mult(getRandomVelocityFactor(plant) * plant.scaleFactor);
        return growthVector;
    }

}
