package team.dedica.logo.tree;

import processing.core.PVector;
import team.dedica.logo.tree.items.PlantSegment;

public interface GrowthParameters {

    PlantSegment getSeed(PVector origin, float scaleRatio);

    boolean canStillGrow(PlantSegment plant);

    float getVelocityReduction();

    /**
     * slight bump into other direction
     * @return bump vector
     */
    PVector bump();

    float getRandomVelocityFactor(PlantSegment plant);

    boolean isWithinBounds(PVector location);

    float getBranchProbabilityFactor();

    PVector createGrowthVector(PlantSegment plant);

    float getNewDiameterFactor();

    /**
     * @param segment given segment
     * @return true if it can have child branches
     */
    boolean canBranch(PlantSegment segment);
}
