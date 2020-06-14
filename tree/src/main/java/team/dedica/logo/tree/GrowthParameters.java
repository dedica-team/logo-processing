package team.dedica.logo.tree;

import processing.core.PVector;

public interface GrowthParameters<T> {

    PlantSegment<T> getSeed(PVector origin, float scaleRatio);

    boolean canStillGrow(PlantSegment<T> plant);

    float getVelocityReduction();

    /**
     * slight bump into other direction
     * @return bump vector
     */
    PVector bump();

    float getRandomVelocityFactor(PlantSegment<T> plant);

    boolean isWithinBounds(PVector location);

    float getBranchProbabilityFactor();

    PVector createGrowthVector(PlantSegment<T> plant);

    float getNewDiameterFactor();

    /**
     * @param segment given segment
     * @return true if it can have child branches
     */
    boolean canBranch(PlantSegment<T> segment);
}
