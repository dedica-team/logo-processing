package team.dedica.logo.tree.plants;

import processing.core.PVector;
import team.dedica.logo.tree.GrowthParameters;
import team.dedica.logo.tree.PlantSegment;

import static team.dedica.logo.tree.Util.random;

public class TreeParameters implements GrowthParameters<Tree> {

    public static final double MIN_DIAMETER_TO_GROW = 1;
    public static final double MIN_BRANCH_PROBABILITY = 0.15;

    /**
     * branch probability is multiplied with this factor each turn
     */
    public static final float BRANCH_PROBABILITY_FACTOR = 0.5f;

    /**
     * branch growth velocity reduction per cycle
     */
    public static final float VELOCITY_REDUCTION = 0.55f;
    public static final int MAX_STEM_DIAMETER = 20;

    public PlantSegment<Tree> getSeed(PVector origin, float scaleFactor) {
        return new PlantSegment<>(origin,
                new PVector(0, random(9, 12) * -1 * scaleFactor),
                ((random(15, MAX_STEM_DIAMETER)) * scaleFactor),
                scaleFactor,
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
     * @return bump vector
     */
    public PVector bump() {
        float upwards = -0.8f;
        float downwards = 0.5f;
        PVector bump = new PVector(random(-1, 1), random(upwards, downwards));
        bump.mult(0.5f);
        return bump;
    }

    public float getRandomVelocityFactor(PlantSegment plantSegment) {
        return random(20, 70) * plantSegment.scaleFactor;
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
    public boolean canStillBranch(PlantSegment<Tree> plant) {
        return plant.branchProbability > MIN_BRANCH_PROBABILITY;
    }

    public PVector createGrowthVector(PlantSegment<Tree> plant) {
        PVector growthVector = new PVector(plant.velocity.x, plant.velocity.y);
        growthVector.normalize();
        growthVector.mult(getVelocityReduction()); //slower growth
        growthVector.add(bump());
        growthVector.mult(getRandomVelocityFactor(plant) * plant.scaleFactor);
        return growthVector;
    }

}
